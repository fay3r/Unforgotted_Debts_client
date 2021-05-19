package com.example.udclient.classes;

import java.io.Serializable;

public class RegisterDto implements Serializable {
    private String nick;
    private String name;
    private String surname;
    private String email;
    private String password;

    public RegisterDto() {
    }

    public RegisterDto(String nick, String name, String surname, String email, String password) {
        this.nick = nick;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
