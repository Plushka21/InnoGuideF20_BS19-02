package com.example.innoguidesjava;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DetailedInfoWindow extends AppCompatActivity {

    private TextView bAddress, bNumber, bName, dayTime;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_info_window);
        bName = findViewById(R.id.bName);
        bNumber = findViewById(R.id.bNumber);
        bAddress = findViewById(R.id.bAddress);
        ratingBar = findViewById(R.id.ratingBar);

        Date CurrentTime = Calendar.getInstance().getTime();

        Intent intent = getIntent();
        bName.setText(intent.getStringExtra("Name"));
        bAddress.setText(intent.getStringExtra("Address"));
        bNumber.setText(intent.getStringExtra("Phone Number"));
        String[] wokringTime = intent.getStringArrayExtra("Time");
        for (int i = 0; i < 7; i++){
            switch (i){
                case (0):
                    dayTime = findViewById(R.id.mondayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
                case (1):
                    dayTime = findViewById(R.id.tuesdayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
                case (2):
                    dayTime = findViewById(R.id.wednesdayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
                case (3):
                    dayTime = findViewById(R.id.thursdayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
                case (4):
                    dayTime = findViewById(R.id.fridayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
                case (5):
                    dayTime = findViewById(R.id.saturdayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
                case (6):
                    dayTime = findViewById(R.id.sundayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
            }

        }


        //PHOTO ADDING
        ImageSlider photos = findViewById(R.id.photos);
        ArrayList<SlideModel> list = new ArrayList<>();


        list.add(new SlideModel("https://upload.wikimedia.org/wikipedia/commons/thumb/1/11/Test-Logo.svg/783px-Test-Logo.svg.png"));
        list.add(new SlideModel("https://storage.theoryandpractice.ru/tnp/uploads/image_avatar/000/013/606/image/medium_2fee5a5a7e.jpg"));

        photos.setImageList(list, true);


        ratingBar.setNumStars(5);
        ratingBar.setStepSize((float) 0.5);
        String rating = intent.getStringExtra("Rating");
        ratingBar.setRating(Float.parseFloat(rating));
    }
}