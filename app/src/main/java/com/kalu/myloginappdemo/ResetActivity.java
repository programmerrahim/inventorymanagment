package com.kalu.myloginappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResetActivity extends AppCompatActivity {

    private EditText edtPhone;
    private TextView edtRePhone;
    private Button requestButton;
    private Button requestLogin;
    private DatabaseReference resetRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        edtPhone = findViewById(R.id.reset_phone_editText1_id);
        edtRePhone = findViewById(R.id.txtId);
        requestButton = findViewById(R.id.request_new_password_ButtonId);
        requestLogin = findViewById(R.id.request_loginButtonId);

        resetRef = FirebaseDatabase.getInstance().getReference();


        requestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {

            String p = edtPhone.getText().toString().trim();
            @Override
            public void onClick(View v) {
                resetRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            String phone = dataSnapshot.child("Users").child(edtPhone.getText().toString()).child("password").getValue().toString();
                            Toast.makeText(ResetActivity.this, "Your Password : "+phone, Toast.LENGTH_LONG).show();
                            edtPhone.setText("");
                        }catch (Exception e){
                            Toast.makeText(ResetActivity.this,"Please Entered Registered Number", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ResetActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }


}
