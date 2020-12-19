package com.example.udclient.classes;


public class TableItem {

    private String tableName;
    private String ownerName;
    public TableItem(String TableName,String OwnerName){
        tableName =TableName;
        ownerName = OwnerName;
    }

    public String getTableName(){
        return tableName;
    }

    public String getOwnerName(){
        return ownerName;
    }
}
