package com.example.innoguidesjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AuthorizationPage extends AppCompatActivity {

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

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorizationPage.this, RegistrationPage.class);
                startActivity(intent);
            }
        });

        authorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password = password.getText().toString();
                String Email = email.getText().toString();
                boolean checker = db.authorization(Email, Password);
                if (checker) {
                    Intent loc = new Intent(AuthorizationPage.this, Map.class);
                    startActivity(loc);
                } else
                    Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT).show();
            }
        });

    }


}