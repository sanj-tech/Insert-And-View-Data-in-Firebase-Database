package com.jsstech.insertviewdatainfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowData extends AppCompatActivity {
    ListView listView;
    List<UserModel> list;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        list=new ArrayList<>();

        listView=findViewById(R.id.listV);
        databaseReference= FirebaseDatabase.getInstance().getReference("user");

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               list.clear();
               for (DataSnapshot usersnap:snapshot.getChildren()){
                   UserModel model=usersnap.getValue(UserModel.class);
                   list.add(model);
               }
               UserAdapter userAdapter=new UserAdapter(ShowData.this,list);
               listView.setAdapter(userAdapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }
}