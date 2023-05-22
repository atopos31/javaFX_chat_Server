package org.example.test;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class main {
    public static void main(String[] args) throws MessagingException {
// 服务器地址:
        String smtp = "smtp.qq.com";
// 登录用户名:
        String username = "2945294768@qq.com";
// 登录口令:
        String password = "irtdpsxgxgrrdhea";
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
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("2945294768@qq.com"));
// 设置邮件主题:
        message.setSubject("Hello", "UTF-8");
// 设置邮件正文:
        message.setText("Hi Xiaoming...", "UTF-8");
// 发送:
        Transport.send(message);
    }
}
