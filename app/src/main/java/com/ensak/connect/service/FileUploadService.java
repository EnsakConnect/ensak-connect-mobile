package com.ensak.connect.service;

import android.content.Context;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import com.ensak.connect.repositories.RepositoryCallBack;
import com.ensak.connect.repositories.resource.ResourceRepository;
import com.ensak.connect.repositories.resource.remote.dto.ResourceResponse;

public class FileUploadService {
    ActivityResultLauncher<String> fileSelectedActivityLauncher;
    ResourceRepository resourceRepository;

    public FileUploadService(Context context, ActivityResultRegistry registry, ActivityResultCallback<ResourceResponse> callback) {
        resourceRepository = new ResourceRepository(context);
        fileSelectedActivityLauncher = registry.register(
                "upload-selected-file",
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if(uri == null){
                        return;
                    }
                    resourceRepository.uploadUriFile(uri, new RepositoryCallBack<ResourceResponse>() {
                        @Override
                        public void onSuccess(ResourceResponse data) {
                            callback.onSuccess(data);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            callback.onError(throwable);
                        }
                    });
                }
            );
    }

    public void selectedAndUpload(String type) {
        fileSelectedActivityLauncher.launch(type);
    }
}
