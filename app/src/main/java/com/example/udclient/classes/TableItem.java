package com.example.udclient.classes;

import java.io.Serializable;

public class TableItem implements Serializable {

    private String tableName;
    private String ownerName;
    private String tableCode;
    private String tablePassword;
    public TableItem(String TableName,String OwnerName){
        tableName =TableName;
        ownerName = OwnerName;
    }

    public TableItem(String TableName,String OwnerName,String TableCode,String TablePassword){
        tableName =TableName;
        ownerName = OwnerName;
        tableCode = TableCode;
        tablePassword = TablePassword;
    }

    public String getTableName(){
        return tableName;
    }

    public String getOwnerName(){
        return ownerName;
    }

    public String getTableCode(){
        return tableCode;
    }

    public String getTablePassword() {return tablePassword;}
}
