package com.example.divin_a.easysms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Divin_A on 6/15/2016.
 */
public class DataModel
{
    private static  boolean mbLockMode = true;
    public  static final  int INBOX_ALL = 1;
    public  static final  int INBOX_PEOPLE = 2;
    public  static final  int INBOX_BULKS = 3;
    public  static final  int INBOX_UNKNOWN = 4;

    public  static final  int INBOX_CONVERSATION = 5;
    public  static final  int SENT = 6;
    public  static final  int DRAFT = 7;
    public  static final  int OUTBOX = 8;

    public  static final  int SEARCH = 9;


    private static int CurrentSMSDisplayMode = -1;


    public static int GetCurrentSMSDisplayMode()
    {
        return CurrentSMSDisplayMode;
    }

    public static void SetCurrentSMSDisplayMode(int nMode_i )
    {
        CurrentSMSDisplayMode = nMode_i;
    }



    private  static  String mcsSearch;
    public static String GetSearchItem()
    {
        return mcsSearch;
    }
    public static void SetSearchItem(String csSearcn )
    {
        mcsSearch = csSearcn;
    }

    public static boolean IsSenderProtected( Context con, String strAddress )
    {
        return  false;
      //  String str = ContactCache.GetContactNameFromID( con, strAddress);
      //  if( str.trim().equalsIgnoreCase( "Janet") )
       // {
      //      return mbLockMode;
       // }
        // return  false;
    }

    public static boolean DoesContentHasProtectedWorkds( String strContent )
    {
        return  false;
      //  if(strContent.contains("080"))
       // {
       //     return mbLockMode;
       // }
       //  return  false;
    }

    public  static  void Lock()
    {
        mbLockMode = true;
    }

    public  static  void UnLock( Context cntx )
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(cntx);
        builder.setTitle("UnLock");

// Set up the input
        final EditText input = new EditText(cntx);

        final Context con = cntx;

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().contains("0871"))
                {
                    mbLockMode = false;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run()
                        {
                            Lock();
                        }
                    }, 20000);
                } else
                {
                    Toast.makeText(con, "WrongPassword", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        builder.show();



    }
}
