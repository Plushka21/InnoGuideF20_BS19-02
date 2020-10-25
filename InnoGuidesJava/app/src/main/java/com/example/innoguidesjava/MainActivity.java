package com.example.innoguidesjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.innoguidesjava.R;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText email,password;
    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.aEmail);
        password = findViewById(R.id.aPassword);
        Button authorize = findViewById(R.id.toAut);
        Button reg = findViewById(R.id.reg);
        db = new DBhelper(this);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, registration.class);
                startActivity(intent);
            }
        });

        authorize.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String Password = password.getText().toString();
                String Email = email.getText().toString();
                boolean checker = db.authorization(Email, Password);
                if (checker) {
                    Intent loc = new Intent(MainActivity.this, MyLocation.class);
                    startActivity(loc);
                }
                else setContentView(R.layout.ne_rabotaet);
            }
        });

    }



}