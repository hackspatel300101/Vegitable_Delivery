package com.example.vegitable_delivery;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.vegitable_delivery.adapter.Oder_list_adp;
import com.example.vegitable_delivery.model.Oders_details;
import java.util.ArrayList;
import java.util.List;

public class Oder_list extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Oders_details> list= new ArrayList<>();
    Oder_list_adp adpl;
    ProgressDialog pd;
    public String number,name_add,oder;
    RequestQueue requestQueue;
    String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("તમારી ખરીદી");
        recyclerView=findViewById(R.id.oder_list_rec);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pd= new ProgressDialog(this);
        pd.setMessage("Please Wait.....");
        pd.show();
        SharedPreferences sharedPreferences = getSharedPreferences("oder_data", Context.MODE_PRIVATE);
        number=sharedPreferences.getString("number",null);
        name_add=sharedPreferences.getString("name_add",null);
        oder=sharedPreferences.getString("oder",null);
        time=sharedPreferences.getString("time",null);
        Oders_details oders_details = new Oders_details(number,name_add,time,oder);
        list.add(oders_details);
        adpl= new Oder_list_adp(Oder_list.this,list);
        recyclerView.setAdapter(adpl);
        pd.dismiss();


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Oder_list.this,HomeActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        {
            Intent i = new Intent(Oder_list.this,HomeActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}