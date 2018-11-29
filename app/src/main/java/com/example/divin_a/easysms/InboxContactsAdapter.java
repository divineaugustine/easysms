package com.example.divin_a.easysms;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Divin_A on 6/13/2016.
 */
public class InboxContactsAdapter extends BaseAdapter
{
    private Activity activity;
    private ArrayList<SMSInfo> data;
    private static LayoutInflater inflater=null;
    // public ImageLoader imageLoader;

    public InboxContactsAdapter(Activity a, ArrayList<SMSInfo> conversation)
    {
        activity = a;
        data=conversation;
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

    public static class ViewHolderNames
    {
        //public TextView Message;
        public TextView ContactNum;
        public TextView Name;
        public TextView Date;

        public String csContactNum;
     //   public boolean bPerson;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi=convertView;
        ViewHolderNames holder;

        if(convertView==null)
        {

            vi = inflater.inflate(R.layout.inboxrow, null);
           // vi = inflater.inflate(android.R.layout.simple_list_item_1, null);
            //  ((MainActivity)activity).IncrCounter(position);
            holder = new ViewHolderNames();
            holder.Name = (TextView) vi.findViewById(R.id.Name);
            holder.Date = (TextView) vi.findViewById(R.id.inboxDate);
            holder.ContactNum  = (TextView) vi.findViewById(R.id.Number);

            // holder.Name = (TextView) vi.findViewById(R.id.text1);
            // holder.ContactNum = (TextView) vi.findViewById(R.id.Number);
            //  holder.Person = (TextView) vi.findViewById(R.id.Person);
            vi.setTag(holder);
        }
        else
        {
            holder = (ViewHolderNames)convertView.getTag();
        }

        //  ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

        SMSInfo item = data.get(position);
        holder.csContactNum = item.csAddress;
        // Setting all values in listview
        //holder.Message.setText( item.Message);

        holder.ContactNum.setText(item.csAddress);
        holder.Name.setText( ContactCache.GetContactNameFromID(activity, item.csAddress));

        //Date date = new Date(item.Date);
       // String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);



        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(item.Date));
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        holder.Date.setText(date);

        // holder.bPerson =  holder.Name.toString() == item? true:false;

     return vi;
    }
}
