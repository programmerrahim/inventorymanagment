package com.kalu.myloginappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kalu.myloginappdemo.Model.Stocks;

public class StockActivity extends AppCompatActivity {
    private RecyclerView stockRecyclerView;
    private FirebaseRecyclerOptions <Stocks>stocOptions;
    private FirebaseRecyclerAdapter<Stocks,StockMyViewHolder>stockAdapter;
    private DatabaseReference stockRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        stockRecyclerView = findViewById(R.id.stock_recyclerViewId);
        stockRecyclerView.setHasFixedSize(true);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        stockRef = FirebaseDatabase.getInstance().getReference().child("products");

        stocOptions = new FirebaseRecyclerOptions.Builder<Stocks>()
                .setQuery(stockRef,Stocks.class)
                .build();

        stockAdapter = new FirebaseRecyclerAdapter<Stocks, StockMyViewHolder>(stocOptions) {
            @Override
            protected void onBindViewHolder(@NonNull StockMyViewHolder holder, int position, @NonNull Stocks model) {
                holder.txtStockProductName.setText("Product Name : "+model.getPname());
                holder.txtStockProductQuantity.setText("Product Quantity : "+model.getPquantity());

            }

            @NonNull
            @Override
            public StockMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_sample_layout,parent,false);
                return new StockMyViewHolder(view);
            }
        };
        stockAdapter.startListening();
        stockRecyclerView.setAdapter(stockAdapter);

    }
}
