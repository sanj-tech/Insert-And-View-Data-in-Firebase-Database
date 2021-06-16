package com.jsstech.insertviewdatainfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText edtname, edtemail, editcontact;
    private Spinner city_spinner;
    private AutoCompleteTextView autoCompleteTextView;
    private Button adduser;
    private ProgressBar progressBar;

    String strLang[] = {"java","Android","Ad.java","Vb.net"};
    ArrayAdapter arrayAdapter;

  DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtname=findViewById(R.id.etname);
        edtemail=findViewById(R.id.etemail);
        editcontact=findViewById(R.id.etcontact);
        city_spinner=findViewById(R.id.spinCity);
        autoCompleteTextView=findViewById(R.id.autoLang);
        adduser=findViewById(R.id.btAdd);
        progressBar=findViewById(R.id.progressBar);

        databaseReference=FirebaseDatabase.getInstance().getReference("user");

        arrayAdapter=new ArrayAdapter(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,strLang);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(1);



        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NAME=edtname.getText().toString().trim();
                String EMAIL=edtemail.getText().toString().trim();
                String CONTACT=editcontact.getText().toString().trim();
                String CITYSPINNER=city_spinner.getSelectedItem().toString().trim();
                String LANG=autoCompleteTextView.getText().toString().trim();
               // String PROGRESS=progressBar.getT
                
                if (NAME.isEmpty()){

                  edtname.setError("name required");
                  edtname.requestFocus();
                  return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
                    edtemail.setError("Enter valid Email Address");
                    edtemail.requestFocus();
                    return;

                }
                if (CONTACT.isEmpty()){
                    editcontact.setError("contact required");
                    editcontact.requestFocus();
                    return;
                }
                if (LANG.isEmpty()){
                    autoCompleteTextView.setError("Language required");
                    autoCompleteTextView.requestFocus();
                    return;
                }
                progressBar.setVisibility(view.VISIBLE);
                
                String uniqueId=databaseReference.push().getKey();
                UserModel userModel=new UserModel(uniqueId,NAME,EMAIL,CONTACT,CITYSPINNER,LANG);
                databaseReference.child(uniqueId).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"user Added",Toast.LENGTH_SHORT).show();
                            edtname.setText("");
                            edtemail.setText("");
                            editcontact.setText("");
                            autoCompleteTextView.setText("");
                            progressBar.setVisibility(View.GONE);


                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }







    public void ViewData(View view) {
        Intent intent = new Intent(MainActivity.this,ShowData.class);
        startActivity(intent);


    }
}