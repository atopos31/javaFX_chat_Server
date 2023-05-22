# HXchat--服务端
## [客户端](https://github.com/atopos31/javaFX_chat)
## 沈阳理工大学java课程设计--基于TCP协议的聊天软件
使用hibernate操作mysql数据库，保存用户信息，jedis操作redis，保存验证码，使用json与客户端交互。
*src/main/resources/hibernate.cfg.xml*
```
 <!-- 数据库连接配置 -->
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://127.0.0.1:3306/chatdata</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">980845</property>
```
*org.example.database.Database*
```
    String URL = "your servr address";
    int RedisPort = 6379;
    String RedisPass = "your password";
```
