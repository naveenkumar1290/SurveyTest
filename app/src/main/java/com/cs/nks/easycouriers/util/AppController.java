package com.cs.nks.easycouriers.util;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;




public class AppController extends Application {
public static final String TAG=AppController.class.getSimpleName();
private RequestQueue mRequestQueue;
private ImageLoader mImageLoader;

public static AppController mInstance;
private static Context mcontext;
@Override
public void onCreate() {
	// TODO Auto-generated method stub
	super.onCreate();
	mInstance=this;
	mcontext=getApplicationContext();
//	final Fabric fabric = new Fabric.Builder(this)
//			.kits(new Crashlytics())
//			.debuggable(true)           // Enables Crashlytics debugger
//			.build();
//	Fabric.with(fabric);

	/****************this will report only release issues************/
/*
	Fabric.with(this, new Crashlytics.Builder().
			core(new CrashlyticsCore.Builder().
					disabled(BuildConfig.DEBUG).
					build()).build());*/

} 

public static synchronized AppController getInstance(){
 return mInstance;	
}
public RequestQueue getRequestQue(){
	if(mRequestQueue==null){
		mRequestQueue= Volley.newRequestQueue(getApplicationContext());
	}
	return mRequestQueue;
	
}


public <T> void addToRequestQueue(com.android.volley.Request<T> req,String tag){
	req.setTag(TextUtils.isEmpty(tag) ? TAG :tag);
	getRequestQue().add(req);
	
}
public <T> void addToRequestQueue(com.android.volley.Request<T> req){
	req.setTag(TAG);
	getRequestQue().add(req);
	
}
public void cancelPendingRequests(Object tag){
	if(mRequestQueue!=null){
		mRequestQueue.cancelAll(tag);
	}
}
public static Context getContext() {
    return mcontext;
}



}
