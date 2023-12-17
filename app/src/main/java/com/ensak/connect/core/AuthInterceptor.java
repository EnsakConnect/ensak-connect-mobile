package com.ensak.connect.core;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final String TAG = getClass().getSimpleName();
    private final SessionManager sessionManager;

    public AuthInterceptor(SessionManager sessionManager) {
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
        return path.equals("/api/v1/auth/login") || path.equals("/api/v1/auth/register");
    }
}
