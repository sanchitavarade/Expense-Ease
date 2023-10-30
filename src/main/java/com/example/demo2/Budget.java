package com.example.demo2;
public class Budget{
    private String categ;
    private int limit;
    private String id;

    public Budget(String categ, int limit){
        this.id = Integer.toString(AlertConnector.user).concat(categ);
        this.categ=categ;
        this.limit=limit;
    }
    public String getCateg(){
        return this.categ;
    }
    public int getLimit(){
        return this.limit;
    }
}