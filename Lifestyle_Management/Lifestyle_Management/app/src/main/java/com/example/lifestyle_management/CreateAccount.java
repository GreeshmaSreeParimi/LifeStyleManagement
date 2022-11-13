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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkData=checkDataEntered();
                if(checkData==true)
                {
                    Intent intent=new Intent(CreateAccount.this,LandingPage.class);
                    startActivity(intent);

                }

                SharedPreferences preferences = getSharedPreferences("MYPREFS",MODE_PRIVATE);
                String newUser  = name.getText().toString();
                String newPassword = password.getText().toString();
                String newEmail = email.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();

                //stores 3 new instances of sharedprefs. Both the user and password's keys are the same as the input.
                //Must be done this way because sharedprefs is stupid and inefficient. You cannot store Arrays easily
                //so I use strings instead.
                editor.putString("newUser",newUser);
                editor.commit();
                editor.putString("newPassword", newPassword);
                editor.commit();
                editor.putString("email", newEmail);
                editor.commit();
                editor.putString("newUser" + "newPassword" + "data", newUser + "\n" + newEmail);
                editor.commit();
            }});

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
