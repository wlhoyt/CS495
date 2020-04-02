package com.example.candor365;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addCalenderEvent extends AppCompatActivity {

    private TextView eventName;
    private Spinner class_time;
    private Spinner coaches;
    private Button saveEventButton;
    String class_time_string = "";
    String coach_string ="";
    String date = CalendarActivity.getDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calender_event);
        eventName  = (TextView) findViewById(R.id.event_name);
        class_time = (Spinner) findViewById(R.id.class_time_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.class_time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        class_time.setAdapter(adapter);
        class_time.setOnItemSelectedListener(timeListener);

        coaches = (Spinner) findViewById(R.id.coaches_spinner);
        ArrayAdapter<CharSequence> coachesadapter = ArrayAdapter.createFromResource(this,
                R.array.coaches_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coaches.setAdapter(coachesadapter);
        coaches.setOnItemSelectedListener(coachesListener);

        saveEventButton = (Button) findViewById(R.id.save_event);
        saveEventButton.setOnClickListener(saveEventListener);

    }
    private View.OnClickListener saveEventListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveEvent();
        }
    };

    private Spinner.OnItemSelectedListener timeListener = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            class_time_string = parentView.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }
    };

    private Spinner.OnItemSelectedListener coachesListener = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            coach_string = parentView.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }
    };

    private void saveEvent(){
        //saves the info of the event and writes it to the database
        String event_name = eventName.getText().toString();
        String coach = coach_string;
        String time = class_time_string;
        int nfcID= 4;

        Map<String, Object> data = new HashMap<>();

        data.put("event_name", event_name);
        data.put("coach", coach);
        data.put("nfcID", nfcID);

        Database.writeClassDb(data, time ,date);
        startActivity(new Intent(addCalenderEvent.this, CalendarActivity.class));
        finish();
    }
}
