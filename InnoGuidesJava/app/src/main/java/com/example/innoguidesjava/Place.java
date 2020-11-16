package com.example.innoguidesjava;

public class Place {

    private String name, number, address, category;
    private double rating, c1, c2; // c1,c2 - coordinates
    private String[] working_time;

    public Place(String name, String number, String address, double c1, double c2, String category, double rating) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.c1 = c1;
        this.c2 = c2;
        this.category = category;
        this.rating = rating;
        working_time = new String[7];
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


    public String[] getWorking_time() {
        return working_time;
    }

    public void setWorking_time(String[] working_time) {
        this.working_time = working_time;
    }
}
