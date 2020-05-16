package com.kalu.myloginappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckConnectionActivity extends AppCompatActivity {
    Button reloadbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_connection);


        reloadbutton = findViewById(R.id.reloadId);


        reloadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckConnectionActivity.this,MainActivity.class);
                startActivity(intent );
            }
        });
    }
}
