package com.ensak.connect.view.Testing;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ActivityTestingBinding;
import com.ensak.connect.repositories.RepositoryCallBack;
import com.ensak.connect.repositories.resource.ResourceRepository;
import com.ensak.connect.repositories.resource.remote.dto.ResourceResponse;
import com.ensak.connect.service.ActivityResultCallback;
import com.ensak.connect.service.FileUploadService;

public class TestingActivity extends AppCompatActivity {

    ActivityTestingBinding binding;
    FileUploadService fileUploadService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fileUploadService = new FileUploadService(this, getActivityResultRegistry(), new ActivityResultCallback<ResourceResponse>() {
            @Override
            public void onSuccess(ResourceResponse data) {
                Toast.makeText(TestingActivity.this, "FileUpload: Success, id: " + data.getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(TestingActivity.this, "FileUpload: Error", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnOpenFile.setOnClickListener(v -> {
            fileUploadService.selectedAndUpload("*/*");
        });
    }
}