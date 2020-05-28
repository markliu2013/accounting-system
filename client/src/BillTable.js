import React from 'react';

import { Table, Alert, message, Button } from 'antd';
import moment from 'moment';
import reqwest from 'reqwest';

import { observe, handleBillTableChange, initBillTableState } from './State'

const BillTable = () => {

    const columns = [
        {
            title: 'Type',
            dataIndex: 'type',
            sorter: true,
            render: type => {
                let typeVal = '';
                if (type === 1) {
                    typeVal = 'income';
                } else if (type === 0) {
                    typeVal = 'outcome';
                }
                return (
                    <>
                    {typeVal}
                    </>
                )
            },
            width: '20%'
        },
        {
            title: 'Time',
            dataIndex: 'time',
            sorter: true,
            render: time => {
                let timeFormat = moment(time).format('YYYY-MM-DD HH:mm:ss');
                return (
                    <>
                    { timeFormat }
                    </>
                )
            },
            width: '20%'
        },
        {
            title: 'Category',
            sorter: true,
            dataIndex: 'categoryName'
        },
        {
            title: 'Amount',
            sorter: true,
            dataIndex: 'amount'
        },
        {
            title: 'Operation',
            dataIndex: '',
            key: 'x',
            render: id => <Button onClick={() => handleDelete(id)}>Remove</Button>
        }
    ];

    const [billTableState, setBillTableState] = React.useState(initBillTableState);

    React.useEffect(() => observe((newBillTableState) => {setBillTableState(newBillTableState)}));

    React.useEffect(() => {
        const { pagination } = initBillTableState;
        handleBillTableChange(pagination);
    }, []);

    function handleTableChange(pagination, filters, sorter) {
        handleBillTableChange(pagination, filters, sorter, billTableState.query);
    }

    function handleDelete(id) {
        // TODO id为什么变成对象?
        reqwest({
            url: 'api/bills/'+id.id,
            method: 'delete',
            success: (data) => {
                if (data.success) {
                    message.success('Remove Successfully!');
                    const { pagination, filters, sorter } = billTableState;
                    handleBillTableChange(pagination, filters, sorter);
                } else {
                    message.error('Remove Failed');
                }
            },
            error: () => {
                message.error('Remove Failed');
            }
        });
        console.log(id);
    }

    return (
        <>
        <Alert
            type="info"
            description = {
                <p>Out come total：<span style={{color: 'red'}}>{billTableState.out}</span>$; Income total：<span style={{color: 'green'}}>{billTableState.in}</span>$.</p>
            }
        />
        <Table
            columns={columns}
            rowKey={record => record.id}
            dataSource={billTableState.data}
            pagination={billTableState.pagination}
            loading={billTableState.loading}
            onChange={handleTableChange}
        />
        </>
    );

}


export default BillTable;