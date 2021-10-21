package com.example.roomdatabasedemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomdatabasedemo.model.User;
import com.example.roomdatabasedemo.model.responsity.database.userDatabase;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST = 1;
    EditText edUsername, edPass;
    Button btn;
    TextView textView;
    EditText edSearch;
    RecyclerView rv;
    private UserAdapter adapter;
    List<User> userList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getID();
        adapter=new UserAdapter(new UserAdapter.clickUpdate() {
            @Override
            public void updateUser(User user) {
                clickUpdateUser(user);
            }

            @Override
            public void deleteUser(User user) {
                clickDeleteUser(user);
            }
        });
        userList=new ArrayList<>();
        adapter.setData(userList);
        LinearLayoutManager layout=new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setAdapter(adapter);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAll();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=edUsername.getText().toString().trim();
                String password=edPass.getText().toString().trim();
                if(userName.isEmpty() || password.isEmpty())
                {
                    return;
                }else{
                    User user=new User(userName, password);
                    if (isUserExists(user))
                    {
                        Toast.makeText(getBaseContext(), "It is exited", Toast.LENGTH_SHORT).show();


                    }
                    else{
                        userDatabase.getInstance(getBaseContext()).userDAO().insertUser(user);
                        Toast.makeText(getBaseContext(), "Successfully!!", Toast.LENGTH_SHORT).show();
                        edPass.setText("");
                        edUsername.setText("");
                        hiddenKeyboard();
                        loadData();
                    }

                }
            }
        });
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH)
                {
                    searchUser();
                }
                return false;
            }
        });
        loadData();

    }

    private void searchUser() {
        String str=edSearch.getText().toString().trim();
        userList=new ArrayList<>();
        userList=userDatabase.getInstance(this).userDAO().searchName(str);
        adapter.setData(userList);
        hiddenKeyboard();
    }

    private void deleteAll() {
        new  AlertDialog.Builder(this).setTitle("Message")
                .setMessage("Are you sure delete?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete
                        userDatabase.getInstance(getBaseContext()).userDAO().deleteAll();
                        loadData();
                    }
                }).setNegativeButton("No", null).show();
    }

    private void clickDeleteUser(User user) {
       new AlertDialog.Builder(this).setTitle("Message")
               .setMessage("Are you sure delete?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               // delete
               userDatabase.getInstance(getBaseContext()).userDAO().deleteUser(user);
               loadData();
           }
       }).setNegativeButton("No", null).show();
    }

    private void clickUpdateUser(User user) {
        Intent intent=new Intent(MainActivity.this, UpdateActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivityForResult(intent, MY_REQUEST);
    }

    public void getID()
    {
        edPass=findViewById(R.id.edPassword);
        edUsername=findViewById(R.id.edUsername);
        btn=findViewById(R.id.btn);
        rv=findViewById(R.id.rvList);
        textView=findViewById(R.id.tvDelete);
        edSearch=findViewById(R.id.searchView);
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
    public void loadData()
    {
        userList=userDatabase.getInstance(getBaseContext()).userDAO().getList();
        adapter.setData(userList);
    }
    public boolean isUserExists(User user)
    {
        List<User> list=userDatabase.getInstance(this).userDAO().checkUser(user.getUsername());
        return list!=null && !list.isEmpty();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadData();
    }
}