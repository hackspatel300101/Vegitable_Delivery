package com.example.vegitable_delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vegitable_delivery.adapter.Vegetable_list;
import com.example.vegitable_delivery.model.Product_details;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Price_list extends AppCompatActivity {
    RecyclerView recyclerView;
    Vegetable_list adp;
    ProgressDialog pd;
    public String name,price,imageurl,weight,URL="https://test-ajay.000webhostapp.com/show_item.php";
    List<Product_details> list = new ArrayList<>();
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptrice_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("શાકભાજી નો ભાવ");
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        recyclerView=findViewById(R.id.price_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pd= new ProgressDialog(this);
        pd.setMessage("Please Wait.....");
        pd.show();
        getdata();

    }
    private void getdata() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                if(response!=null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("root");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            name = object.getString("name");
                            price = object.getString("price");
                            imageurl=object.getString("image_path");
                            weight=object.getString("kilogram");


                            // here create model object and setter the data into setter method
                            Product_details product_details = new Product_details(imageurl,name,price,weight);
                            list.add(product_details);


                        }
                        adp= new Vegetable_list(Price_list.this,list);
                        recyclerView.setAdapter(adp);
                        pd.dismiss();




                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Price_list.this, "exception"+e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }else
                {
                    Toast.makeText(Price_list.this, "null", Toast.LENGTH_LONG).show();

                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                Toast.makeText( Price_list.this,"Volley Response error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }

        });


        requestQueue.add(stringRequest);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        {
            Intent i = new Intent(Price_list.this,HomeActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
