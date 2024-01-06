package com.ensak.connect.presentation.testing;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.ensak.connect.databinding.ActivityTestingBinding;
import com.ensak.connect.repository.resource.model.ResourceResponse;
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