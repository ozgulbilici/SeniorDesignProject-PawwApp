package com.finalproject.seniordesignproject.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

public class DatabaseHelper2 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, mail TEXT, name TEXT, username TEXT, password TEXT)";
        db.execSQL(createTableQuery);

        File dbFile = new File(db.getPath());
        if (dbFile.exists()) {
            Log.d("DatabaseHelper", "Database is created: " + dbFile.getAbsolutePath());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    // E-posta adresini veritabanında kontrol et
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM user WHERE mail = ?";
        String[] selectionArgs = { email };
        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return exists;
    }

    // Yeni kullanıcıyı veritabanına ekle
    public long addUser(String email, String name, String username, String password) {
        if (isEmailExists(email)) {
            Log.d("DatabaseHelper", "A user with the same email already exists");
            return -1;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("mail", email);
        values.put("name", name);
        values.put("username", username);
        values.put("password", password);

        long id = db.insert("user", null, values);

        db.close();

        return id;
    }

    // Kullanıcıyı e-posta ve şifreyle sorgula
    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM user WHERE mail = ? AND password = ?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return exists;
    }

    public boolean isUserExists(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM user WHERE mail = ? AND password = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return exists;
    }
}

