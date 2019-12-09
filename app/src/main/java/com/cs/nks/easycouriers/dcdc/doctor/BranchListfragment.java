package com.cs.nks.easycouriers.dcdc.doctor;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.model.Branch;
import com.cs.nks.easycouriers.model.Patient;
import com.cs.nks.easycouriers.progress_dialog.Progress_Dialog;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class BranchListfragment extends Fragment {

    Context myContext;
    UTIL utill;

    TextView tv_msg;
    ArrayList<Branch> list_Branch = new ArrayList<>();
    UTIL util;
    AlertDialog alertDialog;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_branch_list, container, false);
        getActivity().setTitle("Branch List");
        myContext = getActivity();
        util = new UTIL(getActivity());
        setView(rootView);

        return rootView;

    }

    private void setView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        tv_msg = rootView.findViewById(R.id.tv_msg);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void onResume() {
        super.onResume();
        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            getBranchList();
        } else {
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void getBranchList() {
        util.showProgressDialog(UTIL.Progress_msg);
      //  Progress_Dialog.show_dialog(getActivity());

        String dcotorId = UTIL.getPref(myContext, UTIL.Key_UserId);
        String url = UTIL.Domain_DCDC + UTIL.BranchListByDoctor_API + "user_id=" + dcotorId;
        String tag_json_obj = "json_obj_req";
        list_Branch.clear();


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
                       // Progress_Dialog.hide_dialog();

                        try {

                            JSONArray jsonArray1 = new JSONArray(response);
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                            String status = jsonObject1.getString("status");





                            if (status.equals("1")) {
                                JSONArray jsonArray = jsonObject1.getJSONArray("branch_list");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String branch_id = jsonObject.getString("branch_id");
                                        String branch_name = jsonObject.getString("branch_name");
                                        String mobile_number = jsonObject.getString("mobile_number");
                                        String email = jsonObject.getString("email");
                                        String address = jsonObject.getString("address");

                                        String zip_code = jsonObject.getString("zip_code");
                                        list_Branch.add(new Branch(branch_id, branch_name, mobile_number
                                                , email, address, zip_code));

                                    }




                                }

                            }


                            mAdapter = new MoviesAdapter(getActivity(), list_Branch);
                            recyclerView.setAdapter(mAdapter);


                        } catch (Exception e) {
                            e.getCause();
                        }
                        if (list_Branch.size() < 1) {
                            tv_msg.setVisibility(View.VISIBLE);
                        } else {
                            tv_msg.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        util.hideProgressDialog();
                      //  Progress_Dialog.hide_dialog();
                    }
                }
        );

        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);


    }

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
        private List<Branch> moviesList;
        //   private HttpImageManager mHttpImageManager;

        public MoviesAdapter(Activity context, List<Branch> moviesList) {
            this.moviesList = moviesList;
            //     mHttpImageManager = ((AppController) context.getApplication()).getHttpImageManager();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_branch, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Branch projectPhoto = moviesList.get(position);
            holder.index_no.setText(String.valueOf(position + 1));

            holder.Name.setText(projectPhoto.getBranch_name());
            holder.mobile.setText(projectPhoto.getMobile_number());
          //  holder.email.setText(projectPhoto.get_mail());
            holder.address.setText(projectPhoto.getAddress());
            holder.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(myContext, PatientListActivity.class);
                    i.putExtra(UTIL.BRANCH_ID, projectPhoto.getBranch_id());
                    myContext.startActivity(i);
                }
            });


        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView Name, mobile, address;//, email;
            LinearLayout parentView;//, imgvw_delete, btn_Report,btn_feedback;
            TextView index_no;


            public MyViewHolder(View convertview) {
                super(convertview);

                parentView = convertview.findViewById(R.id.parentView);


                index_no = convertview.findViewById(R.id.serial_no);
                Name = (TextView) convertview.findViewById(R.id.Name);
                mobile = (TextView) convertview.findViewById(R.id.Mobile);
               // email = (TextView) convertview.findViewById(R.id.mail);
                address = (TextView) convertview.findViewById(R.id.address);


            }
        }
    }


}
