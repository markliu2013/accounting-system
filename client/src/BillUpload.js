import React from 'react';
import { Upload, Button, message } from 'antd';
import { InboxOutlined } from '@ant-design/icons';
import reqwest from 'reqwest';
import { observe, handleBillTableChange, initBillTableState } from './State'

const { Dragger } = Upload;

const BillUpload = () => {

    const [billTableState, setBillTableState] = React.useState(initBillTableState);
    const [fileList, setFileList] = React.useState([]);
    const [uploading, setUploading] = React.useState(false);

    React.useEffect(() => observe((newBillTableState) => {setBillTableState(newBillTableState)}));

    function handleUpload() {
        const formData = new FormData();
        fileList.forEach(file => {
            formData.append('files', file);
        });
        setUploading(true);
        reqwest({
            url: 'api/bills/upload',
            method: 'post',
            processData: false,
            data: formData,
            success: (data) => {
                setUploading(false);
                if (data.success) {
                    message.success('上传成功');
                    const { pagination, filters, sorter, query } = billTableState;
                    handleBillTableChange(pagination, filters, sorter, query);
                    setFileList([]);
                } else {
                    message.error('上传失败');
                }
            },
            error: () => {
                setUploading(false);
                message.error('上传失败');
            },
        });
    }

    const props = {
        name: 'files',
        multiple: true,
        accept: '.csv',
        onRemove: file => {
            const index = fileList.indexOf(file);
            let newFileList = fileList.slice();
            newFileList.splice(index, 1);
            setFileList(newFileList);
        },
        beforeUpload: (newFile, newFileList) => {
            setFileList([...fileList, ...newFileList]);
            return false;
        },
        fileList,
    };

    return (
        <div>
            <Dragger {...props}>
                <p className="ant-upload-drag-icon">
                    <InboxOutlined />
                </p>
                <p className="ant-upload-text">将CSV文件拖到此区域，然后点击提交。</p>
                <p className="ant-upload-hint">
                    支持一个或多个文件上传，只能上传固定的CSV文件，请下载模板文件参考。
                </p>
            </Dragger>

            <Button
                type="primary"
                onClick={handleUpload}
                disabled={fileList.length === 0}
                loading={uploading}
                style={{ marginTop: 16 }}
                >
                {uploading ? '上传中...' : '开始上传'}
            </Button>
            <a href="bill.csv" style={{ marginLeft: 16 }}>下载CSV模板文件</a>
        </div>
    );
};

export default BillUpload;