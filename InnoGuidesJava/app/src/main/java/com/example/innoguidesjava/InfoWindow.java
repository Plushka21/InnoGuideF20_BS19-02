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

    private final String name, number, address;
    private final double rating;

    public InfoWindow(String name, String number, String address, double rating){
        this.name = name;
        this.number = number;
        this.address = address;
        this.rating = rating;
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
                        "\nRating: " + this.rating +
                        "\nAddress: " + this.address +
                        "\nPhone: " + this.number)
                .create();
    }
}