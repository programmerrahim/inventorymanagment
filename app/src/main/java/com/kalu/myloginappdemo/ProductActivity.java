package com.kalu.myloginappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kalu.myloginappdemo.Model.Products;

import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {



    private RecyclerView productRecyclerView;
    private FloatingActionButton fab;

    private DatabaseReference pRef;
    private FirebaseRecyclerOptions<Products>options;
    private FirebaseRecyclerAdapter<Products,ProductViewHolder>padapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productRecyclerView = findViewById(R.id.productRecyclerId);
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        pRef = FirebaseDatabase.getInstance().getReference().child("products");
        options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(pRef,Products.class)
                .build();


        fab = findViewById(R.id.product_fabId);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this,PurchaseEntryActivity.class);
                startActivity(intent);
                finish();
            }
        });



        padapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position, @NonNull Products model) {
                holder.textView1.setText("Product Name      :"+model.getPname());
                holder.textView2.setText("Vendor Name          :"+model.getVname());
                holder.textView3.setText("Product Quantity    :"+model.getPquantity());
                holder.textView4.setText("Purchase Date     :"+model.getPdate());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]{
                                "Add New",
                                "Remove",
                                "Edit",
                                "Cancel"
                        };
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
                        builder.setTitle("Options");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                if (i==0){
                                    Intent intent = new Intent(ProductActivity.this,PurchaseEntryActivity.class);
                                    startActivity(intent);
                                }
                                if (i==1){
                                   FirebaseDatabase.getInstance().getReference()
                                           .child("products")
                                           .child(getRef(position).getKey())
                                           .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                          if (task.isSuccessful()){
                                              Toast.makeText(ProductActivity.this,"Product removed successfully.",Toast.LENGTH_LONG).show();
                                          }
                                       }
                                   });
                                }
                                if (i==2){
                                    holder.edt1.setVisibility(View.VISIBLE);
                                    holder.edt2.setVisibility(View.VISIBLE);
                                    holder.btnUpdate.setVisibility(View.VISIBLE);
                                    holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            holder.edt1.setVisibility(View.INVISIBLE);
                                            holder.edt2.setVisibility(View.INVISIBLE);
                                            holder.btnUpdate.setVisibility(View.INVISIBLE);


                                            final String m = holder.edt1.getText().toString();
                                            final String n = holder.edt2.getText().toString();
                                            Map<String,Object>map=new HashMap<>();
                                            map.put("pname",m);
                                            map.put("pquantity",n);
                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("products")
                                                    .child(getRef(position).getKey())
                                                    .updateChildren(map)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                Toast.makeText(ProductActivity.this, "Update successfull", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    });

                                }
                                if (i==3){
                                    holder.edt1.setVisibility(View.INVISIBLE);
                                    holder.edt2.setVisibility(View.INVISIBLE);
                                    holder.btnUpdate.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

               View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_sample_layout,parent,false);

                return new  ProductViewHolder(v);
            }
        };
        padapter.startListening();
        productRecyclerView.setAdapter(padapter);




    }



}
