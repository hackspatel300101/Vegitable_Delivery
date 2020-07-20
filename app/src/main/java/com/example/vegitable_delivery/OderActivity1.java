package com.example.vegitable_delivery;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OderActivity1 extends AppCompatActivity {
   TextInputEditText number,name_address,oder;
    DatabaseReference reference;
    Button submit;
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAnDOtaWo:APA91bEOwsRuDWJDhl5AMpP40Voe9qwmyxvOnFsfTeq7DTn8_dRbKp_WLEL-O9xHwtyPws29fdHrGEfWSPrmg0jyqTS7KV0lA9dUn3BnroLJapp2BWvUwfBo7sn1iG0AurxL0YJHUMDt";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";
    RequestQueue requestQueue;
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    String URL="https://diyavegetable.000webhostapp.com/getoder.php";
    String time,u_number,name_add,veg_oder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("ખરીદી કરો");

        submit=findViewById(R.id.submit_btn);
        number=findViewById(R.id.phone);
        oder=findViewById(R.id.oder_ed);
        name_address=findViewById(R.id.name_address);
        for (int i= 0;i<OderActivity.adapter1.getCount();i++){
            oder.append(OderActivity.adapter1.getItem(i)+"\n");
        }
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
                                Date currentTime = Calendar.getInstance().getTime();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
                                 time = dateFormat.format(currentTime);
                                 u_number=number.getText().toString();
                                 name_add=name_address.getText().toString();
                                 veg_oder=oder.getText().toString();


                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        })
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
                            oder.getText().clear();
                            OderActivity.adapter1.clear();

                            // here write image clear code

                            SharedPreferences sharedPreferences = getSharedPreferences("oder_data", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("number",u_number);
                            editor.putString("name_add",name_add);
                            editor.putString("oder",veg_oder);
                            editor.putString("time",time);
                            editor.commit();
                            TOPIC = "/topics/Oders"; //topic must match with what the receiver subscribed to
                            NOTIFICATION_TITLE = "New Oder";
                            NOTIFICATION_MESSAGE =name_add;


                            JSONObject notification = new JSONObject();
                            JSONObject notifcationBody = new JSONObject();
                            try {
                                notifcationBody.put("title", NOTIFICATION_TITLE);
                                notifcationBody.put("body", NOTIFICATION_MESSAGE);

                                notification.put("to", TOPIC);
                                notification.put("notification", notifcationBody);
                                Log.d("body", " "+notifcationBody);
                            } catch (JSONException e) {
                                Log.e(TAG, "onCreate: " + e.getMessage() );
                            }
                            sendNotification(notification);
                            progressDialog.dismiss();
                            new AlertDialog.Builder(OderActivity1.this)
                                    .setTitle("આભાર")
                                    .setMessage(" તામરો  ઑડર ૨ કલાક પેહલા  તમારા ઘરે આવી  જશે.")

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                           dialog.dismiss();

                                            Intent i = new Intent(OderActivity1.this,HomeActivity.class);
                                            startActivity(i);
                                            finish();

                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.

                                    .setIcon(R.drawable.ic_thumb_up_black_24dp)
                                    .show();

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
                   params.put("oder",oder.getText().toString().trim());
                   params.put("time",time);

                    return params;

                }
            };
            requestQueue.add(stringRequest);
        }

    }

    private void sendNotification(JSONObject notification) {
        Log.d("function", "sendNotification: "+notification);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                        Toast.makeText(OderActivity1.this, "Success", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OderActivity1.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                Log.d("params", "getHeaders: "+params);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

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
    public void onBackPressed() {
        Intent i = new Intent(OderActivity1.this,OderActivity.class);
        startActivity(i);
        finish();
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
