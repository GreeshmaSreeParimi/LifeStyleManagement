package com.example.lifestyle_management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GenerateNotification extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_notification);
        textView=findViewById(R.id.message);
        Bundle bundle=getIntent().getExtras();
        textView.setText(bundle.getString("message"));
    }
}