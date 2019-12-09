package com.cs.nks.easycouriers.dcdc.doctor;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorProfilefragment extends Fragment {

    Context myContext;

    FloatingActionButton fab;

    UTIL util;
    AlertDialog alertDialog;
    TextView txt_name, txt_mail,
            txt_phone,
            txt_address,
            txt_City,
            txt_zip;
    de.hdodenhof.circleimageview.CircleImageView profile_image;
    String branch_id,
            first_name,
            last_name,
            gender,
            contact_no,
            email,
            dob,
            profile_image_url,
            address,
            city,
            zip_code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_profile_main, container, false);
        getActivity().setTitle("Profile");
        myContext = getActivity();
        util = new UTIL(getActivity());
        setView(rootView);

        return rootView;

    }

    private void setView(View rootView) {

        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), UpdateDoctorProfileActivity.class);


                i.putExtra("branch_id", branch_id);
                i.putExtra("first_name", first_name);
                i.putExtra("last_name", last_name);
                i.putExtra("gender", gender);
                i.putExtra("contact_no", contact_no);
                i.putExtra("email", email);
                i.putExtra("dob", dob);
                i.putExtra("profile_image_url", profile_image_url);
                i.putExtra("address", address);
                i.putExtra("city", city);
                i.putExtra("zip_code", zip_code);

                startActivity(i);
            }
        });
        txt_name = rootView.findViewById(R.id.txt_name);
        txt_mail = rootView.findViewById(R.id.txt_mail);
        txt_phone = rootView.findViewById(R.id.txt_phone);
        txt_address = rootView.findViewById(R.id.txt_address);
        txt_City = rootView.findViewById(R.id.txt_City);
        txt_zip = rootView.findViewById(R.id.txt_zip);
        profile_image = rootView.findViewById(R.id.profile_image);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            getDoctor_Profile();
        } else {
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void getDoctor_Profile() {
        util.showProgressDialog(UTIL.Progress_msg);
        String dcotorId = UTIL.getPref(myContext, UTIL.Key_UserId);
        String url = UTIL.Domain_DCDC + UTIL.Profile_Doctor_API + "user_id=" + dcotorId;


        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        util.hideProgressDialog();


                        try {

                            JSONArray jsonArray1 = new JSONArray(response);
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                            String status = jsonObject1.getString("status");


                            if (status.equals("1")) {
                                JSONArray jsonArray = jsonObject1.getJSONArray("doctor_details");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);

                                branch_id = jsonObject.getString("branch_id");
                                first_name = jsonObject.getString("first_name");
                                last_name = jsonObject.getString("last_name");
                                gender = jsonObject.getString("gender");
                                contact_no = jsonObject.getString("contact_no");
                                email = jsonObject.getString("email");
                                dob = jsonObject.getString("dob");
                                profile_image_url = jsonObject.getString("profile_image");
                                address = jsonObject.getString("address");
                                city = jsonObject.getString("city");
                                zip_code = jsonObject.getString("zip_code");

                                String url_img = profile_image_url;
                                txt_name.setText(first_name + " " + last_name);
                                txt_mail.setText(email);
                                txt_phone.setText(contact_no);
                                txt_address.setText(address);
                                txt_City.setText(city);
                                txt_zip.setText(zip_code);

                              /*  Glide
                                        .with(getActivity())
                                        .load(url_img)     // Uri of the picture
                                        .into(profile_image);*/

                                Glide.with(getActivity()).
                                        load(url_img)
                                        .apply(new RequestOptions()
                                                .placeholder(R.drawable.ic_person_white_24dp)
                                        )
                                        .into(profile_image);

                            }


                        } catch (Exception e) {
                            e.getCause();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        util.hideProgressDialog();
                    }
                }
        );

        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);


    }


}
