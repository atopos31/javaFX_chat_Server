package org.example.test;

import redis.clients.jedis.Jedis;

public class Redis {
    public static void main(String[] args) {
        // 创建Jedis实例
        Jedis jedis = new Jedis("your server address", 6379);

        // 身份验证
        jedis.auth("your password");

        // 连接到Redis服务器
        jedis.connect();
        jedis.set("key", "value");

        // 执行Redis操作
        // ...

        // 关闭连接
        jedis.close();
    }
}
