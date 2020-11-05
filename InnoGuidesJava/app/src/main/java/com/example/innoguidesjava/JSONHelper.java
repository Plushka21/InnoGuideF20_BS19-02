package com.example.innoguidesjava;

import android.content.Context;
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
import java.util.ArrayList;
import java.util.List;

import com.example.innoguidesjava.R;

public class JSONHelper {

    public static List<Place> readPlaceJSONFile(Context context) throws IOException, JSONException {
        String textPlace = readText(context, R.raw.innoguide_public_place);
        String placePhone = readText(context, R.raw.innoguide_public_owner_contact);
        String placeAddress = readText(context, R.raw.innoguide_public_buildings);
        String placeTime = readText(context, R.raw.innoguide_public_place_working_time);
        String placeDays = readText(context, R.raw.innoguide_public_place_working_days);
        List<Place> places = new ArrayList<>();

        JSONArray jsonRoot = new JSONArray(textPlace);
        JSONArray phones = new JSONArray(placePhone);
        JSONArray addresses = new JSONArray(placeAddress);
        JSONArray Time = new JSONArray(placeTime);
        JSONArray Days = new JSONArray(placeDays);

        for (int i = 0; i < jsonRoot.length(); i++){
            JSONObject placeInfo = jsonRoot.getJSONObject(i);
            // Get name of place
            String pname = placeInfo.getString("pname");
            //Get rating of place
            double rating = placeInfo.getDouble("rating");
            // Get coordinates of place
            String coor = placeInfo.getString("acoordinates");
            String[] c;
            c = coor.split(",");
            double c1 = Double.parseDouble(c[0]);
            double c2 = Double.parseDouble(c[1]);

            // Find phone number of place in other DB
            String num = "";
            for (int j = 0; j < phones.length(); j++){
                JSONObject pNum = phones.getJSONObject(j);
                if (pNum.getString("pname").equals(pname)){
                    num = pNum.getString("contact_details");
                    break;
                }
            }

            // Find physical address of place in other DB
            String address = "";
            for (int j = 0; j < addresses.length(); j++){
                JSONObject pAd = addresses.getJSONObject(j);
                if (pAd.getString("bcoordinates").equals(coor)){
                    address = pAd.getString("bstreet");
                    break;
                }
            }

            // Find wortking time of place in other DB
            String time[] = new String[7];
            for (int j = 0; j < Time.length(); j++){
                JSONObject pT = Time.getJSONObject(j);
                if (pT.getString("pname").equals(pname)){
                    time = pT.getString("working_time").split(",");
                    break;
                }
            }

            String days[] = new String[7];
            for (int j = 0; j < Days.length(); j++){
                JSONObject pD = Days.getJSONObject(j);
                if (pD.getString("pname").equals(pname)){
                    days = pD.getString("working_days").split(",");
                    break;
                }
            }

            Place p = new Place(pname, num, address, c1, c2, time, days);
            p.setRating(rating);
            places.add(i, p);
        }
        return places;
    }

    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String s= null;
        while((  s = br.readLine())!=null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
}

