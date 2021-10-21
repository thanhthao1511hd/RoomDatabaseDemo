package com.example.roomdatabasedemo.model.responsity.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomdatabasedemo.model.User;
import com.example.roomdatabasedemo.model.responsity.dao.UserDAO;

@Database(entities = {User.class}, version = 1)
public abstract class userDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="user.db";
    private static userDatabase instance;
    public static synchronized  userDatabase getInstance(Context context)
    {
        if (instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(), userDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract UserDAO userDAO();
}
