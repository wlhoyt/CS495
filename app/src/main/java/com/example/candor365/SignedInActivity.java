/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.candor365;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SignedInActivity extends AppCompatActivity {
    private Button signoutButton;
    private Button classSignInButton;
    private Button viewScheduleButton;
    private Button shopButton;
    private View.OnClickListener signoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                signoutClicked();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signedin_activity);
        signoutButton = (Button) findViewById(R.id.sign_out);
        signoutButton.setOnClickListener(signoutListener);

        shopButton = (Button) findViewById(R.id.shop_button);
        shopButton.setOnClickListener(shopButtonListener);

        viewScheduleButton = (Button) findViewById(R.id.viewSchedule);
        viewScheduleButton.setOnClickListener(viewScheduleListener);
        Database.initializeDb();

        Map<String, Object> docData = new HashMap<>();
        docData.put("name","test");
        docData.put("stuff", "test");
        classSignInButton = (Button) findViewById(R.id.class_sign_in);
        classSignInButton.setOnClickListener(attendanceListener);
    }

    private View.OnClickListener attendanceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            attendanceClick();
        }
    };
    public void attendanceClick()
    {
        startActivity(NFC_Reading.createIntent(this));
    }


    @NonNull
    public static Intent createIntent(@NonNull Context context, @Nullable IdpResponse response) {
        return new Intent().setClass(context, SignedInActivity.class)
                .putExtra(ExtraConstants.IDP_RESPONSE, response);
    }

    private void signoutClicked() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        Toast.makeText(SignedInActivity.this, "USER SIGNED OUT", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignedInActivity.this, MainActivity.class));
                        finish();
                    }
                });

    }

    private View.OnClickListener viewScheduleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            viewScheduleClicked();
        }
    };

    private void viewScheduleClicked(){
        startActivity(new Intent(SignedInActivity.this, CalendarActivity.class));
        finish();
    }
    private View.OnClickListener shopButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(SignedInActivity.this, DummyActivity.class));
        }
    };
}