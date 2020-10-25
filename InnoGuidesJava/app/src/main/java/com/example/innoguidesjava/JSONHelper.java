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
        String jsonText = readText(context, R.raw.innoguide_public_place);
        List<Place> places = new ArrayList<>();

        JSONArray jsonRoot = new JSONArray(jsonText);

        for (int i = 0; i < jsonRoot.length(); i++){
            JSONObject object = jsonRoot.getJSONObject(i);
            String pname = object.getString("pname");
            double rating = object.getDouble("rating");
            String coor = object.getString("address");
            String[] c;
            c = coor.split(",");
            double c1 = Double.parseDouble(c[0]);
            double c2 = Double.parseDouble(c[1]);

            Place p = new Place(pname, c1, c2);
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
