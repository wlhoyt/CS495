package com.example.candor365;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

public class addCalenderEvent extends AppCompatActivity {

    private TextView eventName;
    private TextView eventDate;
    private TextView eventDescr;
    private Button saveEventButton;
    String event_title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calender_event);
        eventName  = (TextView) findViewById(R.id.event_name);
        eventDate  = (TextView) findViewById(R.id.event_date);
        eventDescr = (TextView) findViewById(R.id.event_description);
        saveEventButton = (Button) findViewById(R.id.save_event);
        saveEventButton.setOnClickListener(saveEventListener);

    }
    private View.OnClickListener saveEventListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveEvent();
        }
    };

    private void saveEvent(){
        //saves the info of the event and writes it to the database
        String event_name = eventName.getText().toString();
        String event_date = eventDate.getText().toString();
        String event_descr = eventDescr.getText().toString();
        int nfcID= 4;

        Map<String, Object> data = new HashMap<>();

        data.put("event_name", event_name);
        data.put("event_date", event_date);
        data.put("event_descr", event_descr);
        data.put("nfcID", nfcID);

        Database.writeClassDb(data, event_date);
        startActivity(new Intent(addCalenderEvent.this, CalendarActivity.class));
        finish();
    }
}
