package com.kalu.myloginappdemo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kalu.myloginappdemo.Model.Products;
import com.kalu.myloginappdemo.R;

import java.util.List;

public class ProductListCustomAdapter extends ArrayAdapter<Products> {
     Activity contex;
    private List<Products>productsList;

    public ProductListCustomAdapter( Activity context, List<Products> productsList) {
        super(context, R.layout.product_list_sample_layout, productsList);
       this.contex = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = contex.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.product_list_sample_layout,null,true);

        Products products = productsList.get(position);

        TextView t1 = view.findViewById(R.id.sample_productName_Id);
        TextView t2 = view.findViewById(R.id.sample_vendorName_Id);
        TextView t3 = view.findViewById(R.id.sample_quantity_Id);
        TextView t4 = view.findViewById(R.id.sample_date_Id);

        t1.setText("Product Name : "+products.getPname());
        t2.setText("Vendor Name  : "+products.getVname());
        t3.setText("Quantity            : "+products.getPquantity());
        t4.setText("Date: "+products.getPdate());

        t1.setTextSize(26);
        t2.setTextSize(26);
        t3.setTextSize(26);
        t4.setTextSize(20);

        return view;
    }
}
