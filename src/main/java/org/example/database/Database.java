package org.example.database;


import org.example.struct.Userdata;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import redis.clients.jedis.Jedis;

public class Database {
    String URL = "your servr address";
    int RedisPort = 6379;
    String RedisPass = "your password";
    public static Jedis jedis;
    public static Session session;
    public void start() {
        this.mysqlStart();
        this.redisStart();
    }

    private void mysqlStart() {
        // 加载 hibernate.cfg.xml 配置文件
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        //构建 Hibernate 实体类和数据库表之间的映射关系。
        MetadataSources sources = new MetadataSources(registry);
        //实体类与数据库表进行映射。
        sources.addAnnotatedClass(Userdata.class);
        //构建 Metadata 对象。Metadata 对象保存了实体类和数据库表的映射信息。
        Metadata metadata = sources.getMetadataBuilder().build();
        //创建 SessionFactory 对象。SessionFactory 对象是一个线程安全的重量级对象，用于创建和管理 Session 对象。
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        //创建一个 Session 对象，表示一个与数据库的会话，通过 Session 对象可以进行数据库的增删改查等操作。
        session = sessionFactory.openSession();
        System.out.println("Mysql数据库连接成功！映射：" + Userdata.class);
    }

    private void redisStart() {
        jedis = new Jedis(URL, RedisPort);
        // 身份验证
        jedis.auth(RedisPass);
        // 连接到Redis服务器
        jedis.connect();
        System.out.println("Redis数据库" + URL + ":" + RedisPort + "连接成功");
    }
}


