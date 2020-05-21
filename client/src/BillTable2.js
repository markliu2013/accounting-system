import React from 'react';

import { Table } from 'antd';
import reqwest from 'reqwest';
import moment from 'moment';

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
        width: '20%',
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
        width: '20%',
    },
    {
        title: '类别',
        sorter: true,
        dataIndex: 'categoryName',
    },
    {
        title: '金额',
        sorter: true,
        dataIndex: 'amount',
    },
];

const getBillParams = params => {
    return {
        size: params.pagination.pageSize,
        page: params.pagination.current-1,
        ...params,
    };
};


class BillTable extends React.Component {
    state = {
        data: [],
        pagination: {
            current: 1,
            pageSize: 10,
        },
        loading: false,
    };

    componentDidMount() {
        const { pagination } = this.state;
        this.fetch({pagination});
    }

    getAlert() {
        this.forceUpdate();
    }

    handleTableChange = (pagination, filters, sorter) => {
        let orderDir = '';
        if (sorter.order === 'ascend') {
            orderDir = ',asc'
        } else if (sorter.order === 'descend') {
            orderDir = ',desc'
        }
        this.fetch({
            sort: sorter.field ? sorter.field + orderDir : '',
            pagination,
            ...filters,
        });
    };

    fetch = (params = {}) => {
        this.setState({loading: true});
        reqwest({
                url: 'api/bills/',
                method: 'get',
                type: 'json',
                data: getBillParams(params),
            }).then(data => {
                console.log(data);
                this.setState({
                    loading: false,
                    data: data.data.page.content,
                    pagination: {
                        ...params.pagination,
                        total: data.data.page.totalElements,
                    },
            });
        });
    };

    render() {
        const { data, pagination, loading } = this.state;
        //const sortDirections = ['asc', 'desc']; //会导致图标无法显示
        return (
            <Table
                columns={columns}
                //sortDirections={sortDirections}
                rowKey={record => record.id}
                dataSource={data}
                pagination={pagination}
                loading={loading}
                onChange={this.handleTableChange}
            />
        );
    }
}

export default BillTable;