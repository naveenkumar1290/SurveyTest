package com.cs.nks.easycouriers.survey;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    Context myContext;
    Button btnRegister;
    RadioGroup radioGroup;
    RadioButton radioButton, radioButton2;
    EditText fname, mobile, email, address, et_dob, blood_group, remarks, password, confirm_password;
    UTIL util;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle("New Request");
        myContext = getActivity();
        util = new UTIL(getActivity());
        et_dob = rootView.findViewById(R.id.et_dob);
        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click_getDate();
            }
        });

        setView(rootView);
        return rootView;


    }

    public void setView(View rootView) {
        radioGroup = rootView.findViewById(R.id.radioGroup);
        radioButton = rootView.findViewById(R.id.radioButton);
        radioButton2 = rootView.findViewById(R.id.radioButton2);
        fname = rootView.findViewById(R.id.fname);
        mobile = rootView.findViewById(R.id.mobile);
        email = rootView.findViewById(R.id.email);
        address = rootView.findViewById(R.id.address);
        et_dob = rootView.findViewById(R.id.et_dob);
        blood_group = rootView.findViewById(R.id.blood_group);
        remarks = rootView.findViewById(R.id.remarks);
        password = rootView.findViewById(R.id.password);
        confirm_password = rootView.findViewById(R.id.confirm_password);
        btnRegister = rootView.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name = fname.getText().toString().trim();
                String _gender = "M";
                if (radioButton.isChecked()) {
                    _gender = "Male";//M
                } else {
                    _gender = "Female";//F
                }
                String _mobile = mobile.getText().toString().trim();
                String _email = email.getText().toString().trim();
                String _address = address.getText().toString().trim();
                String _et_dob = et_dob.getText().toString().trim();
                String _blood_group = blood_group.getText().toString().trim();
                String _remarks = remarks.getText().toString().trim();
                String _password = password.getText().toString().trim();
                String _confirm_password = confirm_password.getText().toString().trim();

                if (_name.length() == 0) {
                    Toast.makeText(getActivity(), "Please enter name!", Toast.LENGTH_SHORT).show();
                } else if (_mobile.length() < 10) {
                    Toast.makeText(getActivity(), "Please enter 10 digit mobile no.!", Toast.LENGTH_SHORT).show();
                } else if (!UTIL.isValidEmail(_email)) {
                    Toast.makeText(getActivity(), "Please enter valid mail id!", Toast.LENGTH_SHORT).show();
                //} else if (_address.length() == 0) {
                 //   Toast.makeText(getActivity(), "Please enter address!", Toast.LENGTH_SHORT).show();
              //  } else if (_et_dob.length() == 0) {
               //     Toast.makeText(getActivity(), "Please enter date of birth!", Toast.LENGTH_SHORT).show();
               // } else if (_blood_group.length() == 0) {
               //     Toast.makeText(getActivity(), "Please enter blood group!", Toast.LENGTH_SHORT).show();
                } else if (_password.length() == 0) {
                    Toast.makeText(getActivity(), "Please enter password!", Toast.LENGTH_SHORT).show();
                } else if (_confirm_password.length() == 0) {
                    Toast.makeText(getActivity(), "Please enter confirm password!", Toast.LENGTH_SHORT).show();
                } else if (!_password.equals(_confirm_password)) {
                    Toast.makeText(getActivity(), "Password and confirm password didn't match!", Toast.LENGTH_SHORT).show();
                } else {
                    if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                      //  Api_Regisetration(_name, _gender, _mobile, _email, _address, _et_dob, _blood_group, _remarks, _password);
                        Toast.makeText(getActivity(), "Successfully Registered!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(myContext, ActivityWithNavigationMenuPatient.class));
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), UTIL.NoInternet, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = +dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        String dayOfMonthString = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        String monthOfYearString = monthOfYear < 10 ? "0" + monthOfYear : "" + monthOfYear;
        //2018-01-19%20
        // String  date_1 = year + "-" + monthOfYearString + "-" + dayOfMonthString;
        String date_1 = dayOfMonthString + "-" + monthOfYearString + "-" + year;

        et_dob.setText(date_1);

    }

    private void Click_getDate() {

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                RegisterFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(false);
        dpd.vibrate(false);
        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.setAccentColor(getResources().getColor(R.color.colorAccent));

        dpd.setTitle("Select DOB");
        dpd.setYearRange(1985, 2028);
      //  dpd.setMinDate(calendar);
        dpd.show(getActivity().getFragmentManager(), "dialog");


    }

    public void Api_Regisetration(final String name,
                                  final String gender,
                                  final String contact,
                                  final String mail,
                                  final String address,
                                  final String dob,
                                  final String blood_group,
                                  final String remarks,
                                  final String password) {

        String URL = UTIL.Domain_DCDC + UTIL.Registeration_API;
        util.showProgressDialog(UTIL.Progress_msg);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        // {"status":0,"msg":"Request method not accepted"}
                        util.hideProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("Response", response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("msg");
                            if (status.equals("1")) {
                                Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                                Intent i = new Intent(getActivity(), Tab_Login_Register_Activity.class);
                                startActivity(i);

                            } else {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("gender", gender);
                params.put("contact", contact);
                params.put("mail", mail);
                params.put("address", address);
                params.put("dob", parseDateToddMMyyyy(dob));
                params.put("blood_group", blood_group);
                params.put("remarks", remarks);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(postRequest);
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd-MM-yyyy";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
