package org.example.server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jayway.jsonpath.JsonPath;
import org.example.loginAregis.LoginV;
import org.example.loginAregis.RegisV;
import org.example.loginAregis.Sendemail;
import org.example.struct.JSON.Flag;
import org.example.struct.*;
import org.example.struct.Response.CommonResponse;
import org.example.struct.Response.FRRreponse;
import org.example.struct.Response.LRreponse;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class Server {
    public static Map<String, Handler> connectedClients = new HashMap<>();
    public static Map<String,String> onlineUser = new HashMap<>();
    ServerSocket serverSocket;
    public void start() throws IOException {
        serverSocket = new ServerSocket(1004);
        System.out.println("server is listen on 10004");
        for (;;) {
            Socket sock = serverSocket.accept();
            System.out.println("connected from " + sock.getRemoteSocketAddress());
            Thread t = new Handler(sock);
            t.start();
        }
    }
}

class Handler extends Thread {
    String email;
    String username;
    PrintWriter  writer;
    BufferedReader reader;
    Socket sock;
    public Handler(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        try (InputStream input = this.sock.getInputStream()) {
            try (OutputStream output = this.sock.getOutputStream()) {
                writer = new PrintWriter(sock.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                handle();
            }
        } catch (Exception e) {
            //连接异常 将用户下线
            Server.onlineUser.remove(username);
            Server.connectedClients.remove(email);
            throw new RuntimeException(e);
        }
    }

    public void handle() throws IOException {
        String s = reader.readLine();

        String response = getMsg(s);

        System.out.println("response = " + response);

        if (response!=null) {
            writer.println(response);
        }

        // 关闭连接
        System.out.println(email + "连接关闭！");
        reader.close();
        writer.close();
        sock.close();
    }

    private String getMsg(String msg) throws IOException {
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(msg, JsonElement.class);
        String flag = JsonPath.read(element.toString(), "$.Flag");
        System.out.println("flag" + flag);

        if(flag.equals(Flag.FlagLogin)) {

            Logindata logindata = gson.fromJson(msg, Logindata.class);
            System.out.println("userdataemail = " + logindata.getEmail() + "userdatapassword" + logindata.getPassword());
            LRreponse reponse = LoginV.LoginRev(logindata);

            writer.println(gson.toJson(reponse));

            if (reponse.getCode() == 200) {
                System.out.println("开始监听啦");
                this.email = logindata.getEmail();
                this.username = reponse.getUsername();

                //加入在线用户列表
                Server.connectedClients.put(email, this);
                Server.onlineUser.put(username, email);

                Timer timer = new Timer();

                //进入卡死消息循环监听
                handleChatMessages();
            }
            return gson.toJson(reponse);

        }else if (flag.equals(Flag.FlagRegis)) {
            Regisdata regisdata = gson.fromJson(msg, Regisdata.class);
            CommonResponse regisresponse = RegisV.RegisRev(regisdata);
            return gson.toJson(regisresponse);
            //注册请求
        } else if (flag.equals(Flag.FlagSendEmail)) {

            ReceiveSendEmail receiveSendEmail = gson.fromJson(msg,ReceiveSendEmail.class);
            CommonResponse emailReponse = Sendemail.SendTo(receiveSendEmail.getEmail());

            return gson.toJson(emailReponse);
        }

        return null;
    }

    private void handleChatMessages() throws IOException {
        String message;
        String Reponse = null;
        Gson gson = new Gson();
        String onlineuserdata = gson.toJson(new FRRreponse(Server.onlineUser));
        for(Handler h:Server.connectedClients.values()) {
            h.writer.println(onlineuserdata);
        }
        while ((message = reader.readLine()) != null) {

            if(message.equals("exit")) {
                System.out.println(email + "断开连接");
                //移除
                Server.connectedClients.remove(email);
                Server.onlineUser.remove(username);
                return;
            }

            JsonElement element = gson.fromJson(message, JsonElement.class);
            String flag = JsonPath.read(element.toString(), "$.Flag");

            //处理各种消息
            if(flag.equals(Flag.FlagFriendRequire)) {
                FRRreponse frRreponse = new FRRreponse(Server.onlineUser);
                Reponse = gson.toJson(frRreponse);
                System.out.println(Reponse);
                writer.println(Reponse);
            } else if(flag.equals(Flag.FlagSendMessage)) {
                SendMessage sendMessage = gson.fromJson(message, SendMessage.class);
                System.out.println("用户：" + email + "向" + sendMessage.getToemail() + "发送" + sendMessage.getMessage());

                ReceiveMessage receiveMessage = new ReceiveMessage();
                Handler tohandler = Server.connectedClients.get(sendMessage.getToemail());
                if(tohandler==null) {
                    receiveMessage.setEmail(sendMessage.getToemail());
                    receiveMessage.setMessage("[该用户处于离线状态]");
                    Reponse = gson.toJson(receiveMessage);
                    writer.println(Reponse);
                } else {
                    receiveMessage.setEmail(email);
                    receiveMessage.setMessage(sendMessage.getMessage());
                    Reponse = gson.toJson(receiveMessage);
                    tohandler.writer.println(Reponse);
                }
            }
        }
    }
}
