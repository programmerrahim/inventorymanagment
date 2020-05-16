package com.kalu.myloginappdemo;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ProductViewHolder extends RecyclerView.ViewHolder {

    TextView textView1,textView2,textView3,textView4;
    EditText edt1,edt2;
    Button btnUpdate;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        textView1=itemView.findViewById(R.id.sample_productName_Id);
        textView2=itemView.findViewById(R.id.sample_vendorName_Id);
        textView3=itemView.findViewById(R.id.sample_quantity_Id);
        textView4=itemView.findViewById(R.id.sample_date_Id);

        edt1 = itemView.findViewById(R.id.edtrahim);
        edt2 = itemView.findViewById(R.id.edtrahim1);
        btnUpdate = itemView.findViewById(R.id.updaterahim);



    }
}
