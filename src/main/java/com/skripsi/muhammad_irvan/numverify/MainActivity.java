package com.skripsi.muhammad_irvan.numverify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.appcompat.*;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editNumber;
    Button btRequest;
    LinearLayout linearLayout1;
    ProgressBar progressBar1;
    TextView valid, number, local_format, international_format, country_code, country_name, location, carrier, line_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btRequest = (Button)findViewById(R.id.request);
        editNumber = (EditText)findViewById(R.id.phoneNumber);

        linearLayout1 = (LinearLayout)findViewById(R.id.layoutData);
        progressBar1 = (ProgressBar)findViewById(R.id.progress);

        linearLayout1.setVisibility(View.GONE);
        progressBar1.setVisibility(View.GONE);

        valid                   = (TextView)findViewById(R.id.text_valid);
        number                  = (TextView)findViewById(R.id.text_number);
        local_format            = (TextView)findViewById(R.id.text_localformat);
        international_format    = (TextView)findViewById(R.id.text_interformat);
        country_code            = (TextView)findViewById(R.id.text_counterycode);
        country_name            = (TextView)findViewById(R.id.text_counteryname);
        location                = (TextView)findViewById(R.id.text_location);
        carrier                 = (TextView)findViewById(R.id.text_carier);
        line_type               = (TextView)findViewById(R.id.text_linetype);


        btRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editNumber.getText().toString())){
                    String apikey = BuildConfig.numverify_api_key;
                    String url = "http://apilayer.net/api/validate?access_key=" + apikey +"&number=" + editNumber.getText().toString() +"&country_code=&format=1";

                    //String url2 = "http://apilayer.net/api/countries?access_key=e6c8925154b4341484b6d662ebfbc493";
                    progressBar1.setVisibility(View.VISIBLE);

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                linearLayout1.setVisibility(View.VISIBLE);
                                progressBar1.setVisibility(View.GONE);

                                String respone_valid        = response.getString("valid");
                                String respone_number       = response.getString("number");
                                String respone_local_format = response.getString("local_format");
                                String respone_international_format = response.getString("international_format");
                                String respone_country_code = response.getString("country_code");
                                String respone_country_name = response.getString("country_name");
                                String respone_location     = response.getString("location");
                                String respone_carrier      = response.getString("carrier");
                                String respone_line_type    = response.getString("line_type");

                                set(respone_valid, respone_number, respone_local_format, respone_international_format, respone_country_code, respone_country_name,
                                        respone_location, respone_carrier, respone_line_type);

                                /*
                                Iterator<String> iter = response.keys();
                                while (iter.hasNext()) {
                                    String key = iter.next();
                                    try {
                                        Object value = response.get(key);
                                        Log.d("Code", value.toString());
                                    } catch (JSONException e) {
                                        // Something went wrong!
                                    }
                                }
                                */

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            linearLayout1.setVisibility(View.GONE);
                            progressBar1.setVisibility(View.GONE);

                            if(error instanceof TimeoutError){
                                Toast.makeText(getApplicationContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                            }else if(error instanceof NoConnectionError){
                                Toast.makeText(getApplicationContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                            }else if(error instanceof AuthFailureError){
                                Toast.makeText(getApplicationContext(), "Auth Failure Error", Toast.LENGTH_SHORT).show();
                            }else if(error instanceof NetworkError){
                                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                            }else if(error instanceof ServerError){
                                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                            }else if(error instanceof ParseError){
                                Toast.makeText(getApplicationContext(), "JSON Parse Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })/*{

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("access_key", "e6c8925154b4341484b6d662ebfbc493");
                return headers;
            }
        }*/;

                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    requestQueue.add(request);
                }
            }
        });



    }

    public void set(String valid, String number, String local_format, String international_format, String country_code, String country_name, String location, String carrier, String line_type) {
        this.valid.setText(valid);
        this.number.setText(number);
        this.local_format.setText(local_format);
        this.international_format.setText(international_format);
        this.country_code.setText(country_code);
        this.country_name.setText(country_name);
        this.location.setText(location);
        this.carrier.setText(carrier);
        this.line_type.setText(line_type);
    }
}
