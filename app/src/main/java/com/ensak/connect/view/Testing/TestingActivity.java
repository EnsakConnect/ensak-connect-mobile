package com.ensak.connect.view.Testing;

import androidx.activity.result.ActivityResultCallback;
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

public class TestingActivity extends AppCompatActivity {

    ActivityTestingBinding binding;
    ActivityResultLauncher<String> fileSelectedActivityLauncher;
    ResourceRepository resourceRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        resourceRepository = new ResourceRepository(this);
        fileSelectedActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if(uri == null){
                        Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // viewModel.handleFileUri(uri));
                    resourceRepository.uploadUriFile(uri, new RepositoryCallBack<ResourceResponse>() {
                        @Override
                        public void onSuccess(ResourceResponse data) {
                            Toast.makeText(TestingActivity.this, "FileUpload: Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Toast.makeText(TestingActivity.this, "FileUpload: Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        );
        binding.btnOpenFile.setOnClickListener(v -> {
            fileSelectedActivityLauncher.launch("*/*");
        });
    }
}