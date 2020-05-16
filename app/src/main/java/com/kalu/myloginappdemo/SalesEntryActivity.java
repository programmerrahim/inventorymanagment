package com.kalu.myloginappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kalu.myloginappdemo.Model.Products;
import com.kalu.myloginappdemo.Model.Sales;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SalesEntryActivity extends AppCompatActivity{
    //date picker TAG start
    private static final String TAG = "SalesEntryActivity";
    //date picker TAG start

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private EditText pnameEditText,vnameEditText,quantityEditText;
    private Button addSalesButton;

    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_entry);


        databaseReference = FirebaseDatabase.getInstance().getReference("sales");


        pnameEditText = findViewById(R.id.sales_edit_name_Textxt);
        vnameEditText = findViewById(R.id.sales_edit_vendor_name_Textxt);
        quantityEditText = findViewById(R.id.sales_edit_quantity_Textxt);
        addSalesButton = findViewById(R.id.sales_add_button_Id);


        addSalesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

            }
        });

        //Date picker Start
        mDisplayDate = findViewById(R.id.sales_edit_date_Textxt);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SalesEntryActivity.this,
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
        String sname = pnameEditText.getText().toString().trim();
        String svname = vnameEditText.getText().toString().trim();
        String squantity = quantityEditText.getText().toString();
        String sdate = mDisplayDate.getText().toString().trim();



        if (TextUtils.isEmpty(sname))
        {   pnameEditText.requestFocus();
            pnameEditText.setError("Please write product name...");
        }
        else if (TextUtils.isEmpty(svname))
        {
            vnameEditText.requestFocus();
            vnameEditText.setError("Please write vendor name...");
        }
        else if (TextUtils.isEmpty(squantity))
        {
            quantityEditText.requestFocus();
            quantityEditText.setError("Please write quantity...");
        }


         else if (TextUtils.isEmpty(sdate))
        {
            Toast.makeText(SalesEntryActivity.this,"Please select a date.",Toast.LENGTH_LONG).show();

        }
        else
        {

            String key = databaseReference.push().getKey();

            Sales sales = new Sales(sname,svname,squantity,sdate);

            databaseReference.child(key).setValue(sales);
            Toast.makeText(SalesEntryActivity.this,"Sales added successfully.",Toast.LENGTH_LONG).show();


        }



    }
    //save Data Method start




}
