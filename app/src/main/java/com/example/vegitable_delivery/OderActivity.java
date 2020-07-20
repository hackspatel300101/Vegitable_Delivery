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
    String URL="https://diyavegetable.000webhostapp.com/vegetable_show.php";
    RequestQueue requestQueue;
    JSONArray result;
    private ArrayList<String> name;
    MaterialSpinner vegname,vegweight;
    String veg_name,veg_weight;
    Button add,next,remove;
    String item;
    ArrayList<String> list;
   public static ArrayAdapter<String> adapter1;
    String arr[]={null,null,null} ;
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
        listView=findViewById(R.id.oder_listview);
        remove=findViewById(R.id.remove_btn);
        list= new ArrayList<String>();
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        name= new ArrayList<String>();
        getdata();
        String str[] = getResources().getStringArray(R.array.weight);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, str);
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        vegweight.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                veg_name=vegname.getText().toString();
                veg_weight=vegweight.getText().toString();
                String str = "   "+veg_name+"    "+veg_weight;
                list.add(str);
                listView.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();





            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter1.clear();
                adapter1.notifyDataSetChanged();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter1.getCount()==0){
                        Toast.makeText(OderActivity.this, "શાકભાજી પસંદ કરવા  માટે વિનંતી ", Toast.LENGTH_SHORT).show();

                    }else {

                        Intent i = new Intent(OderActivity.this,OderActivity1.class);
                        startActivity(i);
                        finish();
                    }

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


                            // here create model object and setter the data into setter method



                        }

                        vegname.setAdapter(new ArrayAdapter<String>(OderActivity.this, android.R.layout.simple_spinner_dropdown_item, name));



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
    public void onBackPressed() {
        Intent i = new Intent(OderActivity.this,HomeActivity.class);
        startActivity(i);
        finish();
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
