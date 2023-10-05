package com.example.demo2;
public class BorrowLend {
    private String ddate;

    private String type;
    private String desc;

    private int amt;

    public BorrowLend(String ddate, String type, String desc, int amt){
        this.ddate=ddate;
        this.type=type;
        this.desc=desc;
        this.amt=amt;
    }
    public String getDdate(){
        return ddate;
    }
    public String getType(){
        return type;
    }
    public String getDesc(){
        return desc;
    }
    public int getAmt() { return amt; }
}
