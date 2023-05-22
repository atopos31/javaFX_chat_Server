package org.example.struct.Response;

public class LRreponse {
    int Code;
    String Msg;
    String Username;

    String Email;

    public LRreponse() {}

    public void setCode(int code) {
        this.Code = code;
    }

    public void setMsg(String msg) {
        this.Msg = msg;
    }

    public void setEmail(String email) {
        this.Email =email;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public int getCode() {
        return this.Code;
    }

    public String getUsername() {
        return this.Username;
    }
}
