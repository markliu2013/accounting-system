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
                    message.success('添加成功');
                    const { pagination, filters, sorter } = billTableState;
                    handleBillTableChange(pagination, filters, sorter);
                } else {
                    message.error('添加失败');
                }
            },
            error: () => {
                message.error('添加失败');
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
                label="账单类型"
                name="type"
                rules={[{ required: true, message: '请选择账单类型!' }]}
            >
                <Select placeholder="请选择账单类型">
                    <Select.Option value={0}>支出</Select.Option>
                    <Select.Option value={1}>收入</Select.Option>
                </Select>
            </Form.Item>
            <Form.Item
                name="time"
                label="账单时间"
                rules={[{ type: 'object', required: true, message: '请选择账单时间!' }]}
            >
                <DatePicker showTime format="YYYY-MM-DD HH:mm:ss" />
            </Form.Item>
            <Form.Item
                label="账单类别"
                name="categoryId"
                rules={[{ required: true, message: '请选择账单类别!' }]}
            >
                <Select placeholder="请选择账单类别">
                    { categories.map((c, i) => {
                        return (<Select.Option key={c.id} value={c.id}>{c.name}</Select.Option>)
                    })}
                </Select>
            </Form.Item>
            <Form.Item
                name="amount"
                label="账单金额"
                rules={[{ required: true, message: '请选择账单类别!' }]}
            >
                <InputNumber min={0} step={0.1} precision={2} />
            </Form.Item>
            <Form.Item {...tailLayout}>
                <Button type="primary" htmlType="submit">
                    新增账单
                </Button>
                <Button
                    style={{ margin: '0 8px'}}
                    onClick={() => {
                        form.resetFields();
                    }}
                >
                    重置
                </Button>
            </Form.Item>
        </Form>
    );
};

export default BillAddForm;