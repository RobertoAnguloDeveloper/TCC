package com.udc.tcc.model;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private Context context;
    private int dbVersion;
    private String dbName;
    private String [] queries;
    private String query;

    public DbHelper(@Nullable Context context, int dbVersion, String dbName, String [] queries) {
        super(context, dbName, null, dbVersion);
        this.dbVersion = dbVersion;
        this.dbName = dbName;
        this.queries = queries;
    }

    public DbHelper(@Nullable Context context, int dbVersion, String dbName, String query) {
        super(context, dbName, null, dbVersion);
        this.dbVersion = dbVersion;
        this.dbName = dbName;
        this.query = query;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        if(this.queries != null){
            for (int i = 0; i < this.queries.length; i++) {
                sqLiteDatabase.execSQL(this.queries[i]);
            }
        }

        if(!this.query.isEmpty()){
            sqLiteDatabase.execSQL(this.query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
}
