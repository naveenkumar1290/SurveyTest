package com.cs.nks.easycouriers.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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

public class ChangePassword extends AppCompatActivity {
    Context myContext;
    UTIL utill;
    EditText et_old_password, et_password, et_cnfrm_new_password;
    Button btnChngePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myContext = ChangePassword.this;
        utill = new UTIL(myContext);
        setTitle("Change Password");
        et_old_password = (EditText) findViewById(R.id.old_password);
        et_password = (EditText) findViewById(R.id.password);
        et_cnfrm_new_password = (EditText) findViewById(R.id.cnfrm_new_password);

        btnChngePwd = findViewById(R.id.btnLogin);

        btnChngePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String old_pwd = et_old_password.getText().toString().trim();
                String pwd = et_password.getText().toString().trim();
                String cnfrm_pwd = et_cnfrm_new_password.getText().toString().trim();


                if (old_pwd.isEmpty()) {
                    Toast.makeText(myContext,
                            "Please enter old password!", Toast.LENGTH_SHORT)
                            .show();
                } else if (pwd.isEmpty()) {
                    Toast.makeText(myContext,
                            "Please enter new password!", Toast.LENGTH_SHORT)
                            .show();
                } else if (cnfrm_pwd.isEmpty()) {
                    Toast.makeText(myContext,
                            "Please confirm new password!", Toast.LENGTH_SHORT)
                            .show();
                } else if (!pwd.equals(cnfrm_pwd)) {
                    Toast.makeText(myContext,
                            "Password and confirm password do not match!", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    if (new ConnectionDetector(myContext).isConnectingToInternet()) {

                        Api_Change_Pwd(old_pwd, pwd);
                    } else {
                        Toast.makeText(myContext,
                                UTIL.NoInternet, Toast.LENGTH_SHORT)
                                .show();
                    }

                }


            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    public void Api_Change_Pwd(final String oldPwd,

                               final String newPwd
    ) {
        final String patientId = UTIL.getPref(myContext, UTIL.Key_UserId);

        String URL = UTIL.Domain_DCDC + UTIL.Update_Pwd_API;
        utill.showProgressDialog(UTIL.Progress_msg);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        utill.hideProgressDialog();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("Response", response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("msg");

                            if (status.equals("1")) {
                                Toast.makeText(myContext, "Password has been changed!", Toast.LENGTH_SHORT).show();
                                UTIL.clearPref(ChangePassword.this);
                                Intent i = new Intent(ChangePassword.this, Tab_Login_Register_Activity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(myContext, msg, Toast.LENGTH_SHORT).show();
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
                        utill.hideProgressDialog();
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_id", patientId);
                params.put("old_password", oldPwd);
                params.put("password", newPwd);

                return params;


            }
        };
        RequestQueue queue = Volley.newRequestQueue(myContext);
        queue.add(postRequest);

        postRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
