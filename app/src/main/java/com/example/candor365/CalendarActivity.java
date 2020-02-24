package com.example.candor365;

//import android.content.Context;
//import android.content.Intent;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/*
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.util.ExtraConstants;
*/
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/*
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Map;


import static java.lang.Thread.sleep;

public class CalendarActivity extends AppCompatActivity {
    private Button backButton;
    private Button addEventButton;
    private CalendarView eventCalendar;
    private TextView classView;
    static String TAG = "Calendar Activity";

    private View.OnClickListener backButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            backButtonClicked();
        }
    };
    private View.OnClickListener addEventListener = new View.OnClickListener(){
        @Override
        public void onClick(View e){
            addEventClicked();
        }
    };

    private void addEventClicked(){
        startActivity(new Intent(CalendarActivity.this, addCalenderEvent.class));
    }

    private void backButtonClicked(){
        startActivity(new Intent(CalendarActivity.this, SignedInActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        eventCalendar = (CalendarView) findViewById(R.id.calendar_view);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(backButtonListener);
        addEventButton = (Button) findViewById(R.id.add_event_button);
        addEventButton.setOnClickListener(addEventListener);



        eventCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                classView = (TextView) findViewById(R.id.classEvent);
                String date = year + ""+ (month+1) + dayOfMonth;
                classView.setText("");
                Map data = Database.readClassDb(date);

                if (data != null) {
                    Log.d(TAG, "Data has been populated");
                    classView.setText(data.toString());
                }
                else {
                    Log.w(TAG, "Could not read Database");
                }

            }
        });
        //Database.readClassDb("danielsClass");
    }

}
