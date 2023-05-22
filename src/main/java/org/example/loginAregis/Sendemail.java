package org.example.loginAregis;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.example.database.Database;
import org.example.database.databasecontrol.MysqlFind;
import org.example.struct.Response.CommonResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Random;

public class Sendemail {
    public static CommonResponse SendTo(String email)  {
        CommonResponse emailReponse = new CommonResponse();

        if(MysqlFind.KeyAllFirst("email",email) != null) {
            emailReponse.setCode(400);
            emailReponse.setMsg("该邮箱已被注册！");
            return emailReponse;
        }

        String Vkey = email + "vkey";

        if(Database.jedis.exists(Vkey)) {
            Long tiime = Database.jedis.ttl(Vkey);

            emailReponse.setCode(400);
            emailReponse.setMsg("请求过于频繁,请" + tiime + "秒后重试");
            return emailReponse;
        }

        int RandomNumber = getRandomNumber();
        try {
            Send(email,RandomNumber);
        } catch (MessagingException e) {
            emailReponse.setCode(400);
            emailReponse.setMsg("验证码发送失败");
            return emailReponse;
        }

        Database.jedis.setex(email + "vkey",60, "冷却中");

        Database.jedis.setex(email,300 ,String.valueOf(RandomNumber));

        emailReponse.setCode(200);
        emailReponse.setMsg("发送成功！5分钟内有效！");

        return emailReponse;

    }

    private static int getRandomNumber() {
        //获取六位随机数
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;

        return randomNumber;
    }

    public static Boolean Send(String email , int randomNumber) throws MessagingException {
        // 获取当前日期和时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        // 服务器地址:
        String smtp = "smtp.qq.com";
        // 登录用户名:
        String username = "your email address";
        // 登录口令:
        String password = "your email password";
        // 连接到SMTP服务器587端口:
        Properties props = new Properties();
        props.put("mail.smtp.host", smtp); // SMTP主机名
        props.put("mail.smtp.port", "587"); // 主机端口号
        props.put("mail.smtp.auth", "true"); // 是否需要用户认证
        props.put("mail.smtp.starttls.enable", "true"); // 启用TLS加密
        // 获取Session实例:
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        // 设置debug模式便于调试:
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        // 设置发送方地址:
        message.setFrom(new InternetAddress("2945294768@qq.com"));
        // 设置接收方地址:
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        // 设置邮件主题:
        message.setSubject("HXchat--注册验证", "UTF-8");
        // 设置邮件正文:
        message.setText("尊敬的" + email + "\n" + "您于" + formattedDateTime +"注册HXchat。\n" + "您的验证码为:" + randomNumber, "UTF-8");
        // 发送:
        Transport.send(message);
        return true;
    }
}
