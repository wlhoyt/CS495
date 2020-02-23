package com.example.candor365;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import com.firebase.ui.auth.IdpResponse;

public class NFC_Reading extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter!=null && nfcAdapter.isEnabled())
        {
            Toast.makeText(this,"NFC is available and in new activity",Toast.LENGTH_LONG).show();
        }
        else
        {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {

        Toast.makeText(this,"NFC intent received!",Toast.LENGTH_LONG).show();

        super.onNewIntent(intent);
    }


    @Override
    protected void onResume() {
        Intent intent = new Intent(this, NFC_Reading.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilters = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this,pendingIntent,intentFilters,null);
        super.onResume();
    }

    @Override
    protected void onPause() {
        nfcAdapter.disableForegroundDispatch(this);

        super.onPause();
    }

    @NonNull
    public static Intent createIntent(@NonNull Context context, @Nullable IdpResponse response) {
        return new Intent().setClass(context, NFC_Reading.class);
    }
}
