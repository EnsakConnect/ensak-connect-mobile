package com.ensak.connect.repository.shared;

public interface RepositoryCallBack<T> {
    public void onSuccess(T data);
    public void onFailure(Throwable throwable);
}
