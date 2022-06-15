package edu.skku.cs.finalproject;

public class forschedule {
    private String id;
    private String shc_name;
    private int month;
    private int day;
    private int time;

    public void setId(String id) {
        this.id = id;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setShc_name(String shc_name) {
        this.shc_name = shc_name;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getTime() {
        return time;
    }

    public String getShc_name() {
        return shc_name;
    }
}
