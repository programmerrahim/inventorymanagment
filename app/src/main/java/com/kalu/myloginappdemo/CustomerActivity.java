package com.kalu.myloginappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.kalu.myloginappdemo.Model.Customers;
import com.kalu.myloginappdemo.Model.Vendors;
import com.kalu.myloginappdemo.Prevalent.Prevalent;

import java.util.HashMap;
import java.util.Map;

public class CustomerActivity extends AppCompatActivity {
    private FirebaseRecyclerOptions<Customers>customeroptions;
    private FirebaseRecyclerAdapter<Customers,CustomerMyViewHolder>customeradapter;
    private DatabaseReference customerref;

    private RecyclerView recyclerView;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        customerref = FirebaseDatabase.getInstance().getReference().child("customers");

        recyclerView = findViewById(R.id.customer_recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.customer_fabId);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this,CustomerDetailsActivity.class);
                startActivity(intent);
            }
        });

        customeroptions = new FirebaseRecyclerOptions.Builder<Customers>()
                .setQuery(customerref,Customers.class)
                .build();




        customeradapter = new FirebaseRecyclerAdapter<Customers, CustomerMyViewHolder>(customeroptions) {
            @Override
            protected void onBindViewHolder(@NonNull CustomerMyViewHolder holder, final int position, @NonNull Customers model) {
                holder.textViewname.setText("Customer name       : "+model.getCname());
                holder.textViewAddress.setText("Customer address : "+model.getCaddress());
                holder.textViewPhone.setText("Customer phone     : "+model.getCphone());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]{
                                "Add",
                                "Remove"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerActivity.this);
                        builder.setTitle("Options");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (i==0){
                                    Intent intent = new Intent(CustomerActivity.this,CustomerDetailsActivity.class);
                                    startActivity(intent);
                                }

                                if (i==1){
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("customers")
                                            .child(getRef(position).getKey())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(CustomerActivity.this,"Customer removed successfully.",Toast.LENGTH_LONG).show();
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
            public CustomerMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View cview = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_sample_layout,parent,false);
                return new CustomerMyViewHolder(cview);
            }
        };
        customeradapter.startListening();
        recyclerView.setAdapter(customeradapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_search_menu,menu);



        return super.onCreateOptionsMenu(menu);
    }
}
