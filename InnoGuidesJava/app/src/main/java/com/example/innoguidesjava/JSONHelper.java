package com.example.innoguidesjava;

/**
 * Class to read data from .json files
 */

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class JSONHelper extends SQLiteOpenHelper{


    public JSONHelper(@Nullable Context context) {
            super(context, "random.db", null, 1);
        }

        // Create and return list with places
    public static List<Place> readPlaceJSONFile(Context context) throws IOException, JSONException {

        List<Place> places = new ArrayList<>();

        String textPlace = readText(context, R.raw.innoguide_place);
        String placePhone = readText(context, R.raw.innoguide_owner_contact);
        String placeCategory = readText(context, R.raw.innoguide_category_list_places);
        String placeFeedback = readText(context, R.raw.innoguide_feedback);
        String placeTime = readText(context, R.raw.innoguide_place_working_time);
        String placePhotos = readText(context, R.raw.innoguide_photo);

        JSONArray jsonRoot = new JSONArray(textPlace);
        JSONArray phones = new JSONArray(placePhone);
        JSONArray categories = new JSONArray(placeCategory);
        JSONArray feedback = new JSONArray(placeFeedback);
        JSONArray time = new JSONArray(placeTime);
        JSONArray photos = new JSONArray(placePhotos);

        for (int i = 0; i < jsonRoot.length(); i++) {
            JSONObject placeInfo = jsonRoot.getJSONObject(i);
            String pname = placeInfo.getString("pname");
            String address = placeInfo.getString("address");
            String coor = placeInfo.getString("acoordinates");

            // Get coordinates of the place
            String[] c;
            c = coor.split(",");
            double c1 = Double.parseDouble(c[0]);
            double c2 = Double.parseDouble(c[1]);

            // Get phone number of the owner
            String num = "";
            for (int j = 0; j < phones.length(); j++) {
                JSONObject pNum = phones.getJSONObject(j);
                if (pNum.getString("pname").equals(pname)) {
                    num = pNum.getString("contact_details");
                    break;
                }
            }

            // Get category of the place
            String cat = "";
            for (int j = 0; j < categories.length(); j++) {
                JSONObject pCat = categories.getJSONObject(j);
                if (pCat.getString("pname").equals(pname)) {
                    cat = pCat.getString("cname");
                    break;
                }
            }

            // Calculate average rating of the place based on feedback
            double rating = 0;
            int n = 0;
            for (int j = 0; j < feedback.length(); j++) {
                JSONObject pRat = feedback.getJSONObject(j);
                if (pRat.getString("pname").equals(pname)) {
                    rating += pRat.getDouble("rating");
                    n += 1;
                }
            }
            if (n != 0){
                DecimalFormat d = new DecimalFormat("#.##");
                rating /= n;
                rating = Double.parseDouble(d.format(rating));
            }

            // Get working time of each place
            String[] working_time = null;
            for (int j = 0; j < time.length(); j++){
                JSONObject pTime= time.getJSONObject(j);
                if (pTime.getString("pname").equals(pname)){
                    working_time = pTime.getString("working_time").split(",");
                    break;
                }
            }

            // Get names of photos
            String[] pictures = null;
            for (int j = 0; j < photos.length(); j++){
                JSONObject pPic = photos.getJSONObject(j);
                if (pPic.getString("pname").equals(pname)){
                    pictures = pPic.getString("photo").split(",");
                    break;
                }
            }

            // Create new place and add it into array
            Place p = new Place(pname, num, address, c1, c2, cat, rating, working_time, pictures);
            places.add(i, p);
        }
        return places;
    }

    // Create and return hashmap with places where key is name of place and value is element of call Place
    public static HashMap<String, Place> MapPlaceJSONFile(Context context) throws IOException, JSONException {

        HashMap<String, Place> places = new HashMap<>();

        String textPlace = readText(context, R.raw.innoguide_place);
        String placePhone = readText(context, R.raw.innoguide_owner_contact);
        String placeCategory = readText(context, R.raw.innoguide_category_list_places);
        String placeFeedback = readText(context, R.raw.innoguide_feedback);
        String placeTime = readText(context, R.raw.innoguide_place_working_time);
        String placePhotos = readText(context, R.raw.innoguide_photo);

        JSONArray jsonRoot = new JSONArray(textPlace);
        JSONArray phones = new JSONArray(placePhone);
        JSONArray categories = new JSONArray(placeCategory);
        JSONArray feedback = new JSONArray(placeFeedback);
        JSONArray time = new JSONArray(placeTime);
        JSONArray photos = new JSONArray(placePhotos);

        for (int i = 0; i < jsonRoot.length(); i++) {
            JSONObject placeInfo = jsonRoot.getJSONObject(i);
            String pname = placeInfo.getString("pname");
            String address = placeInfo.getString("address");
            String coor = placeInfo.getString("acoordinates");

            // Get coordinates of the place
            String[] c;
            c = coor.split(",");
            double c1 = Double.parseDouble(c[0]);
            double c2 = Double.parseDouble(c[1]);

            // Get phone number of the owner
            String num = "";
            for (int j = 0; j < phones.length(); j++) {
                JSONObject pNum = phones.getJSONObject(j);
                if (pNum.getString("pname").equals(pname)) {
                    num = pNum.getString("contact_details");
                    break;
                }
            }

            // Get category of the place
            String cat = "";
            for (int j = 0; j < categories.length(); j++) {
                JSONObject pCat = categories.getJSONObject(j);
                if (pCat.getString("pname").equals(pname)) {
                    cat = pCat.getString("cname");
                    break;
                }
            }

            // Calculate average rating of the place based on feedback
            double rating = 0;
            int n = 0;
            for (int j = 0; j < feedback.length(); j++) {
                JSONObject pRat = feedback.getJSONObject(j);
                if (pRat.getString("pname").equals(pname)) {
                    rating += pRat.getDouble("rating");
                    n += 1;
                }
            }
            if (n != 0){
                DecimalFormat d = new DecimalFormat("#.##");
                rating /= n;
                rating = Double.parseDouble(d.format(rating));
            }

            // Get working time of each place
            String[] working_time = null;
            for (int j = 0; j < time.length(); j++){
                JSONObject pTime= time.getJSONObject(j);
                if (pTime.getString("pname").equals(pname)){
                    working_time = pTime.getString("working_time").split(",");
                    break;
                }
            }

            // Get names of photos
            String[] pictures = null;
            for (int j = 0; j < photos.length(); j++){
                JSONObject pPic = photos.getJSONObject(j);
                if (pPic.getString("pname").equals(pname)){
                    pictures = pPic.getString("photo").split(",");
                    break;
                }
            }

            // Create new place and add it into array
            Place p = new Place(pname, num, address, c1, c2, cat, rating, working_time, pictures);
            places.put(pname, p);
        }
        return places;
    }

    // Create and return list with events
    public static ArrayList<Event> readEventsJSONFile(Context context) throws IOException, JSONException{
        ArrayList<Event> events = new ArrayList<>();

        String allEvents = readText(context, R.raw.innoguide_events);

        JSONArray jsonRoot = new JSONArray(allEvents);

        for (int i = 0; i < jsonRoot.length(); i++) {
            JSONObject event = jsonRoot.getJSONObject(i);
            int ID = event.getInt("ID");
            String ename = event.getString("ename");
            String date = event.getString("edate");
            String time = event.getString("etime");
            String place = event.getString("event_place");
            String poster = event.getString("poster");

            events.add(new Event(ID, ename, date, time, place, poster));
        }
        return events;
    }

    // Function to read text from file
    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

