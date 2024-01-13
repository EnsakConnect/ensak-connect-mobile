package com.ensak.connect.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Optional;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
public class SessionManagerService {
    private static final String PREF_NAME = "UserSession";
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    private static final String KEY_USER_TOKEN = "UserToken";
    private static final String KEY_USER_ID = "UserId";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    @Provides
    @Singleton
    public static SessionManagerService provideSessionManagerService(@ApplicationContext Context context) {
        return new SessionManagerService(context);
    }

    public SessionManagerService(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void createSession(String token) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_USER_TOKEN, token);
        editor.apply();
    }

    public void setUserToken(String token) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_USER_TOKEN, token);
        editor.apply();
    }

    public void setUserId(Integer id) {
        editor.putInt(KEY_USER_ID, id);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }

    public String getUserToken() {
        return preferences.getString(KEY_USER_TOKEN, null);
    }
    public Integer getUserId() {
        return preferences.getInt(KEY_USER_ID, 0);
    }
    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
}