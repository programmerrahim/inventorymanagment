package com.kalu.myloginappdemo.Model;

public class Sales {
    String sname;
    String svname;
    String squantity;
    String sdate;

    public Sales() {
    }

    public Sales(String sname, String svname, String squantity, String sdate) {
        this.sname = sname;
        this.svname = svname;
        this.squantity = squantity;
        this.sdate = sdate;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSvname() {
        return svname;
    }

    public void setSvname(String svname) {
        this.svname = svname;
    }

    public String getSquantity() {
        return squantity;
    }

    public void setSquantity(String squantity) {
        this.squantity = squantity;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }
}
