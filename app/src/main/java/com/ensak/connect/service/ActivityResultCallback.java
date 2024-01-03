package com.ensak.connect.service;

public interface ActivityResultCallback<T> {
    void onSuccess(T data);
    void onError(Throwable throwable);
}