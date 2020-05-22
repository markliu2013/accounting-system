import React from 'react';
import { Layout, Divider } from 'antd';

import BillTable from './BillTable';
import BillUpload from './BillUpload';
import BillAddForm from './BillAddForm';
import BillSearchForm from './BillSearchForm';

import 'antd/dist/antd.css';
import './App.css';

const { Header, Footer, Sider, Content } = Layout;




const App = () => {

    return (
        <>
        <Layout>
            <Header><h1>记账应用</h1></Header>
            <Layout>
                <Content>
                    <BillTable />
                </Content>
                <Sider width={450}>
                    <BillUpload />
                    <Divider dashed={true} style={{borderColor: '#999'}} />
                    <BillAddForm />
                    <Divider dashed={true} style={{borderColor: '#999'}} />
                    <BillSearchForm />
                </Sider>
            </Layout>
            <Footer>Powered by me.</Footer>
        </Layout>
        </>
    )

}

export default App;
