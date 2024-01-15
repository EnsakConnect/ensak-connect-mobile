
package com.ensak.connect.presentation.profile;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.ensak.connect.R;
import com.ensak.connect.adapters.Profile.CertificateAdapter;
import com.ensak.connect.adapters.Profile.EducationAdapter;
import com.ensak.connect.adapters.Profile.ExperienceAdapter;
import com.ensak.connect.adapters.Profile.SkillsAdapter;
import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.databinding.ProfileActivityBinding;
import com.ensak.connect.repository.resource.model.ResourceResponse;
import com.ensak.connect.service.ActivityResultCallback;
import com.ensak.connect.service.FileUploadService;
import com.ensak.connect.service.GlideAuthUrl;
import com.ensak.connect.service.SessionManagerService;

import java.io.File;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    public static final String KEY_USER_ID = "user_id";
    private ProfileActivityBinding binding;
    private SessionManagerService sessionManager;
    private Integer userId;
    private ExperienceAdapter experienceAdapter;
    private EducationAdapter educationAdapter;
    private SkillsAdapter skillsAdapter;
    private CertificateAdapter certificateAdapter;
    private ProfileViewModel profileViewModel;
    private FileUploadService uploadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProfileActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        uploadService = new FileUploadService(this, getActivityResultRegistry(), new ActivityResultCallback<ResourceResponse>() {
            @Override
            public void onSuccess(ResourceResponse data) {
                profileViewModel.updateResume(data.getId());
            }
            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(ProfileActivity.this, "Error: could not upload file, try again", Toast.LENGTH_SHORT).show();
            }
        });

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        sessionManager = new SessionManagerService(this);
        userId = (Integer) Objects.requireNonNull(getIntent().getExtras()).get(KEY_USER_ID);

        assert userId != null;
        if(! userId.equals(sessionManager.getUserId())) {
            binding.btnModifyProfile.setVisibility(View.GONE);
            binding.modifyEducationButton.setVisibility(View.GONE);
            binding.modifyExperienceButton.setVisibility(View.GONE);
            binding.modifySkillsButton.setVisibility(View.GONE);
            binding.modifyCertificateButton.setVisibility(View.GONE);
            binding.uploadResume.setVisibility(View.GONE);
        }

        initView();
        initViewModel();
        profileViewModel.setUserId(userId);
        profileViewModel.fetchProfileData();
    }

    private void initView() {
        binding.btnModifyProfile.setOnClickListener(v -> {
            Log.d(TAG, "btnModifyProfile click");
            Intent updateProfileIntent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
            Log.d(TAG, "btnModifyProfile Intent:" + updateProfileIntent.toString());
            startActivity(updateProfileIntent);
        });

        binding.experienceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.educationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.skillsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.certificatsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        binding.modifyEducationButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EducationEditActivity.class);
            startActivity(intent);
        });

        binding.modifyExperienceButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ExperienceEditActivity.class);
            startActivity(intent);
        });

        binding.modifySkillsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, SkillsEditActivity.class);
            startActivity(intent);
        });

        binding.modifyCertificateButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, CertificateActivity.class);
            startActivity(intent);
        });

        binding.uploadResume.setOnClickListener(v -> {
            uploadService.selectedAndUpload("application/pdf");
        });

        binding.downloadResume.setOnClickListener(v -> {
            profileViewModel.downloadResume();
        });
    }

    private void initViewModel() {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getIsLoading().observe(this, isLoading -> {
            // TODO: show loading state
        });

        profileViewModel.getCvName().observe(this, filename -> {
            if(filename == null || filename.isEmpty()){
                return;
            }
            File file = new File(getFilesDir(), filename);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri resumeUri = FileProvider.getUriForFile(
                    this,
                    getApplicationContext().getPackageName() + ".provider",
                    file
            );
            Log.d("URIDEBUG", "path:" + resumeUri.toString());
            intent.setDataAndType(resumeUri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        });

        profileViewModel.getIsDownloading().observe(this, isDownloading -> {
            binding.downloadResume.setEnabled(!isDownloading);
            if(isDownloading){
                Toast.makeText(this, "Downloading...", Toast.LENGTH_LONG).show();
            }
        });

        profileViewModel.getErrorMessage().observe(this, errorMessage -> {
            if(errorMessage.isEmpty()){ return; }
            // TODO: show error message/ state
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        profileViewModel.getProfile().observe(this, profileResponse -> {
            if (profileResponse != null) {
                String fullName = profileResponse.getFullName();
                binding.userName.setText(fullName);
                String userTitle = profileResponse.getTitle() == null ? "--" : profileResponse.getTitle();
                String userDescription = profileResponse.getDescription();
                Log.d(TAG, "onCreate: " + userTitle);
                Log.d(TAG, "onCreate: " + userDescription);
                binding.userDetails.setText(userTitle);
                binding.userDescription.setText(userDescription);
                if (profileResponse.getDescription() == null){
                    binding.userDescription.setVisibility(View.GONE);
                }
                if (profileResponse.getResume() == null) {
                    binding.downloadResume.setVisibility(View.GONE);
                }
                experienceAdapter = new ExperienceAdapter(this, profileResponse.getExperienceList());
                binding.experienceRecyclerView.setAdapter(experienceAdapter);

                experienceAdapter.setOnExperienceDeleteListener(experienceId -> {
                    profileViewModel.deleteExperience(experienceId);
                });

                educationAdapter = new EducationAdapter(this, profileResponse.getEducationList());
                binding.educationRecyclerView.setAdapter(educationAdapter);

                educationAdapter.setOnEducationDeleteListener(educationId -> {
                    profileViewModel.deleteEducation(educationId);
                });

                skillsAdapter = new SkillsAdapter(profileResponse.getSkillList());
                binding.skillsRecyclerView.setAdapter(skillsAdapter);

                certificateAdapter = new CertificateAdapter(this, profileResponse.getCertificationList());
                binding.certificatsRecyclerView.setAdapter(certificateAdapter);

                certificateAdapter.setOnCertificateDeleteListener(certificationId -> {
                    profileViewModel.deleteCertification(certificationId);
                });

                if(profileResponse.getResume() != null){
                    binding.uploadResume.setText("Update CV");
                    binding.downloadResume.setVisibility(View.VISIBLE);
                } else {
                    binding.uploadResume.setText("Ajouter un CV");
                }

                Glide.with(this)
                        .load(
                                GlideAuthUrl.getUrl(this, AppConstants.BASE_URL + "resources/" + profileResponse.getProfilePicture())
                        ).placeholder(R.drawable.profile_banner_placeholder)
                        .error(R.drawable.profile_picture_placeholder)
                        .centerCrop()
                        .into(binding.userProfileImage);

                Glide.with(this)
                        .load(
                                GlideAuthUrl.getUrl(this, AppConstants.BASE_URL + "resources/" + profileResponse.getBanner())
                        ).placeholder(R.drawable.profile_banner_placeholder)
                        .error(R.drawable.profile_banner_placeholder)
                        .centerCrop()
                        .into(binding.bannerImage);
            } else {
                Log.d(TAG, "onCreate: Profile response is null");
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        profileViewModel.fetchProfileData();
    }
}