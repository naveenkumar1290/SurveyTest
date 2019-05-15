package com.cs.nks.easycouriers.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MonthWiseFeedbackActivity extends AppCompatActivity {
    UTIL util;
    String appointment_id = "", branchId = "";
    EditText et_description;
 RadioButton its_okay,Good ,VeryGood ,Bad ,VeryBad;
 String rating="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_wise_feedback);
        util = new UTIL(MonthWiseFeedbackActivity.this);
        setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            appointment_id = b.getString("appointment_Id", "");
            branchId = b.getString("branch_id", "");
        }

        et_description = findViewById(R.id.description);

        its_okay= findViewById(R.id.its_okay_En);
        Good = findViewById(R.id.Good_En);
        VeryGood= findViewById(R.id.VeryGood_En);
        Bad = findViewById(R.id.Bad_En);
        VeryBad= findViewById(R.id.VeryBad_En);
        VeryGood.setChecked(true);
        its_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating="It's Ok";
                Good.setChecked(false);
                VeryGood.setChecked(false);
                Bad.setChecked(false);
                VeryBad.setChecked(false);
            }
        });
        Good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating="Good";

                its_okay.setChecked(false);
                VeryGood.setChecked(false);
                Bad.setChecked(false);
                VeryBad.setChecked(false);

            }
        });

        VeryGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating="Very Good";
                its_okay.setChecked(false);
                Good.setChecked(false);
                Bad.setChecked(false);
                VeryBad.setChecked(false);

            }
        });
        Bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating="Bad";

                its_okay.setChecked(false);
                Good.setChecked(false);
                VeryGood.setChecked(false);
                VeryBad.setChecked(false);
            }
        });
        VeryBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating="Very Bad";
                its_okay.setChecked(false);
                Good.setChecked(false);
                VeryGood.setChecked(false);
                Bad.setChecked(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.download) {
            if(! et_description.getText().toString().trim().equals("")){
                callSubmitFeedback();
            }else {
                et_description.setError("Please enter description!");
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    private void callSubmitFeedback() {
        if (new ConnectionDetector(MonthWiseFeedbackActivity.this).isConnectingToInternet()) {
            getFeedback();
        } else {
            Toast.makeText(MonthWiseFeedbackActivity.this, "No internet", Toast.LENGTH_SHORT).show();
        }
    }
    public void getFeedback(
    ) {

        String URL = UTIL.Domain_DCDC + UTIL.Add_feedback_API;
        util.showProgressDialog(UTIL.Progress_msg);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        util.hideProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("Response", response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("msg");
                            if (status.equals("1")) {
                                Toast.makeText(MonthWiseFeedbackActivity.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                               finish();

                            } else {
                                Toast.makeText(MonthWiseFeedbackActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        util.hideProgressDialog();
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String patientId = UTIL.getPref(MonthWiseFeedbackActivity.this, UTIL.Key_UserId);
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_id", patientId);
                params.put("appointment_id", appointment_id);
                params.put("branch_id", branchId);
                params.put("description", et_description.getText().toString().trim());
                params.put("rating", rating);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(MonthWiseFeedbackActivity.this);
        queue.add(postRequest);
    }

    /*public void getFeedback() {
        util.showProgressDialog(UTIL.Progress_msg);
        String URL = UTIL.Domain_DCDC + UTIL.Add_feedback_API;
        String tag_json_obj = "json_obj_req";
        Log.e("Brnch URL>>", URL);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Tag", response.toString());
                        util.hideProgressDialog();

                        try {
                            JSONArray jsonArray = response.getJSONArray("branch");


                        } catch (Exception e) {
                            e.getCause();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                util.hideProgressDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                String patientId = UTIL.getPref(FeedbackActivity.this, UTIL.Key_UserId);
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_id", patientId);
                params.put("appointment_id", appointment_id);
                params.put("branch_id", branchId);
                params.put("description", et_description.getText().toString().trim());
                params.put("rating", rating);

                return params;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(FeedbackActivity.this);
        queue.add(jsonObjReq);

    }*/
}
