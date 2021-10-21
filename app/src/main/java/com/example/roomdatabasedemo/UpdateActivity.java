package com.example.roomdatabasedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabasedemo.model.User;
import com.example.roomdatabasedemo.model.responsity.database.userDatabase;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    EditText edUsername, edPass;
    Button btn;
    RecyclerView rv;
    List<User> userList = null;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getID();
        userList = new ArrayList<>();
        user = (User) getIntent().getExtras().get("user");
        if (user != null) {
            edUsername.setText(user.getUsername());
            edPass.setText(user.getPassword());
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
                hiddenKeyboard();
            }
        });

    }

    private void updateUser() {
        String userName = edUsername.getText().toString().trim();
        String password = edPass.getText().toString().trim();
        user.setUsername(userName);
        user.setPassword(password);
        userDatabase.getInstance(getBaseContext()).userDAO().updateUser(user);
        Toast.makeText(getBaseContext(), "Successfully!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();

    }
    public void hiddenKeyboard()
    {
        try {
            InputMethodManager inp= (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inp.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e)
        {
            Toast.makeText(getBaseContext(), ""+e.getStackTrace(), Toast.LENGTH_SHORT).show();
        }
    }
    public void getID() {
        edPass = findViewById(R.id.edPassword);
        edUsername = findViewById(R.id.edUsername);
        btn = findViewById(R.id.btn);
        rv = findViewById(R.id.rvList);
    }
}
