package org.example.struct;

import org.example.struct.JSON.JSONbase;

public class Logindata extends JSONbase {
    private String Email;
    private String Password;
    public Logindata(){}

    public String getEmail() {
        return this.Email;
    }

    public String getPassword() {
        return this.Password;
    }
}
