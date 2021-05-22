package com.mine.projet.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {
    // The database name and version
    private static final String DB_NAME = "login";
    private static final int DB_VERSION = 1;
    // The database user table
    private static final String DB_TABLE = "create table user (id integer primary key autoincrement, "
            + "username text not null, password text not null);";
    /**
     * Database Helper constructor.
     * @param context
     */
    public dbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    /**
     * Creates the database tables.
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DB_TABLE);
    }
    /**
     * Handles the table version and the drop of a table.
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS user");
        onCreate(database);
    }
}
