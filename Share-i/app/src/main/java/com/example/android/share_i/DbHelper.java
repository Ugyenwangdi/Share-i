package com.example.android.share_i;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "USER_RECORD";
    private static final String TABLE_NAME = "USER_DATA";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "USERNAME";
    private static final String COL_3 = "PASSWORD";
    private static final String COL_4 = "IMGID";
    private static final String TABLE_FEEDBACK = "USER_FEEDBACK";
    private static final String COL1 = "NAME";
    private static final String COL2 = "FEEDBACK";



    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB)
    {
        DB.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, IMGID INTEGER)");

        DB.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_FEEDBACK + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, FEEDBACK TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion)
    {
        DB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        DB.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);
        onCreate(DB);
    }

    public boolean registerUser(String username, String password, int imgid)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, imgid);
        //contentValues.put("image", image);
        long result = DB.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean feedback(String name, String feedbask)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, name);
        contentValues.put(COL2, feedbask);
        //contentValues.put("image", image);
        long result = DB.insert(TABLE_FEEDBACK, null, contentValues);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean checkUser(String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_1};
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String [] selectionargs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionargs, null, null, null);
        int count = cursor.getCount();

        db.close();
        cursor.close();
        if (count > 0 ) {
            return true;
        } else
            return false;

    }

    public Boolean checkusernamepassword(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USER_DATA  where USERNAME = ?", new String[] {username});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USER_DATA where USERNAME = ? and PASSWORD = ?", new String[] {username, password});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }

    public int getImgID(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USER_DATA  where USERNAME = ?", new String[] {username});

        int id;
        cursor.moveToFirst();
        id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("IMGID")));
        MyDB.close();
        return id;
    }

    public String getName(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select USERNAME from USER_DATA  where USERNAME = ?", new String[] {username});

        if (cursor.getCount() > 0)
            return username;
        else
            return null;
    }

    public boolean updateuserdata(String name, int id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4, id);
        Cursor cursor = DB.rawQuery("Select * from USER_DATA where USERNAME=?", new String[] {name});

        if(cursor.getCount()>0)
        {
            long result = DB.update(TABLE_NAME, contentValues, "USERNAME=?", new String[]{name});

            if (result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }

    }

    public Boolean deleteuserdata(String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where name=?", new String[] {name});

        if(cursor.getCount()>0)
        {
            long result = DB.delete("Userdetails", "name=?", new String[]{name});

            if (result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public Cursor getdata()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
        return  cursor;
    }
}
