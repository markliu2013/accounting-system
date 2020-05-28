import React from 'react';
import reqwest from 'reqwest';
import { Form, InputNumber, Select, Button, DatePicker } from 'antd';
import { observe, handleBillTableChange, initBillTableState } from './State'

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};
const tailLayout = {
    wrapperCol: { offset: 8, span: 16 },
};


const BillSearchForm = () => {

    const [form] = Form.useForm();

    const [categories, setCategories] = React.useState([]);
    const [billTableState, setBillTableState] = React.useState(initBillTableState);

    React.useEffect(() => observe((newBillTableState) => {setBillTableState(newBillTableState)}));

    React.useEffect(() => {
        reqwest({
            url: 'api/categories/',
            method: 'get',
            type: 'json'
        }).then(data => {
            setCategories(data.data)
        });
    }, []);

    const onFinish = values => {
        const { pagination, filters, sorter } = billTableState;
        let query = {...values};
        if (query.time) {
            query['year'] = query.time.get('year');
            query['month'] = query.time.get('month')+1;
            delete query.time;
        }
        pagination['current'] = 1;
        handleBillTableChange(pagination, filters, sorter, query);
    };

    const onFinishFailed = errorInfo => {
        console.log('Failed:', errorInfo);
    };

    return (
        <Form
            form={form}
            {...layout}
            name="billSearchForm"
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
        >
            <Form.Item
                label="Bill Type"
                name="type"
            >
                <Select placeholder="Please select a type">
                    <Select.Option value={0}>outcome</Select.Option>
                    <Select.Option value={1}>income</Select.Option>
                </Select>
            </Form.Item>

            <Form.Item
                label="Bill Category"
                name="categoryId"
            >
                <Select placeholder="Please select bill category">
                    { categories.map((c, i) => {
                        return (<Select.Option key={c.id} value={c.id}>{c.name}</Select.Option>)
                    })}
                </Select>
            </Form.Item>
            <Form.Item
                name="time"
                label="Bill Month"
                >
                <DatePicker picker="month" format="YYYY-MM" />
            </Form.Item>
            <Form.Item label="Bill Amount">
                <Form.Item name="minAmount" style={{ display: 'inline-block'}}>
                    <InputNumber min={0} step={0.1} precision={2} />
                </Form.Item>
                <span style={{ display: 'inline-block', width: '24px', lineHeight: '32px', textAlign: 'center' }}>
                    -
                </span>
                <Form.Item name="maxAmount" style={{ display: 'inline-block'}}>
                    <InputNumber min={0} step={0.1} precision={2} />
                </Form.Item>
            </Form.Item>
            <Form.Item {...tailLayout}>
                <Button type="primary" htmlType="submit">
                    Search Bill
                </Button>
                <Button
                    style={{ margin: '0 8px'}}
                    onClick={() => {
                        form.resetFields();
                    }}
                >
                    Reset
                </Button>
            </Form.Item>
        </Form>
    );
};

export default BillSearchForm;