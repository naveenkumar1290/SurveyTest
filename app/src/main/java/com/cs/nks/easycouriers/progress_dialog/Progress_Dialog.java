package com.cs.nks.easycouriers.progress_dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class Progress_Dialog {
    Context context;
//    private android.app.ProgressDialog progressDialog;

    private static ProgressDialog progressDialog = null;

 /*   public Progress_Dialog(Context context) {
        this.context = context;
        if (progressDialog==null) {
            progressDialog = new android.app.ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
        }
    }*/

public static void show_dialog(Context context){

    try {
        if (progressDialog==null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
        }
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }catch (Exception e){
        Log.e("pDialog",e.getMessage());
    }
}
    public static void hide_dialog(){

        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }catch (Exception e){
            Log.e("pDialog",e.getMessage());
        }
    }
  /*  public void showDialog() {
        try {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }catch (Exception e){
            Log.e("pDialog",e.getMessage());
        }
    }

    public void hideDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            Log.e("pDialog",e.getMessage());
        }
    }
*/
}
