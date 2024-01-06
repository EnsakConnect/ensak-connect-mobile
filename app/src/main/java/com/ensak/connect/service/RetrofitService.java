package com.ensak.connect.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ensak.connect.constants.AppConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    public static Retrofit retrofit;

    public RetrofitService(Context context){
        getRetrofitInstance(context);
    }



    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {

            SessionManagerService sessionManager = new SessionManagerService(context);

            // Debug interceptor
            HttpLoggingInterceptor debug_interceptor = new HttpLoggingInterceptor();
            debug_interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Create OkHttpClient instance with AuthInterceptor
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new RetrofitService.AuthInterceptor(sessionManager))
                    .addInterceptor(debug_interceptor)
                    .build();

            // Build Retrofit instance
            retrofit = new retrofit2.Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static class AuthInterceptor implements Interceptor {

        private final String TAG = getClass().getSimpleName();
        private final SessionManagerService sessionManager;

        public AuthInterceptor(SessionManagerService sessionManager) {
            this.sessionManager = sessionManager;
        }

        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Log.d(TAG, "intercept: Interceptor running");
            Request original = chain.request();

            if (isTokenExemptedRequest(original)) {

                return chain.proceed(original);
            }

            // For other requests, add the token
            String token = sessionManager.getUserToken();
            Log.d(TAG, "intercept: Token = " + token );
            if (token != null && !token.isEmpty()) {
                Request.Builder builder = original.newBuilder()
                        .header("Authorization", "Bearer " + token);
                Request request = builder.build();
                return chain.proceed(request);
            }

            return chain.proceed(original);
        }

        private boolean isTokenExemptedRequest(Request request) {
            // Check the request URL and return true if it's for login or register
            String path = request.url().encodedPath();
            return path.equals("/api/v1/auth/login") || path.equals("/api/v1/auth/register") || path.equals("/api/v1/health");
        }
    }
}