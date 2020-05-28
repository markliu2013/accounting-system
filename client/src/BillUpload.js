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
                    message.success('Upload Successfully!');
                    const { pagination, filters, sorter, query } = billTableState;
                    handleBillTableChange(pagination, filters, sorter, query);
                    setFileList([]);
                } else {
                    message.error('Upload Failed');
                }
            },
            error: () => {
                setUploading(false);
                message.error('Upload Failed');
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
                <p className="ant-upload-text">Click or drag csv file to this area to upload</p>
                <p className="ant-upload-hint">
                    Support for a single or bulk upload. Please Download the template csv file, only the template format supported.
                </p>
            </Dragger>

            <Button
                type="primary"
                onClick={handleUpload}
                disabled={fileList.length === 0}
                loading={uploading}
                style={{ marginTop: 16 }}
                >
                {uploading ? 'Uploading...' : 'Start Upload'}
            </Button>
            <a href="bill.csv" style={{ marginLeft: 16 }}>Download CSV Template File</a>
        </div>
    );
};

export default BillUpload;