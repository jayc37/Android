package com.example.admin.activy;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCxt;
    private MySingleton(Context context){
        mCxt = context;
        requestQueue = getRequestQuese();
    }

    private RequestQueue getRequestQuese() {
        if(requestQueue ==null){
            requestQueue = Volley.newRequestQueue(mCxt.getApplicationContext());
        }
        return requestQueue;
    }
    public  static  synchronized MySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }
    public<T> void  addToRequest(Request<T> request)
    {
        getRequestQuese().add(request);
    }
}
