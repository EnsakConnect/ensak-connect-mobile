package com.ensak.connect.repositories;

public interface RepositoryCallBack<T> {
    public void onSuccess(T data);
    public void onFailure(Throwable throwable);
}
