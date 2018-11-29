package com.example.divin_a.easysms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BaseAdapter madapter;
    ListView mlistv;
   // Button mbtContact;
   // Button mbtAll;
   // Button mbtBulk;
   // Button mbtUnknown;
    SubMenu mLabelMenu = null;

   HashMap<String, String> mobjCache = new HashMap<String, String>();


    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    final private  int PERMISSION_REQUEST_CODE = 1;
    final private  int PERMISSION_REQUEST_CODE2 = 2;



    private void requestContactPermission() {

        int hasContactPermission =ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_SMS);

        if(hasContactPermission != PackageManager.PERMISSION_GRANTED ) {
            // Toast.makeText(MainActivity.this, "sms Permission is not granted already", Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, PERMISSION_REQUEST_CODE);
        }else {
         //   Toast.makeText(MainActivity.this, "sms Permission is already granted", Toast.LENGTH_LONG).show();
        }




     /*   hasContactPermission =ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_CONTACTS);

        if(hasContactPermission != PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(MainActivity.this, "Contact Permission is not granted already", Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CODE2);
        }else {
            Toast.makeText(MainActivity.this, "Contact Permission is already granted", Toast.LENGTH_LONG).show();
        }*/

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[]       permissions, int[] grantResults) {
       // Toast.makeText(MainActivity.this, "onRequestPermissionsResult", Toast.LENGTH_LONG).show();

        switch (requestCode) {

            case PERMISSION_REQUEST_CODE:
                // Check if the only required permission has been granted
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i("Permission", "sms permission has now been granted. Showing result.");
                     Toast.makeText(this,"sms Permission is Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Permission", "sms permission was NOT granted.");
                    Toast.makeText(MainActivity.this, "sms permission was NOT granted.", Toast.LENGTH_LONG).show();

                }

                break;
            case PERMISSION_REQUEST_CODE2:
                // Check if the only required permission has been granted
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i("Permission", "Contact permission has now been granted. Showing result.");
                    Toast.makeText(this,"Contact Permission is Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Permission", "Contact permission was NOT granted.");
                    Toast.makeText(MainActivity.this, "Contact permission was NOT granted.", Toast.LENGTH_LONG).show();

                }

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(Build.VERSION.SDK_INT < 23)
        {
         //   Toast.makeText(MainActivity.this, "Old code base", Toast.LENGTH_LONG).show();

            //your code here
        }else {
            requestContactPermission();


        //    ArrayList<SMSInfo> conversation = SMSUtils.GetAllAddressFromSMS(MainActivity.this);
        }

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //         .setAction("Action", null).show();

                Intent intent = new Intent(MainActivity.this, composemsg.class);
                startActivity(intent);

            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);


        toggle.syncState();





        ////////////////////////////////////////////////////////////////////



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


       /* final Menu menu = navigationView.getMenu();
        for (int i = 1; i <= 3; i++)
        {
           MenuItem itm = menu.add("Runtime item "+ i);



        }*/

       final Menu menu = navigationView.getMenu();
        mLabelMenu = menu.addSubMenu("Labels");


        LabelsDBHelper inst = new LabelsDBHelper(this);
        ArrayList<LabelInfo> list = inst.getAllLabels();

        for (LabelInfo info:list
             )
        {
            mLabelMenu.add( info.Name );
            mobjCache.put( info.Name, "1" );
        }






       //////////////////////////////////////////////////////////////////////






/*

        MenuItem itm = mLabelMenu.add("ksrtc");

        mLabelMenu.add( "hdfc" );
        mLabelMenu.add( "ajmera" );
        mLabelMenu.add( "offer" );
        mLabelMenu.add( "bro");

        mobjCache.put("ksrtc", "1");
        mobjCache.put("hdfc", "1" );
        mobjCache.put("ajmera", "1" );
        mobjCache.put("offer", "1" );
        mobjCache.put("bro", "1" );
*/

        /*for (int i1 = 1; i1 <= 2; i1++) {
            subMenu.add("SubMenu Item " + i1);
        }*/


        mlistv=(ListView)findViewById(R.id.listContact);

       // final ArrayList<String> list = new ArrayList<String>();
       // for (int i = 0; i < values.length; ++i) {
        //    list.add(values[i]);
       // }
        //madapter = new InboxContactsAdapter(this,
       //         SMSUtils.GetAllAddressFromSMS(this));
       // mlistv.setAdapter(madapter);



      //  mbtContact = (Button)findViewById(R.id.btContacts);
      //  mbtContact.setOnClickListener(new View.OnClickListener() {
           // @Override
          //  public void onClick(View v) {

              //  MainActivity.this.UpdateListBox(DataModel.INBOX_PEOPLE);
                //DataModel.SetCurrentSMSDisplayMode(DataModel.INBOX_PEOPLE);
                //ListView listv=(ListView)findViewById(R.id.listContact);
                // listv.invalidate();
                // MainActivity.this.madapter.notifyDataSetChanged();
                //MainActivity.this.madapter = new InboxContactsAdapter(MainActivity.this,
                //        SMSUtils.GetContactNamesFromInbox(MainActivity.this));

                // listv.setAdapter(MainActivity.this.madapter);
                //MainActivity.this.mlistv.setAdapter(MainActivity.this.madapter);
                // Toast.makeText(MainActivity.this, "YOUR MESSAGE", Toast.LENGTH_LONG).show();
       //     }
      //  });

       // mbtAll = (Button)findViewById(R.id.btShowAll);
       // mbtAll.setOnClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View v) {
      //          MainActivity.this.UpdateListBox(DataModel.INBOX_ALL);
      //      }
      //  });

      //  mbtBulk = (Button)findViewById(R.id.btShowBulk);
       // mbtBulk.setOnClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View v) {
     //           MainActivity.this.UpdateListBox(DataModel.INBOX_BULKS);
     //       }
      //  });


      //  mbtUnknown = (Button)findViewById(R.id.btShowUnknown);
      //  mbtUnknown.setOnClickListener(new View.OnClickListener() {
     //       @Override
    //        public void onClick(View v) {
    //            MainActivity.this.UpdateListBox(DataModel.INBOX_UNKNOWN);
    //        }
     //   });

        UpdateListBox(DataModel.INBOX_ALL);



        mlistv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id)
            {
                if( DataModel.GetCurrentSMSDisplayMode() == DataModel.INBOX_CONVERSATION )
                {
                    //
                }
                else if( DataModel.GetCurrentSMSDisplayMode() == DataModel.SENT )
                {

                    //
                }
                else if( DataModel.GetCurrentSMSDisplayMode() == DataModel.OUTBOX )
                {
                    //
                }
                else if( DataModel.GetCurrentSMSDisplayMode() == DataModel.DRAFT )
                {
                    //
                }
                else
                {






                    InboxContactsAdapter.ViewHolderNames holder = (InboxContactsAdapter.ViewHolderNames) view.getTag();
                    if (holder != null)
                    {
                        Intent intent = new Intent(MainActivity.this, conversationactivity.class);
                        //EditText editText = (EditText) findViewById(R.id.edit_message);
                        // /String message = editText.getText().toString();
                        intent.putExtra(conversationactivity.EXTRA_MESSAGE, holder.csContactNum );
                        startActivity(intent);

                     /*   ArrayList<ConversationInfo> conversation = SMSUtils.GetConversation(MainActivity.this, holder.csContactNum);
                        ConversationAdapter adapter = new ConversationAdapter(MainActivity.this, conversation);
                        DataModel.SetCurrentSMSDisplayMode(DataModel.INBOX_CONVERSATION);
                        try {
                            MainActivity.this.mlistv.setStackFromBottom(true);
                            MainActivity.this.mlistv.setTranscriptMode(1); // disabled	0	normal 1    alwaysScroll 2
                            MainActivity.this.mlistv.setAdapter(adapter);
                        } catch (Exception ex) {
                            String strErro = ex.toString();
                        }

                        */
                    }
                }

            }
        });
    }

    private void UpdateListBox( int nMode_i )
    {
        if( DataModel.GetCurrentSMSDisplayMode() == nMode_i )
        {
            return;
        }
       // MainActivity.this.mlistv.setAdapter( null );

        MainActivity.this.mlistv.setVisibility( View.INVISIBLE );
        MainActivity.this.mlistv.setStackFromBottom(false);
        MainActivity.this.mlistv.setTranscriptMode(0);


        // ListView listv=(ListView)findViewById(R.id.listContact);
        // listv.invalidate();
        // MainActivity.this.madapter.notifyDataSetChanged();

       // mbtContact.setEnabled(true);
       // mbtAll.setEnabled(true);
       // mbtBulk.setEnabled(true);
       // mbtUnknown.setEnabled(true);

     //   mbtContact.setBackgroundColor(Color.GRAY ); //    "@android:color/holo_blue_dark"( true );
     //   mbtAll.setBackgroundColor(Color.GRAY);
     //   mbtBulk.setBackgroundColor( Color.GRAY  );
     //   mbtUnknown.setBackgroundColor( Color.GRAY  );

        DataModel.SetCurrentSMSDisplayMode( nMode_i );


        if( DataModel.INBOX_PEOPLE == nMode_i )
        {
            MainActivity.this.madapter = new InboxContactsAdapter(MainActivity.this,
                    SMSUtils.GetContactNamesFromInbox(MainActivity.this));
          //  mbtContact.setBackgroundColor(Color.BLUE  ); //
            // mbtContact.setEnabled( false );

        }
        else if( DataModel.INBOX_ALL == nMode_i )
        {
            MainActivity.this.madapter = new InboxContactsAdapter(MainActivity.this,
                    SMSUtils.GetAllAddressFromSMS(MainActivity.this));
          //  mbtAll.setBackgroundColor(Color.BLUE  ); //
            // mbtAll.setEnabled( false );

        }
        else if( DataModel.INBOX_BULKS == nMode_i )
        {
            MainActivity.this.madapter = new InboxContactsAdapter(MainActivity.this,
                    SMSUtils.GetBulkNames(MainActivity.this));
          //  mbtBulk.setBackgroundColor(Color.BLUE  ); //
            // mbtBulk.setEnabled( false );
        }
        else if( DataModel.INBOX_UNKNOWN == nMode_i )
        {
            MainActivity.this.madapter = new InboxContactsAdapter(MainActivity.this,
                    SMSUtils.GetUnknownNames(MainActivity.this));
           // mbtUnknown.setBackgroundColor(Color.BLUE  ); //
            // mbtUnknown.setEnabled( false );
        }
        else if( DataModel.SENT == nMode_i )
        {
            MainActivity.this.madapter = new ConversationAdapter(MainActivity.this,
                    SMSUtils.GetSentItems(MainActivity.this));
        }
        else if( DataModel.DRAFT == nMode_i )
        {
            MainActivity.this.madapter = new ConversationAdapter(MainActivity.this,
                    SMSUtils.GetDraft(MainActivity.this));
        }
        else if( DataModel.SEARCH == nMode_i )
        {
            MainActivity.this.madapter = new ConversationAdapter(MainActivity.this,
                    SMSUtils.Search(MainActivity.this, DataModel.GetSearchItem()));
        }

        // listv.setAdapter(MainActivity.this.madapter);
        MainActivity.this.mlistv.setAdapter(MainActivity.this.madapter);
        MainActivity.this.mlistv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            UpdateListBox(DataModel.INBOX_ALL);
           //  super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_Inbox)
        {
            MainActivity.this.UpdateListBox(DataModel.INBOX_ALL);
        }
        else if (id == R.id.action_Friends)
        {
            MainActivity.this.UpdateListBox(DataModel.INBOX_PEOPLE);
        }
        else if (id == R.id.action_Unknown)
        {
            MainActivity.this.UpdateListBox(DataModel.INBOX_UNKNOWN);
        }
        else if (id == R.id.action_Bulk)
        {
            MainActivity.this.UpdateListBox(DataModel.INBOX_BULKS);
        }
        else if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mnuSentItem)
        {
            MainActivity.this.UpdateListBox(DataModel.SENT);
            // Handle the camera action
        } else if (id == R.id.mnuDraft)
        {
            MainActivity.this.UpdateListBox(DataModel.DRAFT);
        }
        else if (id == R.id.mnuStar)
        {

        }
        else if (id == R.id.nav_manage)
        {

        }
        else if (id == R.id.nav_share)
        {

        }
        else if (id == R.id.nav_send)
        {

        }
        else if ( item.getTitle().toString().contains( "Search" ))// if( id == R.id.search )
        {
            Intent intent = new Intent(MainActivity.this, conversationactivity.class);
            //EditText editText = (EditText) findViewById(R.id.edit_message);
            // /String message = editText.getText().toString();
            intent.putExtra( conversationactivity.EXTRA_MESSAGE, "Search_MODE" );
            startActivity(intent);
        }
        else if( mobjCache.containsKey(item.getTitle().toString() ))
        {
            Intent intent = new Intent(MainActivity.this, conversationactivity.class);
            //EditText editText = (EditText) findViewById(R.id.edit_message);
            // /String message = editText.getText().toString();
            intent.putExtra(conversationactivity.EXTRA_MESSAGE, "Search_MODE_TYPED");
            startActivity(intent);

            DataModel.SetSearchItem(item.getTitle().toString());
            //MainActivity.this.UpdateListBox(DataModel.SEARCH);
        }


        /*
        else if ( item.getTitle().toString().contains( "hdfc" ))
        {
            Intent intent = new Intent(MainActivity.this, conversationactivity.class);
            //EditText editText = (EditText) findViewById(R.id.edit_message);
            // /String message = editText.getText().toString();
            intent.putExtra(conversationactivity.EXTRA_MESSAGE, "Search_MODE_TYPED");
            startActivity(intent);

            DataModel.SetSearchItem("hdfc");
            //MainActivity.this.UpdateListBox(DataModel.SEARCH);
        }
        else if ( item.getTitle().toString().contains( "ksrtc" ))
        {
            DataModel.SetSearchItem( "ksrtc" );
            //MainActivity.this.UpdateListBox(DataModel.SEARCH);
            Intent intent = new Intent(MainActivity.this, conversationactivity.class);
            //EditText editText = (EditText) findViewById(R.id.edit_message);
            // /String message = editText.getText().toString();
            intent.putExtra( conversationactivity.EXTRA_MESSAGE, "Search_MODE_TYPED" );
            startActivity(intent);

        }
        else if ( item.getTitle().toString().contains( "ajmera" ))
        {
            DataModel.SetSearchItem( "ajmera" );
            //MainActivity.this.UpdateListBox(DataModel.SEARCH);
            Intent intent = new Intent(MainActivity.this, conversationactivity.class);
            //EditText editText = (EditText) findViewById(R.id.edit_message);
            // /String message = editText.getText().toString();
            intent.putExtra( conversationactivity.EXTRA_MESSAGE, "Search_MODE_TYPED" );
            startActivity(intent);

        }
        else if ( item.getTitle().toString().contains( "offer" ))
        {
            DataModel.SetSearchItem( "offer" );
            //MainActivity.this.UpdateListBox(DataModel.SEARCH);
            Intent intent = new Intent(MainActivity.this, conversationactivity.class);
            //EditText editText = (EditText) findViewById(R.id.edit_message);
            // /String message = editText.getText().toString();
            intent.putExtra( conversationactivity.EXTRA_MESSAGE, "Search_MODE_TYPED" );
            startActivity(intent);
        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
