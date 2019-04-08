package com.healthcare;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Reservation extends AppCompatActivity {
    RecyclerView listView ;
    AlertDialog alertDialog;
    FloatingActionButton add;
    private DatabaseHelper databaseHelper;
    private ArrayList<Reservation_Model> arrayList = new ArrayList<>();
    int id =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            id = bundle.getInt("id");
        }


        databaseHelper = new DatabaseHelper(this);
        arrayList.addAll(databaseHelper.getAllReservatinList(id));


        final ReservationAdapter adapter = new ReservationAdapter(this, arrayList );
        listView =   findViewById(R.id.recycler);
        listView.setLayoutManager(new LinearLayoutManager(Reservation.this));
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        add =  findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View alertLayout = inflater.inflate(R.layout.add_reservation, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(Reservation.this);
                alert.setView(alertLayout);
                final EditText nameD=(EditText)alertLayout.findViewById(R.id.nameD);
                final EditText nameC=(EditText)alertLayout.findViewById(R.id.nameC);
                final EditText time=(EditText)alertLayout.findViewById(R.id.time);
                final EditText date=(EditText)alertLayout.findViewById(R.id.date);
                getTime(date);
                Button save=(Button) alertLayout.findViewById(R.id.save);
                alertDialog = alert.create();
                alertDialog.show();

               save.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       databaseHelper.Add_Reservation(nameD.getText().toString(),nameC.getText().toString(),date.getText().toString(),time.getText().toString(),id);
                       Toast.makeText(getBaseContext(),"Successfully added",Toast.LENGTH_LONG).show();

                       arrayList.clear();
                       arrayList.addAll(databaseHelper.getAllReservatinList(id));
                       adapter.notifyDataSetChanged();
                       alertDialog.dismiss();
                   }
               });

            }
        });


    }

    public void getTime(final EditText editTextDate){

        final Calendar currentDate = Calendar.getInstance();
        final Calendar date = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(Reservation.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                date.set(year, monthOfYear, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editTextDate.setText(sdf.format(date.getTime()));

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
        //   datePickerDialog.getDatePicker().setMinDate(currentDate.getTimeInMillis());
        editTextDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                datePickerDialog.show();
            }});
    }
}
