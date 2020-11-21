package com.example.innoguidesjava;

import java.util.ArrayList;

public class Place {

    private String name, number, address, category;
    private double rating, c1, c2; // c1,c2 - coordinates
    private String[] working_time, photos;

    public Place(String name, String number, String address, double c1, double c2, String category, double rating, String[] working_time, String[] photos) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.c1 = c1;
        this.c2 = c2;
        this.category = category;
        this.rating = rating;
        this.working_time = working_time;
        this.photos = photos;
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

    public double getC2() {
        return c2;
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

    public String[] getPhotos(){
        return photos;
    }
}
