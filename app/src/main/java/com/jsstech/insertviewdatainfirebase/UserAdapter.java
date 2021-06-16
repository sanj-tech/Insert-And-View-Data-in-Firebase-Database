package com.jsstech.insertviewdatainfirebase;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserAdapter extends ArrayAdapter<UserModel> {

    Activity context;
    List<UserModel> list;

    public UserAdapter(@NonNull Activity context,List<UserModel> list) {
        super(context,R.layout.userlistlayout,list);
        this.context =context;
        this.list = list;

    }

    @NonNull
    @Override
    public View getView(int position,@Nullable View convertView,@NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        convertView=inflater.inflate(R.layout.userlistlayout,null,true);

        TextView txt_name=(TextView) convertView.findViewById(R.id.txtName);

        TextView txt_email=(TextView) convertView.findViewById(R.id.txtEmail);

        TextView txt_contact=(TextView) convertView.findViewById(R.id.txtContact);

        TextView txt_city=(TextView) convertView.findViewById(R.id.txtcity);
        TextView txt_lang=(TextView) convertView.findViewById(R.id.txtLang);

        UserModel model=list.get(position);
        txt_name.setText(model.getName());
        txt_email.setText(model.getEmail());
        txt_contact.setText(model.getContact());
        txt_city.setText(model.getCity());
        txt_lang.setText(model.getLang());



        return convertView;
    }
}
