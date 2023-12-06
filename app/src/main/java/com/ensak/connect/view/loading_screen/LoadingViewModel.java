package com.ensak.connect.view.loading_screen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoadingViewModel extends ViewModel {
    private final String TAG = getClass().getSimpleName();

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);

    public void setIsLoading(Boolean loading) {
        isLoading.setValue(loading);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
