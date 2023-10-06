package com.example.demo2;

public class saving {
    private int id;
    private String date;
    private int saving;

    public saving(int id,String date, int saving){
        this.id=id;
        this.date=date;
        this.saving=saving;
    }

    public int getId() { return id; }
    public String getDate(){
        return date;
    }
    public int getSaving(){
        return saving;
    }
}