package com.example.lifestyle_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {
    TextView textview;
    EditText name,email,password,confirm_password;
    Button submit;
    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_acc_activity);

        textview=findViewById(R.id.create_account);
        name=findViewById(R.id.full_name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);

        submit=findViewById(R.id.create_submit_button);
        myDB = new DatabaseHelper(this);
        insertUser();

        }

    private void insertUser(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkData = checkDataEntered();
                if(checkData==true) {
                    boolean var = myDB.registerUser(email.getText().toString(), email.getText().toString(), password.getText().toString());
                    if (var) {
                         Toast.makeText(CreateAccount.this, "User Registered Successfully !!", Toast.LENGTH_SHORT).show();
                         boolean doesTableExist = myDB.doesTableExist("BREAKS_TABLE");
                         if(!doesTableExist) myDB.createBreaksTable();
                         myDB.addBreak("Work Break", "2022-12-1","2:30 pm", 121,1, email.getText().toString());
                         myDB.addBreak("Gym Break", "2022-12-9","6:30 am", 162,0,email.getText().toString());
                         myDB.addBreak("Water Break", "2022-11-30","11:30 am", 741,1,email.getText().toString());
                         startActivity(new Intent(CreateAccount.this , MainActivity.class));
                    } else
                        Toast.makeText(CreateAccount.this, "User Already Exists !!", Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    boolean isMatchPassword(EditText password,EditText confirm_password){
        CharSequence str1=password.getText().toString();
        CharSequence str2=confirm_password.getText().toString();
        if(TextUtils.equals(str1,str2))
            return true;
        else
            return false;
    }

    boolean checkDataEntered() {
        if (isEmpty(name)) {
            name.setError("Enter your full name");
            return false;
        }
        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
            return false;
        }
        if(isMatchPassword(password,confirm_password)==false){
            confirm_password.setError("Password does not match");
            return false;
        }

        return true;
    }




}
