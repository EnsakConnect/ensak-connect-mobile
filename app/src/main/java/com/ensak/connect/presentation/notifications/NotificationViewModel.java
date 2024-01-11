package com.ensak.connect.presentation.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.notification.NotificationRepository;
import com.ensak.connect.repository.notification.model.NotificationResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NotificationViewModel extends ViewModel {
    private NotificationRepository notificationRepository;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private MutableLiveData<List<NotificationResponse>> notifications = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public NotificationViewModel(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void fetchNotifications() {
        isLoading.setValue(true);
        notificationRepository.fetchNotification(new RepositoryCallBack<List<NotificationResponse>>() {
            @Override
            public void onSuccess(List<NotificationResponse> data) {
                notifications.setValue(data);
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error: could not fetch notifications.");
            }
        });
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<List<NotificationResponse>> getNotifications() {
        return notifications;
    }
}
