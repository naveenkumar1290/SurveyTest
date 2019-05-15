package com.cs.nks.easycouriers.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.activity.ActivityWithNavigationMenu;
import com.cs.nks.easycouriers.activity.MonthWiseFeedbackActivityNew2;
import com.cs.nks.easycouriers.util.UTIL;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class MyFirebaseMessagingService extends FirebaseMessagingService {



    @Override
    public void onMessageReceived(RemoteMessage message) {
        //sendMyNotification(message.getNotification().getBody());

        if (message.getData().size() > 0) {
            Log.e("data_payload==>", "" + message.getData());
           // user_id = message.getData().get("message");
            Map<String, String> data = message.getData();
            String title = data.get("title");
            String body = data.get("body");
            String msg = data.get("message");
            String badge = data.get("badge");
            sendMyNotification(title,body,msg);


        }

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
        UTIL.setPref(this, UTIL.Key_FCMTOken, s);

        String type = UTIL.getPref(MyFirebaseMessagingService.this, UTIL.Key_Type);
        String UserId = UTIL.getPref(MyFirebaseMessagingService.this, UTIL.Key_UserId);
        if (type.equals("2")) {//patient
            if(!UserId.equals("")){
                UpdateFCMTokenAtServer();
            }

        } else if (type.equals("1")) {// admin

        }

    }



    private void sendMyNotification(String title,String message,String type) {
        //On click of notification it redirect to this Activity
        Intent intent=null;
        if (type.equals("1")){ // push for appointment approve/reject/completed
            intent = new Intent(this, ActivityWithNavigationMenu.class);
        }
        else if(type.equals("2")){ //push for requesting monthly feedback
             intent = new Intent(this, MonthWiseFeedbackActivityNew2.class);
        }


     //   Intent intent = new Intent(this, ActivityWithNavigationMenu.class);
        intent.putExtra("push","1");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_192_192)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(UTIL.random_number_notificationId(), notificationBuilder.build());
    }

    public void UpdateFCMTokenAtServer(
    ) {
        String URL = UTIL.Domain_DCDC + UTIL.FCMTokenUpdate_API;
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            Log.d("Response", response);
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String UserId = UTIL.getPref(MyFirebaseMessagingService.this, UTIL.Key_UserId);
                String FCMToken = UTIL.getPref(MyFirebaseMessagingService.this, UTIL.Key_FCMTOken);
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", UserId);
                params.put("fcm_token_id", FCMToken);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(MyFirebaseMessagingService.this);
        queue.add(postRequest);
    }

}
