package com.example.divin_a.easysms;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Divin_A on 6/13/2016.
 */

class SMSInfo
{
    public String csAddress;
    public String csPersonID;
    public String csThread;
    public String Date;
}


class ConversationInfo
{
    public String csAddress;
    public String Msg;
    public String InOrOut;
    public String date;
}

public class SMSUtils
{
    private static ArrayList<SMSInfo> mObjAllSms =  new ArrayList<SMSInfo>();
    // private static ArrayList<SMSInfo> mAllSms =  new ArrayList<~>();

    private static ArrayList<SMSInfo> mbAllContacts =  new ArrayList<SMSInfo>();
    private static ArrayList<SMSInfo> mbAllUnknown =  new ArrayList<SMSInfo>();


    private static ArrayList<SMSInfo> mbAllBulks =  new ArrayList<SMSInfo>();

    private static boolean mbInit = false;
    private static boolean mbFilter = false;

    private static Object mobjFiler = new Object();
    private static HashMap<String, String> mObjThreadCache = new HashMap<String, String>();

    public static ArrayList<SMSInfo> GetAllAddressFromSMS( Context context_i )
    {
        if( !mbInit )
        {
            _dbGetAllAddressFromSMS( context_i );
            mbInit = true;
        }
        return mObjAllSms;
    }


    public static ArrayList<ConversationInfo> Search( Context context_i, String csSearch )
    {
        ArrayList<ConversationInfo> list = new ArrayList<>();

        String arg =  "'%" +csSearch.toLowerCase()+ "%'";
        String[] reqCols = new String[] { "address", "person", "body","date", "type" };// , "service_center" };
       // Cursor cursor = context_i.getContentResolver().query(Uri.parse("content://sms/"), reqCols, "body ="+"'"+strThread+"'", null, null );
        Cursor cursor = context_i.getContentResolver().query(Uri.parse("content://sms"), reqCols, "lower(body) LIKE "+arg , null, null );
        int nnCount = 0;
        if (cursor.moveToFirst())
        {
            do {

                try {
                    String strperson = cursor.getString(cursor.getColumnIndexOrThrow("person"));
                    String strBody = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                    String strAddrtess = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                    String strDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                    String strType = cursor.getString(cursor.getColumnIndexOrThrow("type"));


                    ConversationInfo info = new ConversationInfo();
                    info.csAddress = strAddrtess;
                    info.Msg = strBody;
                    info.InOrOut = strType;
                    info.date = strDate;

                    list.add(0, info);
                    // list.add(info );
                } catch (Exception ex)
                {

                }

            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }


    public static ArrayList<ConversationInfo> GetConversation( Context context_i, String strAddress )
    {
        ArrayList<ConversationInfo> list = new ArrayList<>();

        String strThread = mObjThreadCache.get(strAddress);
        String[] reqCols = new String[] { "address", "person", "body","date", "type" };// , "service_center" };
        Cursor cursor = context_i.getContentResolver().query(Uri.parse("content://sms/"), reqCols, "thread_id ="+"'"+strThread+"'", null, null );
        int nnCount = 0;
        if (cursor.moveToFirst())
        {
            do {

                try {
                    String strperson = cursor.getString(cursor.getColumnIndexOrThrow("person"));
                    String strBody = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                    String strAddrtess = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                    String strDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                    String strType = cursor.getString(cursor.getColumnIndexOrThrow("type"));


                    ConversationInfo info = new ConversationInfo();
                    info.csAddress = strAddrtess;
                    info.Msg = strBody;
                    info.InOrOut = strType;
                    info.date = strDate;

                    list.add(0, info);
                    // list.add(info );
                } catch (Exception ex)
                {

                }

            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }


    public static ArrayList<ConversationInfo> GetDraft( Context context_i )
    {
        ArrayList<ConversationInfo> list = new ArrayList<>();
        String[] reqCols = new String[] { "address", "person", "body","date", "type" };// , "service_center" };
        Cursor cursor = context_i.getContentResolver().query(Uri.parse("content://sms/draft"), reqCols, null, null, null );
        int nnCount = 0;
        if (cursor.moveToFirst())
        {
            do {
                String strperson = cursor.getString(cursor.getColumnIndexOrThrow("person"));
                String strBody = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                String strAddrtess = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String strDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String strType = cursor.getString(cursor.getColumnIndexOrThrow("type"));


                ConversationInfo info = new ConversationInfo();
                info.csAddress = strAddrtess;
                info.Msg = strBody;
                info.InOrOut = strType;
                info.date = strDate;

                // list.add(0, info );
                list.add(info );

            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }


    public static ArrayList<ConversationInfo> GetSentItems( Context context_i )
    {
        ArrayList<ConversationInfo> list = new ArrayList<>();
        String[] reqCols = new String[] { "address", "person", "body","date", "type" };// , "service_center" };
        Cursor cursor = context_i.getContentResolver().query(Uri.parse("content://sms/sent"), reqCols, null, null, null );
        int nnCount = 0;
        if (cursor.moveToFirst())
        {
            do {
                String strperson = cursor.getString(cursor.getColumnIndexOrThrow("person"));
                String strBody = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                String strAddrtess = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String strDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String strType = cursor.getString(cursor.getColumnIndexOrThrow("type"));


                ConversationInfo info = new ConversationInfo();
                info.csAddress = strAddrtess;
                info.Msg = strBody;
                info.InOrOut = strType;
                info.date = strDate;

                // list.add(0, info );
                list.add(info );

            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }


    public static ArrayList<SMSInfo> GetContactNamesFromInbox( Context context_i )
    {
        FilterSMS( context_i);
        return mbAllContacts;
    }

    public static ArrayList<SMSInfo> GetUnknownNames( Context context_i )
    {
        FilterSMS( context_i);
        return mbAllUnknown;
    }

    public static ArrayList<SMSInfo> GetBulkNames( Context context_i )
    {
        FilterSMS( context_i);
        return mbAllBulks;
    }

    private static void FilterSMS(Context context_i)
    {
        synchronized (mobjFiler)
        {
            if (!mbFilter) {
                for (int nindx = 0; nindx < mObjAllSms.size(); ++nindx) {
                    SMSInfo info = mObjAllSms.get(nindx);
                    // if(info.csPersonID != null || info.csAddress.matches("\\d+"))
                    if (info.csPersonID != null || info.csAddress != ContactCache.GetContactNameFromID(context_i, info.csAddress)) {
                        mbAllContacts.add(info);
                    }
                    else if (IsJustAPhoneNumber(info.csAddress))
                    {
                        mbAllUnknown.add(info);
                    } else {
                        mbAllBulks.add(info);
                    }
                }
                mbFilter = true;
            }
        }
    }

    private static boolean IsJustAPhoneNumber( String strAddress )
    {
        // strAddress.replaceAll()
        strAddress= strAddress.replace("+","");
        strAddress= strAddress.replace("-","");
        strAddress= strAddress.replace(" ","");

        /*boolean parsable = true;
        try
        {
            Integer.parseInt(strAddress);
        }catch(NumberFormatException e)
        {
            parsable = false;
        }
        return parsable;
*/


 String regex = "[\\d]+";
       // String regex = "^+?\\d{10}$";
       if( strAddress.matches(regex))
       {
           return true;
       }
        else
       {return  false;}

    }



    private static ArrayList<String> GetContactNamesFromInbox__DB( Context context_i )
    {
        ArrayList<String> AllSms =  new ArrayList<String>();

        //String arg = "'%"+ "hmm" +"%'";
        // String arg =  "'%hmm%'" ;
        // String arg =  "'%hdfc%'" ;

        // String[] reqCols = new String[] { "address", "person", "body","date" };// , "service_center" };
       // String[] reqCols = new String[] { "DISTINCT address" };// , "service_center" };

        String[] reqCols = new String[] { "address" };// , "service_center" };

        // "lower(body) LIKE "+arg
        // Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), reqCols, null , null, null );
        // Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), reqCols, "person IS NOT NULL" , null, null );
        // Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), reqCols, "lower(body) LIKE "+arg , null, null );
        // Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), reqCols, "body LIKE "+arg , null, null );

        // Cursor cursor = context_i.getContentResolver().query(Uri.parse("content://sms/inbox"), reqCols, "person IS NOT NULL) GROUP BY (address", null, null );
        Cursor cursor = context_i.getContentResolver().query(Uri.parse("content://sms/inbox"), reqCols, "person IS NOT NULL", null, null );
        int nnCount = 0;
        if (cursor.moveToFirst())
        {
            HashMap<String, String> mobjCache = new HashMap<String, String>();

            do
            {
                String strperson = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                // String strBody = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                // String strAddrtess = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                //String strDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                //if( strperson != null )// && strBody.toLowerCase().contains( "hmm" ) )
                // if(strBody.toLowerCase().contains( "hmm" ))
                {


                    // MySms objSms = new MySms();
                    // objSms.Address = strAddrtess;
                    ///objSms.Person = strperson;// GetContactNameFromID(cursor.getString(cursor.getColumnIndexOrThrow("person")));
                    // objSms.Message = strBody;
                    // objSms.Date = strDate;
                    // objSms.ServiceCenter = cursor.getString(cursor.getColumnIndexOrThrow("service_center"));


                    // ArrayList<MySms> smsfrom
                    //String value = map.get("x"); // value = "y"
                    //if( )

                    if(mobjCache.containsKey(strperson))//here item is key;item=Entertainment English
                    {
                    }
                    else
                    {
                        mobjCache.put(strperson, "fdgsdf");
                        AllSms.add(strperson);
                    }
                }

                /*for(int idx=0;idx<cursor.getColumnCount();idx++)
                {
                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                }*/

                // String str = cursor.getString(cursor.getColumnIndexOrThrow("person"));

                // if(null != str)
                //  {
                //      msgData += str +" :: ";

                //  }


                // for( int i = 0; i < cursor.getColumnCount(); i++)
                // {
                //info.append(cursor.getColumnName(i) + ":"+cursor.getString(i) + "\n");
                // }
                // info.append("\n\n\n\n");
                //  Toast.makeText(getApplicationContext(), info.toString(), Toast.LENGTH_LONG).show();

                // if( ++nnCount == 20 )
                //  {
                //     break;
                // }
                // use msgData
            } while (cursor.moveToNext());
            // ((ListView)findViewById(R.id.txtSMS)).set(info.toString());
            //Toast.makeText(this, msgData,
            //      Toast.LENGTH_LONG).show();
            cursor.close();
        }

        return AllSms;
    }



    private static void _dbGetAllAddressFromSMS( Context context_i )
    {
        mObjAllSms.clear();
        //mAllSms.clear();

     //   Toast.makeText(context_i,  "_dbGetAllAddressFromSMS", Toast.LENGTH_SHORT).show(); ;

       //  ArrayList<String> AllSms =  new ArrayList<String>();

        //String arg = "'%"+ "hmm" +"%'";
        // String arg =  "'%hmm%'" ;
        // String arg =  "'%hdfc%'" ;

        // String[] reqCols = new String[] { "address", "person", "body","date" };// , "service_center" };
        String[] reqCols = new String[] { "address", "person", "thread_id", "date" };// , "service_center" };
        // "lower(body) LIKE "+arg
        // Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), reqCols, null , null, null );
        // Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), reqCols, "person IS NOT NULL" , null, null );
        // Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), reqCols, "lower(body) LIKE "+arg , null, null );
        // Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), reqCols, "body LIKE "+arg , null, null );

        try
        {
        // , null, null
         Cursor cursor = context_i.getContentResolver().query(Uri.parse("content://sms/inbox"), reqCols, null, null, "date DESC" );

       // Cursor cursor = context_i.getContentResolver().query(Uri.parse( "content://mms-sms/conversations?simple=true"), null, null, null, "normalized_date desc" );

        int nnCount = 0;
        if (cursor.moveToFirst())
        {

           // Toast.makeText(context_i,  "Move to first", Toast.LENGTH_SHORT).show(); ;
            do {
                String strperson = cursor.getString(cursor.getColumnIndexOrThrow("person"));
                // String strBody = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                String strAddrtess = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String strThread = cursor.getString(cursor.getColumnIndexOrThrow("thread_id"));

                String strDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                //if( strperson != null )// && strBody.toLowerCase().contains( "hmm" ) )
                // if(strBody.toLowerCase().contains( "hmm" ))
                {


                    // MySms objSms = new MySms();
                    // objSms.Address = strAddrtess;
                    ///objSms.Person = strperson;// GetContactNameFromID(cursor.getString(cursor.getColumnIndexOrThrow("person")));
                    // objSms.Message = strBody;
                    // objSms.Date = strDate;
                    // objSms.ServiceCenter = cursor.getString(cursor.getColumnIndexOrThrow("service_center"));


                    // ArrayList<MySms> smsfrom
                    //String value = map.get("x"); // value = "y"
                    //if( )

                    if (mObjThreadCache.containsKey(strAddrtess))//here item is key;item=Entertainment English
                    {
                    } else {
                        mObjThreadCache.put(strAddrtess, strThread);
                       // mAllSms.add(strAddrtess);

                        SMSInfo info = new SMSInfo();
                        info.csAddress = strAddrtess;
                        info.csPersonID = strperson;
                        info.csThread = strThread;
                        info.Date = strDate;

                        mObjAllSms.add(info);
                    }
                }
            } while (cursor.moveToNext());

            cursor.close();

            int nLen = mObjAllSms.size();
          //  Toast.makeText(context_i,  Integer.toString(nLen), Toast.LENGTH_LONG).show(); ;

           final Context con =  context_i;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try
                    {
                        FilterSMS( con );
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();
        }
        }
        catch( Exception e )
        {
            Toast.makeText(context_i,  e.toString(), Toast.LENGTH_SHORT).show(); ;
        }

      //  return AllSms;
    }
}
