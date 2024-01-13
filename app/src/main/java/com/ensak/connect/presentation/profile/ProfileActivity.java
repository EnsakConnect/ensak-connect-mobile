
package com.ensak.connect.presentation.profile;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
import com.ensak.connect.service.GlideAuthUrl;
import com.ensak.connect.service.SessionManagerService;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProfileActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
    }

    private void initViewModel() {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getIsLoading().observe(this, isLoading -> {
            // TODO: show loading state
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
                String userTitle = profileResponse.getTitle();
                String userDetailsText = userTitle;
                Log.d(TAG, "onCreate: " + userDetailsText);
                binding.userDetails.setText(userDetailsText);

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
                    binding.resumebtn.setText("Voir le CV");
                } else {
                    binding.resumebtn.setText("Ajouter un CV");
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
        profileViewModel.fetchProfileData(

        );
    }
}