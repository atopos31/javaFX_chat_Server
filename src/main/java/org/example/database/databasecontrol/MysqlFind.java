package org.example.database.databasecontrol;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.database.Database;
import org.example.struct.Userdata;

import java.util.List;


public class MysqlFind {
    public static Userdata KeyAllFirst(String Key,String Value) {
        System.out.println("开始查询");
        List<Userdata> userList = null;
            CriteriaBuilder criteriaBuilder = Database.session.getCriteriaBuilder();
            System.out.println(1);
            CriteriaQuery<Userdata> query = criteriaBuilder.createQuery(Userdata.class);

            System.out.println(2);
            Root<Userdata> root = query.from(Userdata.class);
            System.out.println(3);
            query.select(root).where(criteriaBuilder.equal(root.get(Key), Value));
            System.out.println(4);
            userList = Database.session.createQuery(query).getResultList();
            System.out.println(5);
        if (userList.isEmpty()) {
            System.out.println("查找为空");
            return null;
        } else {
            System.out.println("查找到" + userList.get(0));
            System.out.println(userList.get(0));
            return userList.get(0);
        }
    }
}
