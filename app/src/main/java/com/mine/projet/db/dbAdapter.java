package com.mine.projet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class dbAdapter {
    private static final String LOGIN_TABLE = "user";
    //Table unique id
    public static final String COL_ID = "id";
    //Table username and password columns
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";

    private Context context;
    private SQLiteDatabase database;
    private dbHelper dbHelper;

    public dbAdapter(Context context) {
        this.context = context;
    }


    public dbAdapter open() throws SQLException {
        dbHelper = new dbHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }


    public void close() {
        dbHelper.close();
    }


    public long createUser(String username, String password) {
        ContentValues initialValues = createUserTableContentValues(username, password);
        return database.insert(LOGIN_TABLE, null, initialValues);
    }


    public boolean deleteUser(long rowId) {
        return database.delete(LOGIN_TABLE, COL_ID + "=" + rowId, null) > 0;
    }

    public boolean updateUserTable(long rowId, String username, String password) {
        ContentValues updateValues = createUserTableContentValues(username, password);
        return database.update(LOGIN_TABLE, updateValues, COL_ID + "=" + rowId, null) > 0;
    }


    public Cursor fetchAllUsers() {
        return database.query(LOGIN_TABLE, new String[] { COL_ID, COL_USERNAME,
                COL_PASSWORD }, null, null, null, null, null);
    }


    public Cursor fetchUser(String username, String password) {
        Cursor myCursor = database.query(LOGIN_TABLE,
                new String[] { COL_ID, COL_USERNAME, COL_PASSWORD },
                COL_USERNAME + "='" + username + "' AND " +
                        COL_PASSWORD + "='" + password + "'", null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    public Cursor fetchUserById(long rowId) throws SQLException {
        Cursor myCursor = database.query(LOGIN_TABLE,
                new String[] { COL_ID, COL_USERNAME, COL_PASSWORD },
                COL_ID + "=" + rowId, null, null, null, null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    private ContentValues createUserTableContentValues(String username, String password) {
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        return values;
    }
}
