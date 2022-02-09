package com.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {
    public static void main(String[] args) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("MMMMMMM d");
        DateFormat df1 = new SimpleDateFormat("H:mm");
        String dateString = df.format(date);
        String timeString = df1.format(date);
        System.out.println(dateString + " " + timeString);
    }
}
