package com.example.innoguidesjava;

/**
 * Class for detailed window with information about place
 */

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.io.InputStream;


public class DetailedInfoWindow extends AppCompatActivity {

    private TextView bAddress, bNumber, bName, dayTime;
    private RatingBar ratingBar;
    private ViewFlipper flipper;

    private Animation animFlipInForward;
    private Animation animFlipOutForward;
    private Animation animFlipInBackward;
    private Animation animFlipOutBackward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_info_window);
        bName = findViewById(R.id.bName);
        bNumber = findViewById(R.id.bNumber);
        bAddress = findViewById(R.id.bAddress);
        ratingBar = findViewById(R.id.ratingBar);

        animFlipInForward = AnimationUtils.loadAnimation(this, R.anim.flipin);
        animFlipOutForward = AnimationUtils.loadAnimation(this, R.anim.flipout);
        animFlipInBackward = AnimationUtils.loadAnimation(this,
                R.anim.flipin_reverse);
        animFlipOutBackward = AnimationUtils.loadAnimation(this,
                R.anim.flipout_reverse);

        Intent intent = getIntent();
        // Set name, address, phone number and working time of place
        bName.setText(intent.getStringExtra("Name"));
        bAddress.setText(intent.getStringExtra("Address"));
        bNumber.setText(intent.getStringExtra("Phone Number"));
        String[] wokringTime = intent.getStringArrayExtra("Time");
        for (int i = 0; i < 7; i++){
            switch (i){
                case (0):
                    dayTime = findViewById(R.id.sundayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
                case (1):
                    dayTime = findViewById(R.id.mondayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
                case (2):
                    dayTime = findViewById(R.id.tuesdayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
                case (3):
                    dayTime = findViewById(R.id.wednesdayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
                case (4):
                    dayTime = findViewById(R.id.thursdayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
                case (5):
                    dayTime = findViewById(R.id.fridayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
                case (6):
                    dayTime = findViewById(R.id.saturdayTime);
                    dayTime.setText(wokringTime[i]);
                    break;
            }

        }

        flipper = findViewById(R.id.photos);

        // Set photos
        String[] photos = intent.getStringArrayExtra("Photos");
        for (String photo : photos) {
            setViewFlipper(photo+".jpg");
        }

        ratingBar.setNumStars(5);
        ratingBar.setStepSize((float) 0.5);
        String rating = intent.getStringExtra("Rating");
        ratingBar.setRating(Float.parseFloat(rating));
    }

    // Function to show photos
    public void setViewFlipper(String image){
        ImageView imageView = new ImageView(this);
        InputStream inputStream = null;
        try{
            inputStream = getApplicationContext().getAssets().open(image);
            Drawable d = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(d);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(inputStream!=null)
                    inputStream.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }

        flipper.addView(imageView);
        flipper.setFlipInterval(4000);
        flipper.setAutoStart(true);
    }

    private void SwipeLeft() {
        flipper.setInAnimation(animFlipInBackward);
        flipper.setOutAnimation(animFlipOutBackward);
        flipper.showPrevious();
    }

    private void SwipeRight() {
        flipper.setInAnimation(animFlipInForward);
        flipper.setOutAnimation(animFlipOutForward);
        flipper.showNext();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            float sensitvity = 50;
            if ((e1.getX() - e2.getX()) > sensitvity) {
                SwipeLeft();
            } else if ((e2.getX() - e1.getX()) > sensitvity) {
                SwipeRight();
            }
            return true;
        }
    };

    GestureDetector gestureDetector = new GestureDetector(getBaseContext(),
            simpleOnGestureListener);
}