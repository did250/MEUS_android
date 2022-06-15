package edu.skku.cs.finalproject;

public class fordelete {
    private String id;
    private String shc_name;
    private int month;
    private int day;
    private int time;

    public void setTime(int time) {
        this.time = time;
    }

    public void setShc_name(String shc_name) {
        this.shc_name = shc_name;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShc_name() {
        return shc_name;
    }

    public int getTime() {
        return time;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getId() {
        return id;
    }
}
