package com.example.roomdatabasedemo.model.responsity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdatabasedemo.model.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(User user);
    @Query("select * from user")
    List<User> getList();

    @Query("select * from user where username = :username")
    List<User> checkUser(String username);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("delete from user")
    void deleteAll();

    @Query("select * from user where username like '%' || :name || '%'")
    List<User> searchName(String name);

}
