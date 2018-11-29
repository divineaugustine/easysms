package com.example.divin_a.easysms;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class composemsg extends AppCompatActivity {

    LinearLayout lVNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composemsg);

        lVNameList = (LinearLayout)findViewById(R.id.lVNameList);
        Button btAddNumber = (Button)findViewById( R.id.btAddNumber );
        btAddNumber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                TextView view = new TextView(composemsg.this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 15, 0, 0);
                params.gravity = Gravity.LEFT;
                view.setLayoutParams(params);

                EditText edtNUm = (EditText)findViewById(R.id.edtNumber );
                view.setBackgroundResource(R.drawable.roundbluebg);
                view.setText(edtNUm.getText().toString());
                lVNameList.addView(view);
            }});

        setupSearchView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (ContactsContract.Intents.SEARCH_SUGGESTION_CLICKED.equals(intent.getAction())) {
            //handles suggestion clicked query
            String displayName = getDisplayNameForContact(intent);

            Toast.makeText(composemsg.this, displayName, Toast.LENGTH_LONG).show();


            TextView view = new TextView(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 15, 0, 0);
            params.gravity = Gravity.LEFT;
            view.setLayoutParams(params);

            view.setBackgroundResource(R.drawable.roundbluebg);
            view.setText(displayName);
            lVNameList.addView(view);

            // resultText.setText(displayName);
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // handles a search query
            String query = intent.getStringExtra(SearchManager.QUERY);

            Toast.makeText(composemsg.this, query, Toast.LENGTH_LONG).show();

            //resultText.setText("should search for query: '" + query + "'...");
        }
    }

    private String getDisplayNameForContact(Intent intent) {
        Cursor phoneCursor = getContentResolver().query(intent.getData(), null, null, null, null);
        phoneCursor.moveToFirst();
        int idDisplayName = phoneCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        String name = phoneCursor.getString(idDisplayName);
        phoneCursor.close();
        return name;
    }


    private void setupSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) findViewById(R.id.searchView);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
    }
}
