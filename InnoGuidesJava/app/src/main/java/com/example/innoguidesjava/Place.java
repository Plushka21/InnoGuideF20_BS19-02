package com.example.innoguidesjava;

public class Place {

    private String name, number, address, category;
    private double rating, c1, c2; // c1,c2 - coordinates

    public Place(String name, String number, String address, double c1, double c2, String category){
        this.name = name;
        this.number = number;
        this.address = address;
        this.c1 = c1;
        this.c2 = c2;
        this.category = category;
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

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public double getC2() {
        return c2;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }
}
