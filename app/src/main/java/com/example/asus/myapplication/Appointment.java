package com.example.asus.myapplication;

/**
 * Created by ASUS on 4/27/2018.
 *
 * create Appointment
 */

public class Appointment {


    private  String date;
    private  String time;
    private  String title;
    private  String detail;

    public Appointment(String date,String time, String title, String detail) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.detail = detail;
    }




    public String getDate() {
        return date;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }



    public String getDetail() {
        return detail;
    }

}
