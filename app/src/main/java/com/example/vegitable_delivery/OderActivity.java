package com.example.vegitable_delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OderActivity extends AppCompatActivity {
    ListView listView;
    String URL="https://test-ajay.000webhostapp.com/show_item.php";
    RequestQueue requestQueue;
    JSONArray result;
    private ArrayList<String> name,weight;
    MaterialSpinner vegname,vegweight;
    String veg_name,veg_weight;
    Button add,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oderactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ખરીદી કરો");
        vegname=findViewById(R.id.name_spinner);
        vegweight=findViewById(R.id.weight_spinner);
        next=findViewById(R.id.btn_next);
        add=findViewById(R.id.add);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        name= new ArrayList<String>();
        weight= new ArrayList<String>();
        getdata();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veg_name=vegname.getText().toString();
                veg_weight=vegweight.getText().toString();




            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OderActivity.this,OderActivity1.class);
                startActivity(i);
                finish();
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
