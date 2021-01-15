package com.example.udclient.classes;

import java.io.Serializable;

public class TableItem implements Serializable {

    private String id_meeting;
    private String name;
    private String code;
    private String password;
    public TableItem(String TableName,String OwnerName){
        id_meeting =TableName;
        name = OwnerName;
    }

    public TableItem(String TableName,String OwnerName,String TableCode,String TablePassword){
        id_meeting =TableName;
        name = OwnerName;
        code = TableCode;
        password = TablePassword;
    }

    public String getId_meeting(){
        return id_meeting;
    }

    public String getName(){
        return name;
    }

    public String getCode(){
        return code;
    }

    public String getPassword() {return password;}
}
