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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kalu.myloginappdemo.Model.Vendors;
import com.kalu.myloginappdemo.Prevalent.Prevalent;

import java.util.HashMap;
import java.util.Map;

public class VandorActivity extends AppCompatActivity {
    private FirebaseRecyclerOptions<Vendors>options;
    private FirebaseRecyclerAdapter<Vendors,MyViewHolder>adapter;
    private DatabaseReference ref;

    private RecyclerView recyclerView;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vandor);

        ref = FirebaseDatabase.getInstance().getReference().child("vendors");

        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.vendor_fabId);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VandorActivity.this,VendorDetailsActivity.class);
                startActivity(intent);
            }
        });

        options = new FirebaseRecyclerOptions.Builder<Vendors>()
                .setQuery(ref,Vendors.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Vendors, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull final Vendors model) {
                holder.textViewname.setText("Vendor name       :"+model.getVname());
                holder.textViewAddress.setText("Vendor address :"+model.getVaddress());
                holder.textViewPhone.setText("Vendor phone     :"+model.getVphone());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]{
                                "Add",
                                "Remove"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(VandorActivity.this);
                        builder.setTitle("Options");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (i==0){
                                    Intent intent = new Intent(VandorActivity.this,VendorDetailsActivity.class);
                                    startActivity(intent);
                                }
                                if (i==1){

                                    FirebaseDatabase.getInstance().getReference()
                                            .child("vendors")
                                            .child(getRef(position).getKey())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                               if (task.isSuccessful()){
                                                   Toast.makeText(VandorActivity.this,"Vendor removed successfully.",Toast.LENGTH_LONG).show();


                                               }
                                        }
                                    });

                                }

                            }
                        });
                        builder.show();
                    }
                });



            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendo_sample_layout,parent,false);
                return new MyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }


}
