package com.cs.nks.easycouriers.dcdc.patient;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.util.UTIL;

import java.util.List;
import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {

    Context myContext;
    UTIL utill;
    Timer timer;
    private int currentPage = 0;
    private ViewPager mViewPager;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.activity_cntct_us, container, false);
        getActivity().setTitle("Contact Us");
        myContext = getActivity();

     final    TextView phone=rootView.findViewById(R.id.phone);
        final   TextView mail=rootView.findViewById(R.id.mail);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone.getText().toString().trim()));
                startActivity(intent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                        Intent mailClient = new Intent(Intent.ACTION_VIEW);
//                        mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
//                        startActivity(mailClient);

                    String[] TO = {mail.getText().toString().trim()};
                    shareToGMail(TO, "", "");
                } catch (Exception e) {
                    e.getCause();
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    startActivity(emailIntent);
                }
            }
        });

        return rootView;

    }

    private void shareToGMail(String[] email, String subject, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        final PackageManager pm =getActivity(). getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        startActivity(emailIntent);
    }
}
