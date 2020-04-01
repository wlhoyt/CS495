package com.example.candor365;

import android.content.Context;
import com.firebase.client.Firebase;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class FirebaseMocker {
    @Mock
    Context context;
    @Mock
    Context appContext;
    @Mock
    private android.content.SharedPreferences sharedPreferences;

    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private Firebase firebase;
}
