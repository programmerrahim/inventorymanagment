package com.kalu.myloginappdemo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

class StockMyViewHolder extends ViewHolder {

    TextView txtStockProductName,txtStockProductQuantity;

    public StockMyViewHolder(@NonNull View itemView) {
        super(itemView);

        txtStockProductName = itemView.findViewById(R.id.stock_sample_productName_Id);
        txtStockProductQuantity = itemView.findViewById(R.id.stock_sample_quantity_Id);
    }
}
