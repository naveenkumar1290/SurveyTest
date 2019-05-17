package com.cs.nks.easycouriers.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.activity.ActivityWithNavigationMenu;
import com.cs.nks.easycouriers.activity.ActivityWithNavigationMenuAdmin;

import java.util.Random;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

/**
 * Created by Admin on 7/25/2017.
 */

public class UTIL {
    public static final String HEADER = "http://";
    public static final String URL_DEV = HEADER + "staging.icanstay.com/";


    public static final String KEY_USERID = "USERID";
    public static final String KEY_Email = "Email";
    public static final String KEY_Mobile = "Mobile";
    public static final String KEY_UseType = "UseType";
    public static final String KEY_CCId = "CCId";

    public static final String KEY_FirstName = "FirstName";
    public static final String KEY_LastName = "LastName";
    public static final String KEY_Company_ID = "Company_ID";

    public static final String KEY_PendingFromOperationContracting = "PendingFromOperationContracting_CC";
    public static final String KEY_PendingFromCallCenter_CC = "PendingFromCallCenter_CC";
    public static final String KEY_LeadExpireBooking_CC = "LeadExpireBooking_CC";
    public static final String KEY_LeadCloseBooking_CC = "LeadCloseBooking_CC";

    public static final String KEY_Update_BookingRequestRM = "BookingRequestRM";
    public static final String NetworkError = "Please check your internet connection!";

    public static String NoInternet = "Kindly check your internet connection!";
    public static final String KEY_PendingFromCallCenter_CS = "PendingFromCallCenter_CS";
    public static final String KEY_LeadExpireBooking_CS = "LeadExpireBooking_CS";
    public static final String KEY_LeadCloseBooking_CS = "LeadCloseBooking_CS";
    public static String Key_FCMTOken = "FCMTOken";
/*new*/
public static final String KEY_isLogin = "isLogin";
    public static String Key_GENDER = "gender";//string UserId
    public static String Key_RoleId_Admin = "1";
    public static String Key_RoleId_Sub_admin = "2";
    public static String Key_RoleId_Manager = "3";
    public static String Key_RoleId_General = "4";
    public static String Key_Mobile = "Mobile";
    public static String Key_Type = "Mobile";
    public static String Key_DeviceId = "DeviceId";
    public static String Key_Scheduler_Num = "Scheduler_Num";
    public static String Key_Pin_Num = "Pin_Num";
    public static String Key_Scheduler_Time = "Scheduler_Time";
    public static String Key_Scheduler_SavedAt = "SavedAt";

    public static String Key_UserId = "Key_UserId";
    public static String Key_USERNAME = "USERNAME";
    public static String Key_Schedule = "Schedule";
    public static String LAT = "lat";
    public static String LNG = "lng";
    public static String BRANCH_ID = "branch_id";
    public static String BRANCH_NAME = "branch_name";
    public static String FROM_MAP = "from_map";
    public static String Progress_msg = "Please wait..";

    static String Domain_Arduino_Live = "http://dcdckidneycare.com/staging/";
    static String Domain_DCDC_Dev = "http://dcdc.businesstowork.com/";

    public static String Domain_DCDC = Domain_DCDC_Dev;
    private Context myContext;
    private ProgressDialog progressDialog;





    //public static String Login_API = "/api/Arduino/Login?";//string mobileno, string pwd

    public static String Registeration_API = "dcdc_web_service/patient_registrations.php";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String AppointmentReg_API = "dcdc_web_service/patient_appointment_req.php";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String BranchList_API = "dcdc_web_service/get_branch_list.php?";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8

    public static String Login_API = "dcdc_web_service/patient_login.php?";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String ScheduleList_API = "dcdc_web_service/get_schedule_list.php?";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8

    public static String UpdateSchedule_API = "dcdc_web_service/update_appointment_req.php";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String DeleteSchedule_API = "dcdc_web_service/delete_appointment_req.php";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String Forgot_Pwd_API = "dcdc_web_service/forgot_password_mail.php";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String Add_feedback_API = "dcdc_web_service/add_feedback.php";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String Add_monthly_feedback_API = "dcdc_web_service/feedback_form.php";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String Admin_dash_API = "dcdc_web_service/dashboard_view.php";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String FCMTokenUpdate_API = "dcdc_web_service/add_fcm_token.php";
    public static String Update_Pwd_API = "dcdc_web_service/change_password.php";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String OPD_Report_API = "dcdc_web_service/opd_report.php";//stri

    public static String LAB_Report_API = "dcdc_web_service/lab_report.php";//stri

    public static String DIALYSIS_Report_API = "dcdc_web_service/dialysis_report.php";

    public UTIL(Context context) {
        myContext = context;
    }
    public static void setPref(Context context, String key, String value) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public static String getPref(Context context, String key) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(key, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String isLogin(Context context) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(KEY_isLogin, "false");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    public static boolean clearPref(Context context) {
        boolean result = false;
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            result = preferences.edit().clear().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public static void StartHomeActivity(Activity activity){
        String type = UTIL.getPref(activity, UTIL.Key_Type);
        if (type.equals("2")) { //patient
            activity.  startActivity(new Intent(activity, ActivityWithNavigationMenu.class));
            activity. finish();
        } else if (type.equals("1")) {  // admin
            activity.  startActivity(new Intent(activity, ActivityWithNavigationMenuAdmin.class));
            activity.  finish();
        }
    }
    public static void showToast(Context context,String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static int random_number_notificationId() {
        Random r = new Random();
        int i1 = r.nextInt(500000 - 10) + 10;
        return i1;
    }
    public void showProgressDialog(String message) {
        if (progressDialog == null || (!progressDialog.isShowing())) {
            progressDialog = ProgressDialog.show(myContext, null, null, true);
            progressDialog.setContentView(R.layout.elemento_progress);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            final TextView tv = progressDialog.getWindow().findViewById(R.id.textView6);
            tv.setText(message);
            progressDialog.setCancelable(false);
            if (!progressDialog.isShowing())
                progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public static SpannableString getTitle(String title){
        SpannableString ss1=  new SpannableString(title);
        ss1.setSpan(new AbsoluteSizeSpan(40), 0, title.length(), SPAN_INCLUSIVE_INCLUSIVE);
        return ss1;
    }

}
