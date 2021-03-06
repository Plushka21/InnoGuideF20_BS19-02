package com.example.innoguidesjava;

/**
 * Page to create new users
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationPage extends AppCompatActivity {
    private EditText password, email, cPassword;
    private Button toReg;
    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);

        db = new DBhelper(this);

        password = findViewById(R.id.rPassword);
        cPassword = findViewById(R.id.cPassword);
        email = findViewById(R.id.rEmail);
        toReg = findViewById(R.id.toReg);


        toReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get email and password from textfields
                String Password = password.getText().toString();
                String Email = email.getText().toString();
                String CPassword = cPassword.getText().toString();

                // If something in input strings is wrong error message will appear
                // otherwise new account will be created and user will be automatically moved to authorization page
                if (Password.isEmpty() || CPassword.isEmpty() || Email.isEmpty())
                    Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_SHORT).show();
                else if (!Password.equals(CPassword))
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                else {
                    boolean checkmail = db.checkEmail(Email);
                    if (checkmail) {
                        boolean insert = db.insert(Email, Password);
                        if (insert) {
                            finish();
                            setContentView(R.layout.authorization_page);
                            Toast toast = Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 0);
                            toast.show();
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}