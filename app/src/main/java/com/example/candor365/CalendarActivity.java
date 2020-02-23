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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    private Button backButton;

    private View.OnClickListener backButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            backButtonClicked();
        }
    };

    private void backButtonClicked(){
        startActivity(new Intent(CalendarActivity.this, SignedInActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(backButtonListener);
    }

}