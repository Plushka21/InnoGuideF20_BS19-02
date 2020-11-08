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
        List<Place> places = new ArrayList<>();

        JSONArray jsonRoot = new JSONArray(textPlace);
        JSONArray phones = new JSONArray(placePhone);
        JSONArray addresses = new JSONArray(placeAddress);

        for (int i = 0; i < jsonRoot.length(); i++){
            JSONObject placeInfo = jsonRoot.getJSONObject(i);
            String pname = placeInfo.getString("pname");
            double rating = placeInfo.getDouble("rating");
            String coor = placeInfo.getString("address");
            String[] c;
            c = coor.split(",");
            double c1 = Double.parseDouble(c[0]);
            double c2 = Double.parseDouble(c[1]);

            String num = "";
            for (int j = 0; j < phones.length(); j++){
                JSONObject pNum = phones.getJSONObject(j);
                if (pNum.getString("pname").equals(pname)){
                    num = pNum.getString("contact_details");
                    break;
                }
            }

            String address = "";
            for (int j = 0; j < addresses.length(); j++){
                JSONObject pAd = addresses.getJSONObject(j);
                if (pAd.getString("bcoordinates").equals(coor)){
                    address = pAd.getString("bstreet");
                    break;
                }
            }
            Place p = new Place(pname, num, address, c1, c2);
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

