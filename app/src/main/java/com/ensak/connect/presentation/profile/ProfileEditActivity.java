package com.ensak.connect.presentation.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ensak.connect.R;
import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.databinding.ProfileEditActivityBinding;
import com.ensak.connect.presentation.auth.password_reset.ChangePasswordActivity;
import com.ensak.connect.presentation.testing.TestingActivity;
import com.ensak.connect.repository.profile.model.ProfileInformationRequest;
import com.ensak.connect.repository.resource.model.ResourceResponse;
import com.ensak.connect.service.ActivityResultCallback;
import com.ensak.connect.service.FileUploadService;
import com.ensak.connect.service.GlideAuthUrl;
import com.ensak.connect.service.SessionManagerService;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileEditActivity extends AppCompatActivity {
    private ProfileEditActivityBinding binding;
    private ProfileEditViewModel profileEditViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProfileEditActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initViewModel();
        profileEditViewModel.refresh();
    }
    private void initView() {
        // Toolbar setup
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        binding.btnUpdateInfo.setOnClickListener(v -> {
            saveInformation();
        });

        binding.btnUpdateProfilePicture.setOnClickListener(v -> {
            updateProfilePicture();
        });

        binding.btnUpdateProfileBanner.setOnClickListener(v -> {
            updateProfileBanner();
        });

        binding.btnChangePassword.setOnClickListener(v -> {
            Intent changePasswordIntent = new Intent(this, ChangePasswordActivity.class);
            startActivity(changePasswordIntent);
        });
    }

    private void initViewModel() {
        profileEditViewModel = new ViewModelProvider(this).get(ProfileEditViewModel.class);

        profileEditViewModel.getInformation().observe(this, information -> {
            binding.txtFullName.setText(information.getFullName());
            binding.txtProfileTitle.setText(information.getTitle());
            binding.txtDescription.setText(information.getDescription());
            binding.txtPhone.setText(information.getPhone());
            binding.txtCity.setText(information.getCity());
            binding.txtAddress.setText(information.getAddress());
            binding.txtType.setText(information.getProfileType());
        });

        profileEditViewModel.getProfilePicture().observe(this, profilePicture -> {
            String url = AppConstants.BASE_URL + "resources/"+profilePicture;
            Glide.with(this)
                    .load(GlideAuthUrl.getUrl(this, url))
                    .placeholder(R.drawable.profile_picture_placeholder)
                    .error(R.drawable.profile_picture_placeholder)
                    .centerCrop()
                    .into(binding.imgProfilePicture);
        });

        profileEditViewModel.getProfileBanner().observe(this, profileBanner -> {
            String url = AppConstants.BASE_URL + "resources/"+profileBanner;
            Glide.with(this)
                    .load(GlideAuthUrl.getUrl(this, url))
                    .placeholder(R.drawable.profile_banner_placeholder)
                    .error(R.drawable.profile_banner_placeholder)
                    .centerCrop()
                    .into(binding.imgProfileBanner);
        });

        profileEditViewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading) {
                binding.txtFullName.setEnabled(false);
                binding.txtProfileTitle.setEnabled(false);
                binding.txtDescription.setEnabled(false);
            } else {
                binding.txtFullName.setEnabled(true);
                binding.txtProfileTitle.setEnabled(true);
                binding.txtDescription.setEnabled(true);
            }
        });

        profileEditViewModel.getSuccessMessage().observe(this, successMessage -> {
            if(!successMessage.isEmpty()) {
                Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show();
            }
        });

        profileEditViewModel.getErrorMessage().observe(this, errorMessage -> {
            if(errorMessage == null || errorMessage.isEmpty()){
                return;
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });
    }

    private void saveInformation() {
        ProfileInformationRequest request = new ProfileInformationRequest();
        request.setFullName(binding.txtFullName.getText().toString());
        request.setTitle(binding.txtProfileTitle.getText().toString());
        request.setDescription(binding.txtDescription.getText().toString());
        request.setPhone(binding.txtPhone.getText().toString());
        request.setCity(binding.txtCity.getText().toString());
        request.setAddress(binding.txtAddress.getText().toString());
        request.setProfileType(binding.txtType.getText().toString());

        profileEditViewModel.saveInformation(request);
    }

    private void updateProfilePicture() {
        FileUploadService profileUploadService = new FileUploadService(this, getActivityResultRegistry(), new ActivityResultCallback<ResourceResponse>() {
            @Override
            public void onSuccess(ResourceResponse data) {
                profileEditViewModel.updateProfilePicture(data.getId());
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(ProfileEditActivity.this, "Error uploading file.", Toast.LENGTH_SHORT).show();
            }
        });

        profileUploadService.selectedAndUpload("image/*");
    }

    private void updateProfileBanner() {
        FileUploadService profileUploadService = new FileUploadService(this, getActivityResultRegistry(), new ActivityResultCallback<ResourceResponse>() {
            @Override
            public void onSuccess(ResourceResponse data) {
                profileEditViewModel.updateProfileBanner(data.getId());
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(ProfileEditActivity.this, "Error uploading file.", Toast.LENGTH_SHORT).show();
            }
        });

        profileUploadService.selectedAndUpload("image/*");
    }
}