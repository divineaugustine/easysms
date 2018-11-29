package com.example.divin_a.easysms;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Divin_A on 6/18/2016.
 */


public class ConversationAdapter extends BaseAdapter implements SectionIndexer
{
    private Activity activity;
    private ArrayList<ConversationInfo> data;
    private static LayoutInflater inflater=null;
    // public ImageLoader imageLoader;
    // private static String sections = "abcdefghilmnopqrstuvz";
    private String[] msections = null;//new String[0];

    private HashMap<String,Integer> hmsectionmp = new HashMap<>();
    private HashMap<Integer,Integer> mhPosToSection = new HashMap<>();

    private void UpdateSections()
    {
        Integer nLastSection = 0;
        ArrayList<String> str = new ArrayList<>();
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        for( int nIndex = 0; nIndex < data.size(); ++nIndex )
        {
            cal.setTimeInMillis(Long.parseLong(data.get(nIndex).date));
            String date = DateFormat.format("yyyy", cal).toString();

            if( hmsectionmp.containsKey(date))
            {

            }
            else
            {
                hmsectionmp.put(date, nIndex);
                str.add(date);
                nLastSection = str.size()-1;
            }
            mhPosToSection.put(nIndex, nLastSection );
        }

        msections = new String[str.size()];
        for (int i=0; i < str.size(); i++)
        {
            msections[i] = str.get(i);
        }
    }

    public ConversationAdapter(Activity a, ArrayList<ConversationInfo> d )
    {
        activity = a;
        data=d;
       UpdateSections();
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        /// imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount()
    {
        return data.size();
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position)
    {
        ConversationInfo item = data.get(position);
        if( item.InOrOut.contains("2"))
        {
            return 0;
        }
        else
        {
            return 1;
        }
        // return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    public static class ViewHolderNames
    {
        public TextView Sender;
        public TextView Msg;
        public TextView Date;
        public View parentView;

        public String csContactNum;
        //   public boolean bPerson;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi=convertView;
        ViewHolderNames holder;

        ConversationInfo item = data.get(position);
        boolean bMsgIn = true;
        if( item.InOrOut.contains("2"))
        {
            bMsgIn = false;
        }
        else
        {
            bMsgIn = true;
        }


        if(convertView==null)
        {
            // vi = inflater.inflate(android.R.layout.simple_list_item_2, null);
//            vi = inflater.inflate(R.layout.mychat, null);
            if( bMsgIn )
            {
                vi = inflater.inflate(R.layout.hischat, null);
            }
            else
            {
                vi = inflater.inflate(R.layout.mychat, null);

            }
            // vi = inflater.inflate(R.layout.conversationrow, null);

            //  ((MainActivity)activity).IncrCounter(position);
            holder = new ViewHolderNames();
            holder.Msg = (TextView) vi.findViewById(R.id.Msg);
            holder.Date= (TextView) vi.findViewById(R.id.convDate);
            holder.parentView = (View)vi.findViewById(R.id.content );
            holder.Sender = (TextView) vi.findViewById(R.id.sender);
            vi.setTag(holder);
        }
        else
        {
            holder = (ViewHolderNames)convertView.getTag();
        }

        holder.csContactNum = item.csAddress;

        boolean bProtected = DataModel.IsSenderProtected( activity, item.csAddress );
        boolean bContentProtected = DataModel.DoesContentHasProtectedWorkds(item.Msg );

        if( bProtected   )
        {
            holder.Msg.setText("Protected Contact");
        }
        else if( bContentProtected )
        {
            holder.Msg.setText("Protected contents");
        }
        else
        {
            holder.Msg.setText(item.Msg);
            holder.Msg.setFocusable(true);
            holder.Msg.setFocusableInTouchMode(true);
        }
        holder.Msg.setTextIsSelectable(true);

       // int sizeInDp = 5;
       // float scale = activity.getResources().getDisplayMetrics().density;
       // int dpAsPixels = (int) (sizeInDp*scale + 0.5f);

        RelativeLayout rl = (RelativeLayout)vi;
        if( bMsgIn )
        {
           rl.setGravity(Gravity.LEFT);

          //  holder.Date.setGravity(Gravity.LEFT);
            holder.parentView.setBackgroundResource( R.drawable.hisbubble2 );
        }
        else
        {
            rl.setGravity(Gravity.RIGHT);
         //   holder.Date.setGravity(Gravity.RIGHT);
            holder.parentView.setBackgroundResource(R.drawable.mybubble2 );
        }



        if( bMsgIn )
        {
            if( DataModel.GetCurrentSMSDisplayMode() == DataModel.SEARCH )
            {
                holder.Sender.setText( ContactCache.GetContactNameFromID(activity, item.csAddress));
            }
            else
            {
                holder.Sender.setText( "" );
            }
        }
        else
        {
            if( DataModel.GetCurrentSMSDisplayMode() == DataModel.SEARCH ||
                    DataModel.GetCurrentSMSDisplayMode() == DataModel.SENT )
            {
                holder.Sender.setText( ContactCache.GetContactNameFromID(activity, item.csAddress));
            }
            else
            {
                holder.Sender.setText( "" );
            }
            // vi.setBackgroundColor(Color.GRAY);

        }


        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(item.date));
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        holder.Date.setText(date);




       /* if( !holder.bPerson && DataModel.GetCurrentSMSDisplayMode() == DataModel.INBOX_PEOPLE )
        {
            // vi.setVisibility(View.INVISIBLE );
            data.remove(position);
            notifyDataSetChanged();
        }*/

        return vi;
    }


    @Override
    public int getPositionForSection(int section)
    {
       //  Log.d("ListView", "Get position for section");
      //  for (int i=0; i < this.getCount(); i++) {
           // String item = this.getItem(i). toLowerCase();
           // if (item.charAt(0) == sections.charAt(section))
           //     return i;
      //  }

        String secName = msections[section];
        return hmsectionmp.get(secName);
    }

    @Override
    public int getSectionForPosition(int arg0) {
        // Log.d("ListView", "Get section");


        int nIndx = 0;
        /*Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(data.get(arg0).date));
        String date = DateFormat.format("yyyy", cal).toString().trim();


        for (int i=0; i < msections.length; i++)
        {
            String str2 = msections[i].trim();
            if( str2.equalsIgnoreCase(date))
            {
                nIndx = i;
                break;
            }
        }
        return nIndx;*/


        nIndx = mhPosToSection.get( arg0 );
        return nIndx;
    }



    @Override
    public Object[] getSections()
    {
      //  Log.d("ListView", "Get sections");
       // String[] sectionsArr = new String[sections.length()];
       // for (int i=0; i < sections.length(); i++)
           //     sectionsArr[i] = "" + sections.charAt(i);

        return msections;

    }

}
