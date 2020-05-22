# 简易记账本

[https://github.com/xmindltd/hiring/tree/master/frontend-1](https://github.com/xmindltd/hiring/tree/master/frontend-1)

在线运行效果：[http://demo.zfwhub.com:4000/](http://demo.zfwhub.com:4000/)

已完成基本需求和附加需求。


## 安装
使用docker和docker compose安装，请先安装对应平台的docker和docker compose。

[https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/)

[https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/)

安装脚本：
```sh
  $ git clone --depth 1 https://github.com/markliu2013/accounting-system.git
  $ docker-compose up
```
应用入口：[http://127.0.0.1:4000/](http://127.0.0.1:4000/)

Swagger API：[http://localhost:8089/api/swagger-ui.html](http://localhost:8089/api/swagger-ui.html)

phpMyAdmin：[http://127.0.0.1:8084/](http://127.0.0.1:8084/)

**注意**：在本地搭建的docker环境，如果应用中数据为空，需要先将server目录下的accounting_system.sql文件导入数据库（可以在phpMyAdmin执行导入操作），之后才能进行添加账单和上传账单的操作，因为程序暂时没有提供账单类别的增删改查接口。


## 关联技术


1. [spring boot](https://spring.io/projects/spring-boot).
2. [ReactJS](https://create-react-app.dev/)
3. [Ant Design](https://ant.design/docs/spec/introduce)


## 思考过程
采用前后端分离架构，后端框架为springboot(spring mvc, jpa, spring)，前端框架为ReactJS + Ant Design。根据需求以及提供的数据，设计对应的一对多实体类模型，提供账单的增删改查RESTful API。分析前端的组件和State数据，由于State数据并不复杂，没有必要引入Redux进行状态管理，将需要全局管理的State提取到[State.js](https://github.com/markliu2013/accounting-system/blob/master/client/src/State.js)，提供发布和订阅接口。

## 遇到的问题以及思考
1. 后端异常的处理。后端应该根据项目的异常种类，定义异常代码，返回在接口返回的json数据中。各个异常种类自定义异常类，由Spring全局处理，避免在代码中出现try catch。参考资料：[https://www.toptal.com/java/spring-boot-rest-api-error-handling](https://www.toptal.com/java/spring-boot-rest-api-error-handling)
2. 统一后端返回的json格式，固定success字段表面处理是否正常，如果正常数据放在data中，如果不正常，code代表异常代码，message代表异常信息，前端可以依照此固定逻辑处理数据。后端定义了对应的类处理[BaseResponse.java](https://github.com/markliu2013/accounting-system/blob/master/server/src/main/java/com/zfwhub/bill/dto/BaseResponse.java)，[ResponseSuccess.java](https://github.com/markliu2013/accounting-system/blob/master/server/src/main/java/com/zfwhub/bill/dto/ResponseSuccess.java)，[ResponseError.java](https://github.com/markliu2013/accounting-system/blob/master/server/src/main/java/com/zfwhub/bill/dto/ResponseError.java)。
3. 前端和后端的时区问题。原则1：后端存储的时间统一为UTC时区。原则2：后端返回[current millis](https://currentmillis.com/), 前端根据客户端时区转换处理。
4. 上传和新增账单等操作后，保存页面的排序，分页，搜索条件等信息不变。解决方法：排序，分页，搜索条件等抽离到全局状态管理。
5. 前端输入的时间，mysql存储多出一秒。搜索解决，[https://blog.csdn.net/qq_38345296/article/details/99639376](https://blog.csdn.net/qq_38345296/article/details/99639376)
6. 需求问题。问题1：账单类别和账单都有支出或收入。（忽略账单类别中的）问题2：账单金额已经有收入和支出区分，提供的csv数据还是有正负数。（没有处理）
7. springboot+reactjs 架构的最佳实践思考，提供一个项目作为架构的骨架代码。
