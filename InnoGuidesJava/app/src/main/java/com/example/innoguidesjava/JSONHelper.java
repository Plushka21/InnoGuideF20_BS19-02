package com.example.innoguidesjava;

import android.content.Context;
import android.icu.number.Precision;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.innoguidesjava.R;

public class JSONHelper {

    public static List<Place> readPlaceJSONFile(Context context) throws IOException, JSONException {
        List<Place> places = new ArrayList<>();

        String textPlace = readText(context, R.raw.innoguide_public_place);
        String placePhone = readText(context, R.raw.innoguide_public_owner_contact);
        String placeCategory = readText(context, R.raw.innoguide_public_category_list_places);
        String placeFeedback = readText(context, R.raw.innoguide_public_feedback);
        String placeDays = readText(context, R.raw.innoguide_public_place_working_days);
        String placeTime = readText(context, R.raw.innoguide_public_place_working_time);

        JSONArray jsonRoot = new JSONArray(textPlace);
        JSONArray phones = new JSONArray(placePhone);
        JSONArray categories = new JSONArray(placeCategory);
        JSONArray feedback = new JSONArray(placeFeedback);
        JSONArray days = new JSONArray(placeDays);
        JSONArray time = new JSONArray(placeTime);


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
                if (pRat.getString("place_name").equals(pname)) {
                    rating += pRat.getDouble("rating");
                    n += 1;
                }
            }
            if (n != 0){
                DecimalFormat d = new DecimalFormat("#.##");
                rating /= n;
                rating = Double.parseDouble(d.format(rating));
            }


            String[] working_time = null;
            for (int j = 0; j < time.length(); j++){
                JSONObject pTime= time.getJSONObject(j);
                if (pTime.getString("pname").equals(pname)){
                    working_time = pTime.getString("working_time").split(",");
                }
            }

            // Create new place and add it into array
            Place p = new Place(pname, num, address, c1, c2, cat, rating);
            p.setWorking_time(working_time);
            places.add(i, p);
        }
        return places;
    }

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
}

