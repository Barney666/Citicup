package com.citicup.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {

    private String username;
    private String password;
    private String mark;
    private ArrayList footprint=new ArrayList();


    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList getFootprint() {
        return footprint;
    }

    public void setFootprint(ArrayList footprint) {
        this.footprint = footprint;
    }

}
