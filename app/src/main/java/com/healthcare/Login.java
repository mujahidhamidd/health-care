package com.healthcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText  email,password ;
    Button create , login;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        email= findViewById(R.id.email);
        password= findViewById(R.id.password);

        create= findViewById(R.id.creat_btn);
        login = findViewById(R.id.login_btn);
        databaseHelper = new DatabaseHelper(Login.this);
        databaseHelper.getReadableDatabase();
        databaseHelper.getWritableDatabase();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, R.string.empty_message, Toast.LENGTH_SHORT).show();
                } else if (databaseHelper.checkUser(email.getText().toString() , password.getText().toString()) < 0) {
                    Toast.makeText(Login.this, R.string.error_message, Toast.LENGTH_SHORT).show();

                } else {
                  int id = databaseHelper.checkUser(email.getText().toString() , password.getText().toString());
                    Intent i = new Intent(Login.this, Reservation.class);
                    i.putExtra("id",id);
                    startActivity(i);
                }

            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, create_acount.class);
                startActivity(i);
            }
        });
    }
}
