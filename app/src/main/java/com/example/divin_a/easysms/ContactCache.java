package com.example.divin_a.easysms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Divin_A on 6/13/2016.
 */
public class ContactCache
{
    static HashMap<String, String> mobjCache = new HashMap<String, String>();

    /*public static void InitPhoneLookUp( Context context_i )
    {
        ContentResolver cr = context_i.getContentResolver();
        Cursor cursorPhone = cr.query(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME,ContactsContract.PhoneLookup.},
                null, null, null);
    }*/


    public static String GetContactNameFromID( Context context_i, String nContactID )
    {
       // nContactID = null;
       // return "JustSomeName";

       // return nContactID;

        String Displayname  = nContactID;

        if( nContactID == null || nContactID.isEmpty())
        {
            return nContactID;
        }

        if(mobjCache.containsKey(nContactID))//here item is key;item=Entertainment English
        {

            Displayname = mobjCache.get(nContactID);
            return  Displayname;
        }

        if( null == context_i )
        {
            return  Displayname;
        }

        try {


            ContentResolver cr = context_i.getContentResolver();
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                    Uri.encode(nContactID));
            Cursor cursorPhone = cr.query(uri,
                    new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},
                    null, null, null);

            //   Cursor cursorPhone = context_i.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
         //           new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
         //           ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ nContactID ,
         //           //ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
         //           //        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
         //           //        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
         //           null/*new String[]{nContactID}*/,
         //           null
         //           );

            if (cursorPhone.moveToFirst())
            {
                Displayname = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            }
            cursorPhone.close();
            mobjCache.put(nContactID, Displayname);
        }
        catch (Exception ex)
        {
            Toast.makeText(context_i, ex.toString(), Toast.LENGTH_SHORT).show();
        }
        return  Displayname;
    }
}
