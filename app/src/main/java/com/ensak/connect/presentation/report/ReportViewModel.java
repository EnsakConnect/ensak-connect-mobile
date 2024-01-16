package com.ensak.connect.presentation.report;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.report.ReportRepository;
import com.ensak.connect.repository.report.ReportRequest;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReportViewModel extends ViewModel {

    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private ReportRepository reportRepository;
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>(false);

    @Inject
    public ReportViewModel(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    public void SendReport(ReportRequest reportRequest){
        reportRepository.sendReport(reportRequest, new RepositoryCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                isSuccess.setValue(true);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isSuccess.setValue(false);
                errorMessage.setValue("Error loading profile information");
            }
        });
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public LiveData<Boolean> getIsSuccess() {return isSuccess;}
}
