package com.example.demo2;
public class Transactions {
    private int id;
    private String type;
    private int amt;
    private String categ;
    private String date;


    public Transactions(int id,String type,int amt,String categ,String date){
        this.id=id;
        this.type=type;
        this.amt=amt;
        this.categ=categ;
        this.date=date;
    }


    public int getId() { return id; }
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
