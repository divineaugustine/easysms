package com.example.divin_a.easysms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Divin_A on 7/18/2016.
 */


class  LabelInfo
{
    public LabelInfo( String name, String type)
    {
        Name = name;
        Type = type;
    }

    public String Name;
    public String Type;
}

public class LabelsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "EasySmsMaster.db";
    public static final String CONTACTS_TABLE_NAME = "LabelInfo";
    public static final String CONTACTS_COLUMN_NAME = "Name";
    public static final String CONTACTS_COLUMN_PHONE = "Type";
    private HashMap hp;

    public LabelsDBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table LabelInfo " +
                        "(id integer primary key, Name text,Type text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS LabelInfo");
        onCreate(db);
    }

    public boolean InsertLabel  (String Name, String Type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", Name);
        contentValues.put("Type", Type);
        db.insert("LabelInfo", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from LabelInfo where id="+id+"", null );
        return res;
    }

    public int numberOfRows()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String type )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Type", type);
        db.update("LabelInfo", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteLabel (String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("LabelInfo",
                "Name = ? ",
                new String[] { name });
    }

    public ArrayList<LabelInfo> getAllLabels()
    {
        ArrayList<LabelInfo> array_list = new ArrayList<LabelInfo>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from LabelInfo", null );
        res.moveToFirst();

        while(res.isAfterLast() == false)
        {
            String name = res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME));
            String num = res.getString(res.getColumnIndex(CONTACTS_COLUMN_PHONE));
            LabelInfo ctn = new LabelInfo( name, num );
            array_list.add(ctn);
            res.moveToNext();
        }
        res.close();
        return array_list;
    }
}