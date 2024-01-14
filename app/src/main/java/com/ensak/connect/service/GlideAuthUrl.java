package com.ensak.connect.service;

import android.content.Context;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ensak.connect.constants.AppConstants;

public class GlideAuthUrl {

    public static GlideUrl getUrl(Context context, String url) {
        SessionManagerService sessionManagerService = new SessionManagerService(context);
        String token = sessionManagerService.getUserToken();
        return new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + token)
                .build()
        );
    }

    public static GlideUrl getResource(Context context, String resource) {
        return getUrl(context, AppConstants.BASE_URL + "resources/" + resource);
    }
}
