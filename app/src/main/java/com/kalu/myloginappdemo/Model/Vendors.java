package com.kalu.myloginappdemo.Model;

public class Vendors {
    String vname;
    String vaddress;
    String vphone;

    public Vendors() {
    }

    public Vendors(String vname, String vaddress, String vphone) {
        this.vname = vname;
        this.vaddress = vaddress;
        this.vphone = vphone;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getVaddress() {
        return vaddress;
    }

    public void setVaddress(String vaddress) {
        this.vaddress = vaddress;
    }

    public String getVphone() {
        return vphone;
    }

    public void setVphone(String vphone) {
        this.vphone = vphone;
    }
}
