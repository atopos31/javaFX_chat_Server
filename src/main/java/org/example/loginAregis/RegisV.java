package org.example.loginAregis;

import org.example.database.Database;
import org.example.database.databasecontrol.MysqlFind;
import org.example.struct.Regisdata;
import org.example.struct.Response.CommonResponse;
import org.example.struct.Userdata;
import org.hibernate.Transaction;

public class RegisV {
    public static CommonResponse RegisRev(Regisdata regisdata) {
        CommonResponse regisresponse = new CommonResponse();

        if(MysqlFind.KeyAllFirst("email",regisdata.getEmail()) != null) {
            regisresponse.setCode(400);
            regisresponse.setMsg("该邮箱已被注册！");
            return regisresponse;
        }

        System.out.println("email:" + regisdata.getEmail());
        if(!Database.jedis.exists(regisdata.getEmail())) {
             regisresponse.setCode(400);
             regisresponse.setMsg("验证码未发送或以失效！");

             return regisresponse;
        }
        System.out.println(regisdata.getVerifyCode());
        System.out.println("redis"+Database.jedis.get(regisdata.getEmail()));
        if(!regisdata.getVerifyCode().equals(Database.jedis.get(regisdata.getEmail()))) {
             regisresponse.setCode(400);
             regisresponse.setMsg("验证码错误！");

             return regisresponse;
        }

        Userdata userdata = new Userdata();
        Transaction tx = Database.session.beginTransaction();
        userdata.setUsername(regisdata.getUsername());
        userdata.setEmail(regisdata.getEmail());
        userdata.setPassword(regisdata.getPassword());
        Database.session.save(userdata);
        tx.commit();

        regisresponse.setCode(200);
        regisresponse.setMsg("注册成功！");

        return regisresponse;
    }
}
