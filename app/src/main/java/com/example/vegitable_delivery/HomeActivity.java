package com.example.vegitable_delivery;

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
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mdrawerlayout;
    private NavigationView mNavigationView;
    ActionBarDrawerToggle toggle;
    CardView pricelist,oder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pricelist=findViewById(R.id.price_list_card);
        oder=findViewById(R.id.oder_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mdrawerlayout=findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,mdrawerlayout,R.string.open,R.string.close);
        mdrawerlayout.addDrawerListener(toggle);
        toggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mNavigationView=findViewById(R.id.navigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.price_list:
                        Intent intent= new Intent(HomeActivity.this,Price_list.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.oder_list:
                        Intent intent2= new Intent(HomeActivity.this,Oder_list.class);
                        startActivity(intent2);
                        finish();
                        return true;
                    case R.id.about:
                        Intent i_n= new Intent(HomeActivity.this,About_Activity.class);
                        startActivity(i_n);
                        finish();
                        return true;
                }
                mdrawerlayout.closeDrawer(GravityCompat.START);
                return true;

            }

        });
        pricelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,Price_list.class);
                startActivity(i);
                finish();
            }
        });
        oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,OderActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
