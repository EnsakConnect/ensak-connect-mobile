package com.ensak.connect.retrofit;

import android.content.Context;

import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.core.AuthInterceptor;
import com.ensak.connect.core.SessionManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {
    public static Retrofit retrofit;

    public RetrofitRequest(Context context){
        getRetrofitInstance(context);
    }



    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            // Create SessionManager instance with context
            SessionManager sessionManager = new SessionManager(context);

            // Debug interceptor
            HttpLoggingInterceptor debug_interceptor = new HttpLoggingInterceptor();
            debug_interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Create OkHttpClient instance with AuthInterceptor
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(sessionManager))
                    .addInterceptor(debug_interceptor)
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