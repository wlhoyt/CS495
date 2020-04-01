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
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import java.io.UnsupportedEncodingException;


public class NFC_Reading extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    EditText txtTagContent;
    //    ToggleButton tglReadWrite;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_reading_activity);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        txtTagContent = (EditText) findViewById(R.id.txtTagContent);
        //  tglReadWrite = (ToggleButton) findViewById(R.id.tglReadWrite);

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
               // Toast.makeText(this,"No NDEF Message",Toast.LENGTH_LONG).show();
            }

            /*
            if(tglReadWrite.isChecked())
            {
                Parcelable [] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

                if(parcelables != null && parcelables.length > 0 )
                {
                    readTextFromMessage((NdefMessage) parcelables[0]);
                }
                else
                {
                    Toast.makeText(this,"No NDEF Message",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                NdefMessage ndefMessage =  createNdefMessage(txtTagContent.getText()+"");
                writeNdefMessage(tag, ndefMessage);

            }
            */

        }
    }

    private void readTextFromMessage(NdefMessage ndefMessage)
    {
        NdefRecord [] ndefRecords = ndefMessage.getRecords();
        if(ndefRecords != null && ndefRecords.length > 0)
        {
            NdefRecord ndefRecord = ndefRecords[0];
            String tagContent = getTextFromNdefRecord(ndefRecord);
            txtTagContent.setText(tagContent);
            attendanceChecker(tagContent);

        }
        else
        {
           // Toast.makeText(this,"No NDEF records found!",Toast.LENGTH_LONG).show();
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
    public static Intent createIntent(@NonNull Context context, @Nullable IdpResponse response) {
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

    /*
    private void formatTag(Tag tag, NdefMessage ndefMessage)
    {
        try
        {
            NdefFormatable ndefFormatable = NdefFormatable.get(tag);

            if(ndefFormatable == null)
            {
                Toast.makeText(this,"Tag is not ndef formatable!", Toast.LENGTH_SHORT).show();
                return;
            }

            ndefFormatable.connect();
            ndefFormatable.format(ndefMessage);
            ndefFormatable.close();

            Toast.makeText(this, "Tag formatted", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Log.e("FormatTag", e.getMessage());
        }
    }
    */

    /*
    private void writeNdefMessage(Tag tag, NdefMessage ndefMessage)
    {
        try
        {
            if(tag == null) {
                Toast.makeText(this, "Tag object cannot be null", Toast.LENGTH_SHORT).show();
                return;
            }
            Ndef ndef = Ndef.get(tag);

            if(ndef == null)
            {
                // Format Tag with the ndef format and writes message
                formatTag(tag,ndefMessage);
            }
            else
            {
                ndef.connect();
                if(!ndef.isWritable())
                {
                    Toast.makeText(this, "Tag is not writable!", Toast.LENGTH_SHORT).show();
                    ndef.close();
                    return;
                }
                ndef.writeNdefMessage(ndefMessage);
                ndef.close();

                Toast.makeText(this, "Tag written", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Log.e("writeNdefMessage",e.getMessage());
        }
    }
    */

    /*
    private NdefRecord createTextRecord(String content)
    {
        try
        {
            byte [] language;
            language = Locale.getDefault().getLanguage().getBytes("UTF-8");

            final byte[] text = content.getBytes("UTF-8");
            final int languageSize = language.length;
            final int textLength = text.length;
            final ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + languageSize + textLength);

            payload.write((byte)(languageSize & 0x1F));
            payload.write(language, 0, languageSize);
            payload.write(text,0,textLength);

            return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());
        }
        catch (UnsupportedEncodingException e)
        {
            Log.e("CreateTextRecord",e.getMessage());
        }
        return null;
    }
    */

    /*
    private NdefMessage createNdefMessage(String content)
    {
        NdefRecord ndefRecord = createTextRecord(content);

        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{ ndefRecord });

        return ndefMessage;
    }
    */

    /*
    public void tglReadWriteOnClick(View view)
    {
        txtTagContent.setText("");
    }
    */

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
    public void attendanceChecker (String nfc_tag){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String username = acct.getDisplayName();

    }

}
