package com.example.roomdatabasedemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabasedemo.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {

    private List<User> userList;
    private clickUpdate update;

    public UserAdapter(clickUpdate update) {
        this.update = update;
    }

    public void setData(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        User user=userList.get(position);
        if(user==null)
        {
            return;
        }else{
           holder.tvUsername.setText(user.getUsername());
           holder.tvPassword.setText(user.getPassword());
           holder.btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   update.updateUser(user);
               }
           });
           holder.btndelete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   update.deleteUser(user);
               }
           });
        }
    }

    @Override
    public int getItemCount() {
        if(userList!=null)
        {
            return userList.size();
        }
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
       TextView tvUsername, tvPassword;
       Button btn, btndelete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvPassword=itemView.findViewById(R.id.tvPassword);
            tvUsername=itemView.findViewById(R.id.tvUsername);
            btn=itemView.findViewById(R.id.btnUpdate2);
            btndelete=itemView.findViewById(R.id.btnDel);
        }
    }
    public interface clickUpdate{
        void updateUser(User user);
        void deleteUser(User user);
    }
    public interface clickDelete{

    }
}
