package com.kalu.myloginappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.kalu.myloginappdemo.Adapter.ProductListCustomAdapter;

import io.paperdb.Paper;

public class BeginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private CardView purchase,sales,vendor,cutomer,product,stock;



   //Main Method starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        Paper.init(this);




        drawerLayout = findViewById(R.id.drawerId);
        navigationView = findViewById(R.id.navigationView_Id);
        navigationView.setNavigationItemSelectedListener(this);




        purchase = findViewById(R.id.purchaseViewId);
        sales = findViewById(R.id.salesViewId);
        vendor = findViewById(R.id.vendorViewId);
        cutomer = findViewById(R.id.customerViewId);
        product = findViewById(R.id.productViewId);
        stock = findViewById(R.id.stockViewId);


    purchase.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(BeginActivity.this,PurchaseEntryActivity.class);
            startActivity(intent);
        }
    });

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeginActivity.this,SalesEntryActivity.class);
                startActivity(intent);
            }
        });

        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeginActivity.this,VandorActivity.class);
                startActivity(intent);
            }
        });

        cutomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeginActivity.this,CustomerActivity.class);
                startActivity(intent);
            }
        });

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeginActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });

        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stockintent = new Intent(BeginActivity.this,StockActivity.class);
                startActivity(stockintent);
            }
        });




        //Navigation Drawer in main method starts

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Navigation Drawer in main method ends
    }
    //Main Method ends




    //Navigation Drawer in out method starts

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_purchase_Id :
               Intent intent = new Intent(BeginActivity.this,PurchaseEntryActivity.class);
               startActivity(intent);
                break;

            case R.id.menu_sales_Id :
                Intent sintent = new Intent(BeginActivity.this,SalesEntryActivity.class);
                startActivity(sintent);
                break;

            case R.id.menu_vendor_Id :
                Intent vintent = new Intent(BeginActivity.this,VandorActivity.class);
                startActivity(vintent);
                break;

            case R.id.menu_customer_Id :
                Intent cintent = new Intent(BeginActivity.this,CustomerActivity.class);
                startActivity(cintent);
                break;

            case R.id.menu_list_Id :
                Intent lintent = new Intent(BeginActivity.this,ProductActivity.class);
                startActivity(lintent);
                break;

            case R.id.menu_stock_Id :
                Intent stockintent = new Intent(BeginActivity.this,StockActivity.class);
                startActivity(stockintent);
                break;

            case R.id.menu_logOut_Id :
                Toast.makeText(BeginActivity.this,"Log out is Successfull!",Toast.LENGTH_LONG).show();
                Paper.book().destroy();
                Intent logintent = new Intent(BeginActivity.this,MainActivity.class);
                logintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logintent);
                finish();
                break;
        }
        return false;
    }
    //Navigation Drawer in out method ends
}
