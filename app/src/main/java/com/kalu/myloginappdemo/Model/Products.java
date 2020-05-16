package com.kalu.myloginappdemo.Model;

public class Products {

    String pname;
    String vname;
    String pquantity;
    String pdate;

    public Products() {
    }

    public Products(String pname, String vname, String pquantity, String pdate) {
        this.pname = pname;
        this.vname = vname;
        this.pquantity = pquantity;
        this.pdate = pdate;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getPquantity() {
        return pquantity;
    }

    public void setPquantity(String pquantity) {
        this.pquantity = pquantity;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }
}
