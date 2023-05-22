package org.example.struct;

import jakarta.persistence.*;

@Entity
@Table(name = "userrs")
public class Userdata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username" )
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return this.username;
    }
}
