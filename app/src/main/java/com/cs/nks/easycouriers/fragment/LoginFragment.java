package com.cs.nks.easycouriers.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.activity.ChangePassword;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import static com.google.android.gms.common.util.WorkSourceUtil.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
/* get release sh1 for gSign
 * https://stackoverflow.com/questions/34933380/sha1-key-for-debug-release-android-studio-mac
 * */
/*
 * SHA1: 51:96:13:9B:0F:FB:95:1D:C9:8B:B7:02:E1:B4:DD:D5:83:AE:FD:C9
 *
 * */

public class LoginFragment extends Fragment implements
        View.OnClickListener {//,
    //GoogleApiClient.OnConnectionFailedListener{


    static final int RequestPermissionCode = 100;
    // private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    Context myContext;
    UTIL utill;
    TextView tv_timing, forget_password, Change_password, tv_compName, tv_title_timing, tv_title_compName;
    Button btnLogin, btn_register;
    ImageView btn_g_sign_in, btn_fb_sign_in;
    EditText et_mobile, et_password;
    android.app.AlertDialog alertDialog;
    UTIL util;
    String mailId = "";
    // private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;
    // private Button btnSignOut, btnRevokeAccess;
    private ProgressDialog mProgressDialog;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        myContext = getActivity();
        utill = new UTIL(myContext);

        getActivity().setTitle("Login");


        et_mobile = (EditText) rootView.findViewById(R.id.email);
        et_password = (EditText) rootView.findViewById(R.id.password);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btn_g_sign_in = (ImageView) rootView.findViewById(R.id.btn_g_sign_in);
        btn_g_sign_in.setOnClickListener(this);

        btn_fb_sign_in = (ImageView) rootView.findViewById(R.id.btn_fb_sign_in);
        btn_fb_sign_in.setOnClickListener(this);
        forget_password = rootView.findViewById(R.id.forget_password);
        Change_password = rootView.findViewById(R.id.Change_password);
        Change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ChangePassword.class);
                startActivity(i);
            }
        });


        if (CheckingPermissionIsEnabledOrNot()) {
            //   Toast.makeText(HomeActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        } else {
            RequestMultiplePermission();
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_mobile = et_mobile.getText().toString().trim();
                String user_pwd = et_password.getText().toString().trim();
                if (user_mobile.isEmpty()) {
                    Toast.makeText(myContext,
                            "Please enter username!", Toast.LENGTH_SHORT)
                            .show();
                } else if (user_pwd.isEmpty()) {
                    Toast.makeText(myContext,
                            "Please enter password!", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    if (new ConnectionDetector(myContext).isConnectingToInternet()) {
                        LoginApiCall(user_mobile, user_pwd);
                        // startActivity(new Intent(myContext, ActivityWithNavigationMenu.class));


                    } else {
                        Toast.makeText(myContext,
                                UTIL.NoInternet, Toast.LENGTH_SHORT)
                                .show();
                    }

                }


            }

        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = et_mobile.getText().toString().trim();
                if (mobile.length() != 10) {
                    Toast.makeText(getActivity(), "Please enter registered mobile no.!", Toast.LENGTH_SHORT).show();
                } else {
                    dialog_mail(mobile);
                }

            }
        });
        return rootView;

    }

    private void dialog_mail(final String Mob) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog_mail, null);
        dialogBuilder.setView(dialogView);


        final TextView title = dialogView.findViewById(R.id.title);
        final EditText mail = dialogView.findViewById(R.id.mail);
        final Button positiveBtn = dialogView.findViewById(R.id.positiveBtn);
        final Button negativeBtn = dialogView.findViewById(R.id.negativeBtn);

        // dialogBuilder.setTitle("Device Details");
        title.setText(Mob);
        //   mail.setText("Are you sure?");

        positiveBtn.setText("Ok");
        negativeBtn.setText("Cancel");
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailId = mail.getText().toString().trim();
                alertDialog.dismiss();

                Api_ForgotPwd(Mob, mailId);

            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    public void Api_ForgotPwd(final String mobile,
                              final String mail
    ) {

        String URL = UTIL.Domain_DCDC + UTIL.Forgot_Pwd_API;
        utill.showProgressDialog(UTIL.Progress_msg);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        utill.hideProgressDialog();
                        alertDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("Response", response);
                            String status = jsonObject.getString("status");
                            if (status.equals("1")) {
                                Toast.makeText(getActivity(), "Password has been sent\nPlease check mail!", Toast.LENGTH_SHORT).show();
                                //  startActivity(new Intent(myContext,ChangePassword.class));
                            } else {
                                Toast.makeText(getActivity(), "Mobile no. is not registered!", Toast.LENGTH_SHORT).show();
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
                params.put("contact", mobile);
                params.put("mail_id", mail);


                return params;


            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(postRequest);

        postRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    void LoginApiCall(final String mobile, String password) {
        String tag_string_req = "req_login";
        utill.showProgressDialog("Logging in...");

        String URL_LOGIN = null;

        try {
            URL_LOGIN = UTIL.Domain_DCDC + UTIL.Login_API + "user_id=" + mobile + "&password=" + password;
            URL_LOGIN = URL_LOGIN.replaceAll(" ", "%20");
        } catch (Exception e) {
            e.printStackTrace();
        }


        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                utill.hideProgressDialog();
                //{"errormsg":"Login Correct","Status":"1","Key_UserId":"00000012"}
                //{"errormsg":"Wrong","Status":"2","Key_UserId":""}
                //{"status":1,"admin_id":null,"msg":"Success!!","type":1}

                try {
                    JSONObject jObj = new JSONObject(response);
                    String status = jObj.getString("status");
                    String msg = jObj.getString("msg");

                    String type = jObj.getString("type");

                    if (status.equals("1")) {
                        Toast.makeText(myContext,
                                "Logged-In successfully", Toast.LENGTH_SHORT).show();

                        //  UTIL.setPref(myContext, UTIL.Key_GENDER, Gender);
                        //    UTIL.setPref(myContext, UTIL.Key_USERNAME, UserName);
                        //    UTIL.setPref(myContext, UTIL.Key_USERNAME, UserName);
                        UTIL.setPref(myContext, UTIL.Key_Mobile, mobile);
                        UTIL.setPref(myContext, UTIL.KEY_isLogin, "true");
                        UTIL.setPref(myContext, UTIL.Key_Type, type);


                        if (type.equals("2")) { //Patient


                            String UserId = jObj.getString("user_id");
                            UTIL.setPref(myContext, UTIL.Key_UserId, UserId);


                        } else if (type.equals("1")) {  // Admin
                            String UserId = jObj.getString("admin_id");
                            UTIL.setPref(myContext, UTIL.Key_UserId, UserId);
                        }

                        UTIL.StartHomeActivity(getActivity());


                    } else if (status.equals("0")) {

                        Toast.makeText(myContext,
                                "Wrong mobile number or password", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(myContext,
                                "Some error occurred!", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(myContext, "Json error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(myContext,
                        "Volley Error!", Toast.LENGTH_SHORT).show();
                utill.hideProgressDialog();

            }
        }) {


        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    void gSign(View rootView) {
        btnSignIn = (SignInButton) rootView.findViewById(R.id.btn_sign_in);

        btnSignIn.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
        //       .enableAutoManage(getActivity(), this)
        //       .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        //      .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                UTIL.showToast(getActivity(), "Signed out");
            }
        });

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //  Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                break;
            case R.id.btn_g_sign_in:
                signIn();
                break;
            case R.id.btn_fb_sign_in:
                signOut();
                break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            Toast.makeText(getActivity(), account.getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            UTIL.showToast(getActivity(), "Sorry..G+ sign in not available!");
        }
    }

    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(getActivity(), new String[]
                {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,

                }, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean Location = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WriteExtrenalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (WriteExtrenalStorage && Location) {

                        //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return
                FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                        SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

}
