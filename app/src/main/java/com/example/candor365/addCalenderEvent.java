package com.example.candor365;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class addCalenderEvent extends AppCompatActivity {

    private EditText eventName;
    private EditText eventDate;
    private EditText eventDescr;
    String event_title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calender_event);
        eventName  = (EditText) findViewById(R.id.event_name);
        eventDate  = (EditText) findViewById(R.id.event_date);
        eventDescr = (EditText) findViewById(R.id.event_description);
        eventName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //save the text to write to the db on save event button click
                
            }
        });
//        eventDate.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                //do nothing
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                //do nothing
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                //save text to write to the db on save event button click
//            }
//        });
//        eventDescr.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                //do nothing
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                //do nothing
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                //save text to write to db on save event click
//            }
//        });

    }
}
