package com.kalu.myloginappdemo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textViewname,textViewAddress,textViewPhone;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewname = itemView.findViewById(R.id.vendor_sample_nameId);
        textViewAddress = itemView.findViewById(R.id.vendor_sample_addressId);
        textViewPhone = itemView.findViewById(R.id.vendor_sample_phoneId);
    }
}
