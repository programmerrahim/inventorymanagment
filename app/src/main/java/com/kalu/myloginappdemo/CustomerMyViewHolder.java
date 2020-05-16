package com.kalu.myloginappdemo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CustomerMyViewHolder extends RecyclerView.ViewHolder {

    TextView textViewname,textViewAddress,textViewPhone;


    public CustomerMyViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewname = itemView.findViewById(R.id.customer_sample_nameId);
        textViewAddress = itemView.findViewById(R.id.customer_sample_addressId);
        textViewPhone = itemView.findViewById(R.id.customer_sample_phoneId);
    }
}
