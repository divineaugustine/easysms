package com.example.divin_a.easysms;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import static android.content.Intent.ACTION_CALL;

public class conversationactivity extends AppCompatActivity {

    public static final  String EXTRA_MESSAGE = "CONVERSATION_WITH";

    private boolean mbSearchMode = false;
    private String mContactNum;
    private ListView mlistv;
    private EditText medt;
    private int mnCurrentMode;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversationactivity);
        mContactNum = getIntent().getStringExtra(conversationactivity.EXTRA_MESSAGE);
        mlistv=(ListView)findViewById(R.id.listContact2);

        mnCurrentMode = DataModel.GetCurrentSMSDisplayMode();
        medt = (EditText)findViewById(R.id.edtTOSend );

        if( mContactNum.contains("Search_MODE_TYPED"))
        {
            mbSearchMode = true;
            DataModel.SetCurrentSMSDisplayMode(DataModel.SEARCH);
            mContactNum = DataModel.GetSearchItem();

            this.medt.setHint("Type here to Search");

        }
        else if( mContactNum.contains("Search_MODE"))
        {
            mContactNum = "";
            mbSearchMode = true;
            this.medt.setHint( "Type here to Search" );
            DataModel.SetCurrentSMSDisplayMode(DataModel.SEARCH);
        }
        else
        {
            DataModel.SetCurrentSMSDisplayMode(DataModel.INBOX_CONVERSATION);
        }


        ResetView();

        setTitle( ContactCache.GetContactNameFromID(this, mContactNum ));



        Button bt = (Button)findViewById(R.id.btchuj );


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if( conversationactivity.this.mbSearchMode == false )
                {
                    String strMsg = conversationactivity.this.medt.getText().toString();
                    if( strMsg.isEmpty() == false )
                    {

                        String SENT = "SMS_SENT";
                        String DELIVERED = "SMS_DELIVERED";

                        PendingIntent sentPI = PendingIntent.getBroadcast(conversationactivity.this, 0,
                                new Intent(SENT), 0);

                        PendingIntent deliveredPI = PendingIntent.getBroadcast(conversationactivity.this, 0,
                                new Intent(DELIVERED), 0);

                        //---when the SMS has been sent---
                        registerReceiver(new BroadcastReceiver(){
                            @Override
                            public void onReceive(Context arg0, Intent arg1) {
                                switch (getResultCode())
                                {
                                    case Activity.RESULT_OK:
                                        Toast.makeText(getBaseContext(), "SMS sent",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                        Toast.makeText(getBaseContext(), "Generic failure",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                                        Toast.makeText(getBaseContext(), "No service",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case SmsManager.RESULT_ERROR_NULL_PDU:
                                        Toast.makeText(getBaseContext(), "Null PDU",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                                        Toast.makeText(getBaseContext(), "Radio off",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }, new IntentFilter(SENT));

                        //---when the SMS has been delivered---
                        registerReceiver(new BroadcastReceiver(){
                            @Override
                            public void onReceive(Context arg0, Intent arg1) {
                                switch (getResultCode())
                                {
                                    case Activity.RESULT_OK:
                                        Toast.makeText(getBaseContext(), "SMS delivered",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                    case Activity.RESULT_CANCELED:
                                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                                Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }, new IntentFilter(DELIVERED));

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(mContactNum, null, strMsg, sentPI, deliveredPI );
                        //Snackbar.make(conversationactivity.this, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //        .setAction("Action", null).show();
                        conversationactivity.this.medt.setText("");
                       // Toast.makeText(conversationactivity.this, "Sent", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    mContactNum = conversationactivity.this.medt.getText().toString();
                    // conversationactivity.this.medt.clearComposingText();
                    conversationactivity.this.medt.setText( "" );
                }
                ResetView();
            }
        });

    }

    private void ResetView()
    {

        ConversationAdapter adapter = null;

        if( mbSearchMode )
        {
            if( mContactNum.isEmpty() == false )
            {
                adapter = new ConversationAdapter(this,
                        SMSUtils.Search(this, mContactNum));
                setTitle(mContactNum);
            }
            else
            {
                setTitle("Search");
            }
        }
        else
        {
            ArrayList<ConversationInfo> conversation = SMSUtils.GetConversation(conversationactivity.this, mContactNum);
            adapter = new ConversationAdapter(conversationactivity.this, conversation);
        }

        try
        {
            //conversationactivity.this.mlistv.setStackFromBottom(true);
            //conversationactivity.this.mlistv.setTranscriptMode(1); // disabled	0	normal 1    alwaysScroll 2
            conversationactivity.this.mlistv.setAdapter(adapter);
        } catch (Exception ex) {
            String strErro = ex.toString();
        }
    }

    public void onBackPressed()
    {
        DataModel.SetCurrentSMSDisplayMode(mnCurrentMode);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        if( mbSearchMode )
        {
            inflater.inflate(R.menu.search, menu);
        }
        else {
            inflater.inflate(R.menu.conversation, menu);
        }
        return true;
    }

    private static void DialNumber( Context ctx, String snumber )
    {
        if (ContextCompat.checkSelfPermission(ctx,
                Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            // Uri number = Uri.parse("tel:8431583661");
            Uri number = Uri.parse(snumber);
            Intent callIntent = new Intent(ACTION_CALL, number);
            ctx.startActivity(callIntent);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_call)
        {
            if( !mbSearchMode )
            {
                DialNumber(this, "tel:" + mContactNum );

                //Toast.makeText(getBaseContext(), "Calling contact :)",
                //        Toast.LENGTH_SHORT).show();

            }
            return true;
        }
        else if (id == R.id.action_Lock )
        {
            // DialNumber(this, "tel:" + mContactNum );

            Lock();
            return true;
        }
        else if (id == R.id.action_Unlock )
        {
            // DialNumber(this, "tel:" + mContactNum );

            Unlock();
            return true;
        }

        else if (id == R.id.action_Add )
        {
               // DialNumber(this, "tel:" + mContactNum );

            if( mContactNum.isEmpty() == false ) {
                LabelsDBHelper inst = new LabelsDBHelper(this);
                // LabelInfo info = new LabelInfo( mContactNum, "fff");
                inst.deleteLabel( mContactNum );
                inst.InsertLabel(mContactNum, "ff");
            }
                //Toast.makeText(getBaseContext(), "Add to fav :)",
                //        Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_Remove )
        {
            if( mContactNum.isEmpty() == false )
            {
                LabelsDBHelper inst = new LabelsDBHelper(this);
                // LabelInfo info = new LabelInfo( mContactNum, "fff");
                inst.deleteLabel( mContactNum );
            }
            {
                //DialNumber(this, "tel:" + mContactNum );
              // Toast.makeText(getBaseContext(), "Remove from fav :)",
              //          Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Lock()
    {
        DataModel.Lock();
    }
    private void Unlock()
    {
        DataModel.UnLock( this );
        mlistv.invalidate();
    }
}




