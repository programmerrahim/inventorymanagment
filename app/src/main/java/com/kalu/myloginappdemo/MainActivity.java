package com.kalu.myloginappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kalu.myloginappdemo.Model.Users;
import com.kalu.myloginappdemo.Prevalent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

   private EditText loginEmailEditText,loginPasswordEditText;
   private Button loginButton,loginSignUpButton;
    private TextView forgotText;
    private ProgressDialog loadingBar;
    private CheckBox chkBoxRememberMe;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mAuth = FirebaseAuth.getInstance();

        this.setTitle("Login Activity");

        loginEmailEditText = findViewById(R.id.login_email_editText_id);
        loginPasswordEditText = findViewById(R.id.login_password_editText_id);
        loginButton = findViewById(R.id.login_login_btn_id);
        loginSignUpButton = findViewById(R.id.login_signUp_btn_id);
        forgotText = findViewById(R.id.login_forgot_txt_id);

        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.checkBox_1Id);
        Paper.init(this);


        loginButton.setOnClickListener(this);
        loginSignUpButton.setOnClickListener(this);
        forgotText.setOnClickListener(this);


        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey !="" && UserPasswordKey !=""){
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)){
                AllowAccess(UserPhoneKey,UserPasswordKey);

                checkConnection();

                loadingBar.setTitle("Welcome back again!!!");
                loadingBar.setIcon(R.drawable.ic_wecome);
                loadingBar.setMessage("Please wait ...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login_login_btn_id:
                LoginUser();

            break;

            case R.id.login_signUp_btn_id:
                Intent registerintent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(registerintent);
                break;

            case R.id.login_forgot_txt_id:
                Intent intent = new Intent(getApplicationContext(),ResetActivity.class);
                startActivity(intent);
                break;

        }

    }
     //user login method starts
     private void LoginUser()
     {
         String phone = loginEmailEditText.getText().toString().trim();
         String password = loginPasswordEditText.getText().toString().trim();

         if (TextUtils.isEmpty(phone))
         {
             Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
         }
         else if (TextUtils.isEmpty(password))
         {
             Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
         }
         else
         {
             checkConnection();

             loadingBar.setTitle("Login Account");
             loadingBar.setMessage("Please wait, while we are checking the credentials.");
             loadingBar.setCanceledOnTouchOutside(false);
             loadingBar.show();


             AllowAccessToAccount(phone, password);
         }
     }



    private void AllowAccessToAccount(final String phone, final String password)
    {

        if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }



        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            if (parentDbName.equals("Admins"))
                            {
                                Toast.makeText(MainActivity.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, BeginActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("Users"))
                            {
                                Toast.makeText(MainActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(MainActivity.this, BeginActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //user login method ends

    private void AllowAccess(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists()){
                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone)){
                        if (usersData.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this,"Login is Successfull",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(MainActivity.this,BeginActivity.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Password is Incorrect!!!",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                }
                else {
                    Toast.makeText(MainActivity.this,"Account with this "+phone+" number do not exists",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to Exit?");
            builder.setNegativeButton("No",null);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                    finish();
                }
            }).show();

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Check Connection Start
    public void checkConnection(){

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (null!=activeNetwork){

            if (activeNetwork.getType()==ConnectivityManager.TYPE_WIFI){
                Toast.makeText(this,"Wifi Connected",Toast.LENGTH_SHORT).show();
            }
            if (activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(this,"Mobile Network Connected",Toast.LENGTH_SHORT).show();
            }


        }

        else{
            Intent intent = new Intent(MainActivity.this,CheckConnectionActivity.class);
            startActivity(intent);
        }
    }
    //Check Connection Ends
}
