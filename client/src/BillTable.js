import React from 'react';

import { Table, Alert, message } from 'antd';
import moment from 'moment';
import reqwest from 'reqwest';

import { observe, handleBillTableChange, initBillTableState } from './State'

const BillTable = () => {

    const columns = [
        {
            title: '类型',
            dataIndex: 'type',
            sorter: true,
            render: type => {
                let typeVal = '';
                if (type === 1) {
                    typeVal = '收入';
                } else if (type === 0) {
                    typeVal = '支出';
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
            title: '时间',
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
            title: '类别',
            sorter: true,
            dataIndex: 'categoryName'
        },
        {
            title: '金额',
            sorter: true,
            dataIndex: 'amount'
        },
        {
            title: '操作',
            dataIndex: '',
            key: 'x',
            render: id => <a onClick={() => handleDelete(id)}>删除</a>
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
                    message.success('删除成功');
                    const { pagination, filters, sorter } = billTableState;
                    handleBillTableChange(pagination, filters, sorter);
                } else {
                    message.error('删除失败');
                }
            },
            error: () => {
                message.error('删除失败');
            }
        });
        console.log(id);
    }

    return (
        <>
        <Alert
            type="info"
            description = {
                <p>支出总金额：<span style={{color: 'red'}}>{billTableState.out}</span>元；收入总金额：<span style={{color: 'green'}}>{billTableState.in}</span>元。</p>
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