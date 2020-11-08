package com.example.innoguidesjava;

public class Place {

    private String name, number, address, time[], days[];
    private double rating;
    private final double c1, c2; // c1,c2 - coordinates

    public Place(String name, String number, String address, double c1, double c2, String time[], String days[]){
        this.name = name;
        this.number = number;
        this.address = address;
        this.c1 = c1;
        this.c2 = c2;
        this.time = time;
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getC1() {
        return c1;
    }

    public double getC2() {
        return c2;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String[] getTime() {
        return time;
    }

    public String[] getDays() {
        return days;
    }
}
