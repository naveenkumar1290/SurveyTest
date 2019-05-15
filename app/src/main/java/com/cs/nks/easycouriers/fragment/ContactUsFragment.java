package com.cs.nks.easycouriers.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.util.UTIL;

import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {

    Context myContext;
    private int currentPage = 0;
    UTIL utill;
    private ViewPager mViewPager;
Timer timer;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        getActivity().setTitle("New Request");
        myContext = getActivity();


        return rootView;

    }


}
