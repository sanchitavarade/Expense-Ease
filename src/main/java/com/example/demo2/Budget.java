package com.example.demo2;
public class Budget{
    private String categ;
    private int limit;
    private String id;
    private int TotalTrans;

    public Budget(String categ, int limit, int TotalTrans){
        this.id = Integer.toString(AlertConnector.user).concat(categ);
        this.categ=categ;
        this.limit=limit;
        this.TotalTrans=TotalTrans;
    }
    public String getCateg(){
        return this.categ;
    }
    public int getLimit(){
        return this.limit;
    }
    public int getTotalTrans(){return this.TotalTrans;}

}