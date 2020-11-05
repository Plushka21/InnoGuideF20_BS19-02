package com.example.innoguidesjava;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

public class InfoWindow extends DialogFragment {

    private final String name;
    private final String number;
    private final String address;
    private final String[] time;
    private final String[] days;
    private final double rating;

    public InfoWindow(String name, String number, String address, double rating, String[] time, String[] days){
        this.name = name;
        this.number = number;
        this.address = address;
        this.rating = rating;
        this.time = time;
        this.days = days;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Place Info")
                .setView(R.layout.activity_info_window)
                .setPositiveButton("OK", null)
                .setMessage("Name: " + this.name +
                        "\n\nRating: " + this.rating +
                        "\n\nAddress: " + this.address +
                        "\n\nPhone: " + this.number +
                        "\n\nWorking time: " + this.days[0] + "\n" + this.time[0])
                .create();
    }
}