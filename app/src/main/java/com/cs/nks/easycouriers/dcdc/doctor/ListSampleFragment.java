package com.cs.nks.easycouriers.dcdc.doctor;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.model.ClientUserAll;
import com.cs.nks.easycouriers.util.UTIL;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListSampleFragment extends Fragment {

    Context myContext;
    UTIL utill;
    Timer timer;
    TextView tv_msg;
    private int currentPage = 0;
    private ViewPager mViewPager;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    ArrayList<ClientUserAll> list_ClientUser = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        getActivity().setTitle("Schedule");
        myContext = getActivity();
        setView(rootView);

        return rootView;

    }

    private void setView(View rootView) {
        recyclerView = (RecyclerView)rootView. findViewById(R.id.recycler_view);

        tv_msg = rootView.findViewById(R.id.tv_msg);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        list_ClientUser.add(new ClientUserAll("1","21-12-2018","12:55","Monday","","","","","","",""));
        list_ClientUser.add(new ClientUserAll("1","22-12-2018","01:05","Tuesday","","","","","","",""));
        list_ClientUser.add(new ClientUserAll("1","23-12-2018","02:10","Wednesday","","","","","","",""));

        mAdapter = new MoviesAdapter(getActivity(), list_ClientUser);
        recyclerView.setAdapter(mAdapter);

    }
    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
        private List<ClientUserAll> moviesList;
        //   private HttpImageManager mHttpImageManager;

        public MoviesAdapter(Activity context, List<ClientUserAll> moviesList) {
            this.moviesList = moviesList;
            //     mHttpImageManager = ((AppController) context.getApplication()).getHttpImageManager();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_client_all_user, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            ClientUserAll projectPhoto = moviesList.get(position);
            holder.index_no.setText(String.valueOf(position + 1));


            holder.name.setText(projectPhoto.getTxt_Mail()); //date
            holder.user_type.setText(projectPhoto.getCompID());//time
            holder.master.setText(projectPhoto.getCompName());//day


/*
            if (Descr == null || Descr.trim().equals("")) {
                holder.tv_status.setText("Not available");
            } else {
                holder.tv_status.setText(Descr);
            }*/

            holder.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  Intent intent = new Intent(context, ClientFileDetailActivity.class);
                    // intent.putExtra("obj", projectPhoto);
                    intent.putExtra("FileId", FileId);
                    intent.putExtra("FileName", FileName);
                    intent.putExtra("jobID", jobID);
                    intent.putExtra("jobName", job);
                    startActivity(intent);*/
                }
            });
        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name, user_type, master, phone, email;
            ImageView imgvw_edit;
            Button index_no;
            LinearLayout parentView;

            public MyViewHolder(View convertview) {
                super(convertview);

                parentView =  convertview.findViewById(R.id.row_jobFile);



            index_no = (Button) convertview.findViewById(R.id.serial_no);

                name = (TextView) convertview.findViewById(R.id.name);
               user_type = (TextView) convertview.findViewById(R.id.user_type);
                master = (TextView) convertview.findViewById(R.id.master);
            /*  phone = (TextView) convertview.findViewById(R.id.phone);
               email = (TextView) convertview.findViewById(R.id.email);*/
            imgvw_edit = (ImageView) convertview.findViewById(R.id.imgvw_edit);


            }
        }
    }

}
