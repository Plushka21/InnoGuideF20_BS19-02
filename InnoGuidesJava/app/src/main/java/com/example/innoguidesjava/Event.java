package com.example.innoguidesjava;

/**
 * Class for events
 */

import java.io.Serializable;

public class Event implements Serializable {

    // Each event has name, date, time and place
    private int ID;
    private String name, date, time, place, poster;

    public Event(int ID, String name, String date, String time, String place, String poster){
        this.ID = ID;
        this.name = name;
        this.date = date;
        this.time = time;
        this.place = place;
        this.poster = poster;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getPoster() {
        return poster;
    }
}
