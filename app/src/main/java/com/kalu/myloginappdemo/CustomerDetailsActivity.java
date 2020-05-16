package com.kalu.myloginappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kalu.myloginappdemo.Model.Customers;
import com.kalu.myloginappdemo.Model.Vendors;

public class CustomerDetailsActivity extends AppCompatActivity {
    private EditText customerNameEditText,customerAddressEditText,customerPhoneEditText;
    private Button addCustomerButton;



    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        databaseReference = FirebaseDatabase.getInstance().getReference("customers");

        customerNameEditText = findViewById(R.id.customer_edit_customer_Textxt);
        customerAddressEditText = findViewById(R.id.customer_edit_customer_address_Textxt);
        customerPhoneEditText = findViewById(R.id.customer_edit_phone_Textxt);
        addCustomerButton = findViewById(R.id.customer_add_button_Id);

        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

            }
        });
    }

    //save Data Method start
    private void saveData() {
        String cname =  customerNameEditText.getText().toString().trim();
        String caddress = customerAddressEditText.getText().toString().trim();
        String cphone = customerPhoneEditText.getText().toString().trim();


        if (TextUtils.isEmpty(cname))
        {   customerNameEditText.requestFocus();
            customerNameEditText.setError("Please write customer name...");
        }
        else if (TextUtils.isEmpty(caddress))
        {
            customerAddressEditText.requestFocus();
            customerAddressEditText.setError("Please write customer address...");
        }
        else if (TextUtils.isEmpty(cphone))
        {
            customerPhoneEditText.requestFocus();
            customerPhoneEditText.setError("Please write customer phone number...");
        }

        else if (customerPhoneEditText.length()<11){
            customerPhoneEditText.requestFocus();
            customerPhoneEditText.setError("Please write at least 11 digit phone number...");
        }

        else
        {

            String key = databaseReference.push().getKey();

            Customers customers = new Customers(cname,caddress,cphone);

            databaseReference.child(key).setValue(customers);
            Toast.makeText(CustomerDetailsActivity.this,"Customer added successfully.",Toast.LENGTH_LONG).show();

            customerNameEditText.setText("");
            customerAddressEditText.setText("");
            customerPhoneEditText.setText("");

            Intent intent = new Intent(CustomerDetailsActivity.this,CustomerActivity.class);
            startActivity(intent);


        }



    }
    //save Data Method Ends
}
