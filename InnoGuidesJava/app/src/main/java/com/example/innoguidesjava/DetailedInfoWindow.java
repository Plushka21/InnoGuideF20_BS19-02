package com.example.innoguidesjava;

import android.content.Intent;
import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;


public class DetailedInfoWindow extends AppCompatActivity {

    private TextView bAddress, bNumber, bName;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_info_window);
        bName = findViewById(R.id.bName);
        bNumber = findViewById(R.id.bNumber);
        bAddress = findViewById(R.id.bAddress);
        ratingBar = findViewById(R.id.ratingBar);


        Intent intent = getIntent();
        bName.setText(intent.getStringExtra("Name"));
        bAddress.setText(intent.getStringExtra("Address"));
        bNumber.setText(intent.getStringExtra("Phone Number"));


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