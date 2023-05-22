package org.example.struct;

import org.example.struct.JSON.JSONbase;

public class Regisdata extends JSONbase {
    private String Username;
    private String Password;
    private String Email;
    private String VerifyCode;

    public Regisdata() {}

    public String getUsername() {
        return this.Username;
    }

    public String getPassword() {
        return this.Password;
    }

    public String getEmail() {
        return this.Email;
    }

    public String getVerifyCode() {
        return this.VerifyCode;
    }
}
