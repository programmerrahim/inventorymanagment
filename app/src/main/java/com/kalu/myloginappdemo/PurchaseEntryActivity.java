package com.kalu.myloginappdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kalu.myloginappdemo.Model.Products;

import java.util.Calendar;

public class PurchaseEntryActivity extends AppCompatActivity {
   //date picker TAG start
    private static final String TAG = "PurchaseEntryActivity";
    //date picker TAG start

    private ProgressDialog loadingBar;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private EditText pnameEditText,vnameEditText,quantityEditText;
    private Button addProductsButton;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_entry);

        databaseReference = FirebaseDatabase.getInstance().getReference("products");



        pnameEditText = findViewById(R.id.purchase_edit_name_Textxt);
        vnameEditText = findViewById(R.id.purchase_edit_vendor_name_Textxt);
        quantityEditText = findViewById(R.id.purchase_edit_quantity_Textxt);
        addProductsButton = findViewById(R.id.purchase_add_button_Id);

        loadingBar = new ProgressDialog(this);

        addProductsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

            }
        });



        //Date picker Start
        mDisplayDate = findViewById(R.id.purchase_edit_date_Textxt);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PurchaseEntryActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getColor(R.color.fui_transparent)));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                Log.d(TAG, "onDateSet: "+month+"/"+dayOfMonth+"/"+year);

                String date = month+"/"+dayOfMonth+"/"+year;
                mDisplayDate.setText(date);
            }
        };
        //Date picker Ends
    }
    //save Data Method start
    private void saveData() {
        String pname = pnameEditText.getText().toString().trim();
        String vname = vnameEditText.getText().toString().trim();
        String pquantity = quantityEditText.getText().toString().trim();
        String pdate = mDisplayDate.getText().toString().trim();

        if (TextUtils.isEmpty(pname))
        {   pnameEditText.requestFocus();
            pnameEditText.setError("Please write product name...");
        }
        else if (TextUtils.isEmpty(vname))
        {
            vnameEditText.requestFocus();
            vnameEditText.setError("Please write vendor name...");
        }
        else if (TextUtils.isEmpty(pquantity))
        {
            quantityEditText.requestFocus();
            quantityEditText.setError("Please write vendor name...");
        }
        else if (TextUtils.isEmpty(pdate))
        {
            Toast.makeText(PurchaseEntryActivity.this,"Please select a date.",Toast.LENGTH_LONG).show();

        }
        else
        {

            String key = databaseReference.push().getKey();

            Products products = new Products(pname,vname,pquantity,pdate);

            databaseReference.child(key).setValue(products);
            loadingBar.dismiss();
            Toast.makeText(PurchaseEntryActivity.this,"Product added successfully.",Toast.LENGTH_LONG).show();

            pnameEditText.setText("");
            vnameEditText.setText("");
            quantityEditText.setText("");
            mDisplayDate.setText("");

            Intent intent = new Intent(PurchaseEntryActivity.this, ProductActivity.class);
            startActivity(intent);

        }



    }
    //save Data Method start
}
