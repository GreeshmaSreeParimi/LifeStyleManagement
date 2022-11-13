package com.example.lifestyle_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etName = (EditText) findViewById(R.id.username);
        final EditText etPassword = (EditText) findViewById(R.id.password);

        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            final EditText userN = (EditText) findViewById(R.id.username);
            final EditText password = (EditText) findViewById(R.id.password);

            @Override
            public void onClick(View v) {
                if (userN.getText().toString().trim().length() == 0) {
                    userN.setError("Please enter the email");

                }
                else if (password.getText().toString().trim().length() == 0) {
                    password.setError("Please enter the password");
                }
                else {

                    String user = etName.getText().toString();
                    String password = etPassword.getText().toString();
                    SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);

                    String userDetails = preferences.getString(user + password + "data", "No information on that user.");
                    String userPrefPass = preferences.getString("newPassword", "");
                    String userPrefName = preferences.getString("email", "");

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("display", userDetails);
                    editor.commit();

                    if(user.equals(userPrefName) && password.equals(userPrefPass)) {
                        Intent displayScreen = new Intent(MainActivity.this, LandingPage.class);
                        startActivity(displayScreen);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        TextView createAcc = (TextView) findViewById(R.id.createaccount);

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerScreen = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(registerScreen);
            }
        });

        configureSkipAcc();

    }


    private void configureSkipAcc()
    {
        TextView skip = (TextView) findViewById(R.id.skip);
        skip.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LandingPage.class));
            }
        }));
    }

}
