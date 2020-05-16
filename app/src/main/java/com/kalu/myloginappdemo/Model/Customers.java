package com.kalu.myloginappdemo.Model;

public class Customers {
    String cname;
    String caddress;
    String cphone;

    public Customers() {
    }

    public Customers(String cname, String caddress, String cphone) {
        this.cname = cname;
        this.caddress = caddress;
        this.cphone = cphone;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCaddress() {
        return caddress;
    }

    public void setCaddress(String caddress) {
        this.caddress = caddress;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }
}
