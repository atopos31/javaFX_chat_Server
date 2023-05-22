package org.example.loginAregis;

import com.google.gson.Gson;
import org.example.database.Database;
import org.example.database.databasecontrol.MysqlFind;
import org.example.server.Server;
import org.example.struct.Logindata;
import org.example.struct.Response.LRreponse;
import org.example.struct.Userdata;

import java.util.Objects;

public  class LoginV {
    public static LRreponse LoginRev(Logindata logindata) {
        LRreponse lRreponse = new LRreponse();
        Gson gson = new Gson();

        System.out.println("要查找的邮箱是：" + logindata.getEmail());
        Userdata userdata = MysqlFind.KeyAllFirst("email",logindata.getEmail());

        if (userdata == null || !userdata.getPassword().equals(logindata.getPassword())) {
            lRreponse.setCode(400);
            lRreponse.setMsg("账号或密码错误！");
            lRreponse.setUsername(null);
            return lRreponse;
        } else {
            lRreponse.setCode(200);
            lRreponse.setMsg("登陆成功！");
            System.out.println("username" + userdata.getUsername());
            lRreponse.setUsername(userdata.getUsername());
            lRreponse.setEmail(userdata.getEmail());
            return lRreponse;
        }

    }
}
