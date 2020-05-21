/**
 * 全局的状态管理
 */

import reqwest from 'reqwest';

export const initBillTableState = {
    data: [],
    in: 0,
    out: 0,
    pagination: {
        current: 1,
        pageSize: 10
    },
    filters: [],
    sorter: {},
    query: {},
    loading: false
}

let billTableState = {...initBillTableState};

let observers = [];

function emitChange() {
    observers.forEach((o) => o && o(billTableState));
}

export function observe(o) {
    observers.push(o);
    emitChange();
    return () => {
        observers = observers.filter((t) => t !== o)
    }
}

const getBillParams = (pagination, filters, sorter, query) => {
    let orderDir = '';
    if (sorter) {
        if (sorter.field) {
            orderDir = sorter.field;
        }
        if (sorter.order === 'ascend') {
            orderDir += ',asc'
        } else if (sorter.order === 'descend') {
            orderDir += ',desc'
        }
    }
    return {
        size: pagination.pageSize,
        page: pagination.current-1,
        sort: orderDir,
        ...query
    };
};

export function handleBillTableChange(pagination, filters, sorter, query) {
    //billTableState = Object.assign({}, billTableState, {});
    billTableState = {...billTableState};
    billTableState.pagination = pagination;
    billTableState.sorter = sorter;
    billTableState.filters = filters;
    billTableState.loading = true;
    billTableState.query = query;

    emitChange();

    reqwest({
        url: 'api/bills/',
        method: 'get',
        contentType: 'application/x-www-form-urlencoded',
        data: getBillParams(pagination, filters, sorter, query)
    }).then(data => {
        billTableState = {...billTableState};
        billTableState.loading = false;
        if (data.success) {
            billTableState.data = data.data.page.content;
            billTableState.in = data.data.in;
            billTableState.out = data.data.out;
            billTableState.pagination = {
                ...pagination,
                total: data.data.page.totalElements
            }
        }
        emitChange();
    });
}
