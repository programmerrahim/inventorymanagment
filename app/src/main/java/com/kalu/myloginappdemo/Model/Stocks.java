package com.kalu.myloginappdemo.Model;

public class Stocks {
    String pname;
    String pquantity;

    public Stocks() {
    }

    public Stocks(String pname, String pquantity) {
        this.pname = pname;
        this.pquantity = pquantity;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPquantity() {
        return pquantity;
    }

    public void setPquantity(String pquantity) {
        this.pquantity = pquantity;
    }
}
