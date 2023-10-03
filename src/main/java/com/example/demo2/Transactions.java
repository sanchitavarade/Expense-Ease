package com.example.demo2;
public class Transactions {
    private String type;
    private int amt;
    private String categ;
    private String date;

    public Transactions(String type,int amt,String categ,String date){
        this.type=type;
        this.amt=amt;
        this.categ=categ;
        this.date=date;
    }
    public String getType(){
        return type;
    }
    public int getAmt(){
        return amt;
    }
    public String getCateg(){
        return categ;
    }
    public String getDate(){
        return date;
    }
}
