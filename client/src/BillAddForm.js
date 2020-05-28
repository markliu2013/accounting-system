import React from 'react';
import reqwest from 'reqwest';
import { Form, InputNumber, Select, Button, DatePicker, message } from 'antd';
import { observe, handleBillTableChange, initBillTableState } from './State'
import moment from 'moment';

const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 16 },
};
const tailLayout = {
    wrapperCol: { offset: 8, span: 16 },
};


const BillAddForm = () => {

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
        values.time = values.time.valueOf();
        let jsonData = JSON.stringify(values);
        reqwest({
            url: 'api/bills/',
            method: 'post',
            contentType: 'application/json',
            processData: false,
            data: jsonData,
            success: (data) => {
                if (data.success) {
                    message.success('Add Successfully!');
                    const { pagination, filters, sorter, query } = billTableState;
                    handleBillTableChange(pagination, filters, sorter, query);
                } else {
                    message.error('Add Fail!');
                }
            },
            error: () => {
                message.error('Add Fail!');
            }
        });
    };

    const onFinishFailed = errorInfo => {
        console.log('Failed:', errorInfo);
    };

    return (
        <Form
            form={form}
            {...layout}
            name="billAddForm"
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            initialValues={{
                'type': 0,
                'time': moment()
            }}
            >
            <Form.Item
                label="Bill Type"
                name="type"
                rules={[{ required: true, message: 'Please select a type!' }]}
            >
                <Select placeholder="Please select a type">
                    <Select.Option value={0}>outcome</Select.Option>
                    <Select.Option value={1}>income</Select.Option>
                </Select>
            </Form.Item>
            <Form.Item
                name="time"
                label="Bill Time"
                rules={[{ type: 'object', required: true, message: 'Please select bill time!' }]}
            >
                <DatePicker showTime format="YYYY-MM-DD HH:mm:ss" />
            </Form.Item>
            <Form.Item
                label="Bill Category"
                name="categoryId"
                rules={[{ required: true, message: 'Please select bill category!' }]}
            >
                <Select placeholder="Please select bill category">
                    { categories.map((c, i) => {
                        return (<Select.Option key={c.id} value={c.id}>{c.name}</Select.Option>)
                    })}
                </Select>
            </Form.Item>
            <Form.Item
                name="amount"
                label="Bill Amount"
                rules={[{ required: true, message: 'Please type bill amount!' }]}
            >
                <InputNumber min={0} step={0.1} precision={2} />
            </Form.Item>
            <Form.Item {...tailLayout}>
                <Button type="primary" htmlType="submit">
                    Add Bill
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

export default BillAddForm;