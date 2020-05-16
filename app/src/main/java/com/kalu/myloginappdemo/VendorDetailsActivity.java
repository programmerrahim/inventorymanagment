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
import com.kalu.myloginappdemo.Model.Sales;
import com.kalu.myloginappdemo.Model.Vendors;

public class VendorDetailsActivity extends AppCompatActivity {

    private EditText vendorNameEditText,vendorAddressEditText,vendorPhoneEditText;
    private Button addVendorButton;



    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_details);

        databaseReference = FirebaseDatabase.getInstance().getReference("vendors");

        vendorNameEditText = findViewById(R.id.vendor_edit_name_Textxt);
        vendorAddressEditText = findViewById(R.id.vendor_edit_vendor_address_Textxt);
        vendorPhoneEditText = findViewById(R.id.vendor_edit_phone_Textxt);
        addVendorButton = findViewById(R.id.vendor_add_button_Id);

        addVendorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

            }
        });
    }

    //save Data Method start
    private void saveData() {
        String vname =  vendorNameEditText.getText().toString().trim();
        String vaddress = vendorAddressEditText.getText().toString().trim();
        String vphone = vendorPhoneEditText.getText().toString().trim();


        if (TextUtils.isEmpty(vname))
        {   vendorNameEditText.requestFocus();
            vendorNameEditText.setError("Please write vendor name...");
        }
        else if (TextUtils.isEmpty(vaddress))
        {
            vendorAddressEditText.requestFocus();
            vendorAddressEditText.setError("Please write vendor address...");
        }
        else if (TextUtils.isEmpty(vphone))
        {
            vendorPhoneEditText.requestFocus();
            vendorPhoneEditText.setError("Please write vendor phone number...");
        }
        else if (vendorPhoneEditText.length()<11){
            vendorPhoneEditText.requestFocus();
            vendorPhoneEditText.setError("Please write at least 11 digit phone number...");
        }

        else
        {

            String key = databaseReference.push().getKey();

            Vendors vendors = new Vendors(vname,vaddress,vphone);

            databaseReference.child(key).setValue(vendors);
            Toast.makeText(VendorDetailsActivity.this,"Vendor added successfully.",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(VendorDetailsActivity.this,VandorActivity.class);
            startActivity(intent);

            vendorNameEditText.setText("");
            vendorAddressEditText.setText("");
            vendorPhoneEditText.setText("");


        }



    }
    //save Data Method start
}
