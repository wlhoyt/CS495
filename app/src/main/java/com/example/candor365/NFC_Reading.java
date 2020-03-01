package com.example.candor365;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class NFC_Reading extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    TextView testing;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_reading_activity);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        testing = (TextView)findViewById(R.id.testing);
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);

        if(intent.hasExtra(NfcAdapter.EXTRA_TAG))
        {


            Parcelable [] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if(parcelables != null && parcelables.length > 0 )
            {
                readTextFromMessage((NdefMessage) parcelables[0]);
            }
            else
            {
               Toast.makeText(this,"No NDEF Message",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readTextFromMessage(NdefMessage ndefMessage)
    {
        NdefRecord [] ndefRecords = ndefMessage.getRecords();
        if(ndefRecords != null && ndefRecords.length > 0)
        {
            NdefRecord ndefRecord = ndefRecords[0];
            String tagContent = getTextFromNdefRecord(ndefRecord);
//            attendanceChecker(tagContent);
            preregisterChecker(tagContent,"2020214","6:30");

        }
        else
        {
           Toast.makeText(this,"No NDEF records found!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
       super.onResume();

        enableForegroundDispatchSystem();
    }

    @Override
    protected void onPause() {
        super.onPause();

        disableForegroundDispatchSystem();
    }

    @NonNull
    public static Intent createIntent(@NonNull Context context) {
        return new Intent().setClass(context, NFC_Reading.class);
    }

    private void enableForegroundDispatchSystem()
    {
        Intent intent = new Intent(this, NFC_Reading.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        IntentFilter[] intentFilters = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent,intentFilters,null);
    }
    private void disableForegroundDispatchSystem()
    {
        nfcAdapter.disableForegroundDispatch(this);
    }


    public String getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        String tagContent = null;
        try
        {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload,languageSize + 1, payload.length - languageSize - 1, textEncoding);
        }
        catch (UnsupportedEncodingException e)
        {
            Log.e("getTextFromNdefRecord",e.getMessage());
        }
        return tagContent;
    }

    public void preregisterChecker(String nfc_tag, final String data, String time)
    {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String username = acct.getDisplayName();
//        Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, nfc_tag, Toast.LENGTH_LONG).show();

        Database.readPreregisterDb(data, time, new readCallBack() {
            @Override
            public void onCallBack(Map dataMap) {

                if(dataMap != null)
                {

                    Toast.makeText(NFC_Reading.this, dataMap.toString() + "Harder", Toast.LENGTH_SHORT).show();
                    testing.setText(dataMap.toString());
                }
                else
                {
                    Toast.makeText(NFC_Reading.this,"DataMap Not Working",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void attendanceChecker (String nfc_tag, final String data, String time)
    {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String username = acct.getDisplayName();
//        Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, nfc_tag, Toast.LENGTH_LONG).show();

        Database.initializeDb();
        Database.readAttendanceDb(data, time, new readCallBack() {
            @Override
            public void onCallBack(Map dataMap) {
                    if(dataMap == null)
                    {
                        Toast.makeText(NFC_Reading.this,"GOD DAM IT",Toast.LENGTH_SHORT).show();
                    }
            }
        });
        return;
    }

}
