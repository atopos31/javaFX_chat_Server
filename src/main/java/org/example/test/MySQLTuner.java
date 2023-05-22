package org.example.test;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.struct.Userdata;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
//irtdpsxgxgrrdhea
@Entity // 标注该类为实体类
@Table(name = "users") // 指定关联的数据库表名
class User {

    @Id // 标注该属性为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 指定主键生成策略
    private Long id;

    @Column(name = "username") // 指定该属性对应数据库表中的列名
    private String username;

    @Column(name = "password")
    private String password;

    public void setUserName(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    // 省略 getter 和 setter 方法
}


public class MySQLTuner {
    public static void main(String[] args) {
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
        Session session = sessionFactory.openSession();


        Transaction tx = null;
        try {
            //开始一个数据库事务。beginTransaction() 方法返回一个 Transaction 对象，该对象用于管理数据库事务操作。
            tx = session.beginTransaction();
            Userdata user = new Userdata();
            user.setUsername("hackerxiao");
            user.setEmail("294294768@qq.com");
            user.setPassword("xiaojiaxing678");
            session.save(user);
            //提交数据库事务。commit() 方法将之前的操作作为一个事务提交到数据库，使得之前的保存操作生效。
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }

//        var columnValue = "hackerxiao";
//
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Userdata> query = criteriaBuilder.createQuery(Userdata.class);
//        Root<Userdata> root = query.from(Userdata.class);
//        query.select(root).where(criteriaBuilder.equal(root.get("email"), "2945294768@qq.com"));
//        List<Userdata> userList = session.createQuery(query).getResultList();
//
//        if (!userList.isEmpty()) {
//            System.out.println(userList.get(0).getUsername());
//        } else {
//            System.out.println("查询结果为空!");
//        }

        session.close();

    }
}
