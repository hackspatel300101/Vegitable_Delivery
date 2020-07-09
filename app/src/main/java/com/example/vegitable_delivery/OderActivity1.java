package com.example.vegitable_delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OderActivity1 extends AppCompatActivity {
   TextInputEditText number,name_address;
    DatabaseReference reference;
    private ArrayList<String> name,weight;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    Button submit;
    String URL="https://rich-ears.000webhostapp.com/getoder.php";
    RequestQueue requestQueue;
    String obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ખરીદી કરો");
        Intent i = getIntent();
        name= new ArrayList<String>();
        weight= new ArrayList<String>();
        arrayList= new ArrayList<String>();
        arrayList = (ArrayList<String>) i.getSerializableExtra("list");
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView=findViewById(R.id.submit_list);
        submit=findViewById(R.id.submit_btn);
        number=findViewById(R.id.phone);
        name_address=findViewById(R.id.name_address);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        GsonBuilder gsonBuilder =  new GsonBuilder();
        Gson gson = gsonBuilder.create();
         obj= gson.toJson(arrayList);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(OderActivity1.this)
                        .setTitle("તમારા ઓડર ની પુષ્ટિ ")
                        .setMessage(" તમે ખરેખર આ ઓર્ડર સબમિટ કરો છો?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               SubmitData();

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("No", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


    }
    public void SubmitData()
    {

        if(CheckEmpty()) {
            //  Toast.makeText(Add_itemActivity.this, "Entered value =" + name.getText().toString() + "pirce = " + price.getText().toString() + "weight =" + weight.getText().toString() + " " + img, Toast.LENGTH_LONG).show();
            final ProgressDialog progressDialog=new ProgressDialog(OderActivity1.this);
            progressDialog.setMessage("Loading... ");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getBoolean("key")) {
                            number.getText().clear();
                            name_address.getText().clear();
                            adapter.clear();

                            // here write image clear code

                            Toast.makeText(OderActivity1.this, "Succees", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(OderActivity1.this, "false", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(OderActivity1.this, "Exception =" + e.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(OderActivity1.this, "Error =" + error.getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                   params.put("number", number.getText().toString().trim());
                   params.put("name_add", name_address.getText().toString().trim());
                   params.put("oder",obj);

                    return params;

                }
            };
            requestQueue.add(stringRequest);
        }

    }



    public boolean CheckEmpty()
    {  // check the You enter number or anything else


        if(number.getText().toString().isEmpty())
        {
            number.setError("Invalid Input");
            return false;

        }else if(name_address.getText().toString().isEmpty())
        {name_address.setError("Invalid Input");
            return  false;
        }else
        {
            return true;
        }
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        {
            Intent i = new Intent(OderActivity1.this,OderActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
