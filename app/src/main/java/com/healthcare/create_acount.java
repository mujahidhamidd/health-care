package com.healthcare;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class create_acount extends AppCompatActivity {

    EditText name,email,password,age;
    Button create;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acount);




        name= findViewById(R.id.username);
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        age= findViewById(R.id.age);
        create= findViewById(R.id.creat_btn);
        databaseHelper = new DatabaseHelper(create_acount.this);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (name.getText().toString().isEmpty()){

                 Toast.makeText(create_acount.this,"enter name !",Toast.LENGTH_LONG).show();
             }else   if (password.getText().toString().isEmpty()){

                 Toast.makeText(create_acount.this,"enter password !",Toast.LENGTH_LONG).show();
             }else   if (email.getText().toString().isEmpty()){

                 Toast.makeText(create_acount.this,"enter email !",Toast.LENGTH_LONG).show();
             } else   if (age.getText().toString().isEmpty()){

                 Toast.makeText(create_acount.this,"enter age !",Toast.LENGTH_LONG).show();
             }else {

                 databaseHelper.Add_Patient(name.getText().toString(),password.getText().toString(),email.getText().toString(),age.getText().toString());

                 Toast.makeText(create_acount.this,"thank you ",Toast.LENGTH_LONG).show();
                 Notify();
                 Intent i = new Intent(create_acount.this,Login.class);
                startActivity(i);

             }

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void Notify() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notify)
                        .setContentTitle("new account ")
                        .setContentText("thank you");

        Intent notificationIntent = new Intent(this, Login.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}
