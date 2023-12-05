package com.ensak.connect.retrofit;

import android.content.Context;

import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.core.AuthInterceptor;
import com.ensak.connect.core.SessionManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {
    private Retrofit retrofit;

    public RetrofitRequest(){
        getRetrofitInstance();
    }

<<<<<<< HEAD

    public Retrofit getRetrofitInstance() {
=======
    public static Retrofit getRetrofitInstance(Context context) {
>>>>>>> 8f2173b4abf46c8fe6fa4665dec145f3fac9d500
        if (retrofit == null) {
            // Create SessionManager instance with context
            SessionManager sessionManager = new SessionManager(context);

            // Create OkHttpClient instance with AuthInterceptor
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(sessionManager))
                    .build();

            // Build Retrofit instance
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}