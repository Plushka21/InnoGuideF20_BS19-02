package com.example.innoguidesjava;

/**
 * Class for starting page of application with authorization page
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AuthorizationPage extends AppCompatActivity {

    // Textfields and database with users
    private EditText email, password;
    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_page);

        email = findViewById(R.id.aEmail);
        password = findViewById(R.id.aPassword);
        Button authorize = findViewById(R.id.toAut);
        Button reg = findViewById(R.id.reg);

        db = new DBhelper(this);

        // Go to registration page
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorizationPage.this, RegistrationPage.class);
                startActivity(intent);
            }
        });

        // Try to authorize
        authorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password = password.getText().toString();
                String Email = email.getText().toString();
                boolean checker = db.authorization(Email, Password);
                // If email and password are valid load the map
                if (checker) {
                    Intent loc = new Intent(AuthorizationPage.this, MainMap.class);
                    startActivity(loc);
                }
                // Else show error message
                else
                    Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT).show();
            }
        });

    }
}