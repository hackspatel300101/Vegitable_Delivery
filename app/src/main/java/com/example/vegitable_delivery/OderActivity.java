package com.example.vegitable_delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vegitable_delivery.adapter.Vegetable_list;
import com.example.vegitable_delivery.model.Product_details;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OderActivity extends AppCompatActivity {
   TextInputEditText oder_btn;
    DatabaseReference reference;
   String URL="https://test-ajay.000webhostapp.com/show_item.php";
    RequestQueue requestQueue;
    JSONArray result;
    private ArrayList<String> name,weight;
    MaterialSpinner vegname,vegweight;
    String veg_name,veg_weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ખરીદી કરો");
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        name= new ArrayList<String>();
        weight= new ArrayList<String>();
        oder_btn=findViewById(R.id.oder_btn);
        oder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(OderActivity.this);
                dialog.setContentView(R.layout.custom_dailog);
                dialog.setTitle("Select Oder");
                Button ok_btn= dialog.findViewById(R.id.ok_btn);
                Button cancel_btn=dialog.findViewById(R.id.cancel_btn);
                vegname=dialog.findViewById(R.id.name_spinner);
                vegweight=dialog.findViewById(R.id.weight_spinner);
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        veg_name = vegname.getText().toString();
                        veg_weight= vegweight.getText().toString();
                        oder_btn.setText(veg_name+veg_weight);

                    }
                });

                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                getdata();



            }
        });


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

                            name.add(object.getString("name"));
                            weight.add(object.getString("kilogram"));

                            // here create model object and setter the data into setter method



                        }

                        vegname.setAdapter(new ArrayAdapter<String>(OderActivity.this, android.R.layout.simple_spinner_dropdown_item, name));
                        vegweight.setAdapter(new ArrayAdapter<String>(OderActivity.this,android.R.layout.simple_spinner_dropdown_item,weight));



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(OderActivity.this, "exception"+e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }else
                {
                    Toast.makeText(OderActivity.this, "null", Toast.LENGTH_LONG).show();

                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                Toast.makeText( OderActivity.this,"Volley Response error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }

        });


        requestQueue.add(stringRequest);
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        {
            Intent i = new Intent(OderActivity.this,HomeActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
