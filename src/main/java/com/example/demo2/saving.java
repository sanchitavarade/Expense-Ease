package com.example.demo2;

public class saving {
    private String categ;
    private int saving;

    public saving(String categ, int saving){
        this.categ=categ;
        this.saving=saving;
    }
    public String getCateg(){
        return categ;
    }
    public int getSaving(){
        return saving;
    }
}