package com.ensak.connect.presentation.job_post.create.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ensak.connect.databinding.JobPostDescriptionFragmentBinding;
import com.ensak.connect.presentation.job_post.create.JobPostCreateViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DescriptionFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    public String jobTitle,companyName,location,companyType,category;
    private JobPostDescriptionFragmentBinding binding;
    private JobPostCreateViewModel jobPostCreateViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = JobPostDescriptionFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getDetailsFragmentInfos();
        initializeViewModel();
        binding.btnPost.setOnClickListener(view1 -> {
            createJobPost();
        });

        binding.btnCancel.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
    private void initializeViewModel() {
        jobPostCreateViewModel = new ViewModelProvider(this)
                .get(JobPostCreateViewModel.class);

        jobPostCreateViewModel.getIsSuccess().observe(getViewLifecycleOwner(), success -> {
            if(success) {
                getActivity().finish();
            }
        });

        jobPostCreateViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        });

        jobPostCreateViewModel.getErrorMessage().observe(getViewLifecycleOwner(), msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(getContext(),"Error"+msg, Toast.LENGTH_SHORT).show();

        });
    }


    void getDetailsFragmentInfos(){
        Bundle arguments = getArguments();

        jobTitle = arguments.getString("jobTitle");
        companyName = arguments.getString("companyName");
        location = arguments.getString("location");
        companyType = arguments.getString("companyType");
        category=arguments.getString("category");

    }

    private void createJobPost(){
        String description=binding.editTextDescription.getText().toString();
        String tags = binding.postTags.getText().toString().trim();
        jobPostCreateViewModel.createJobPost(jobTitle,description,companyName,location,companyType,category,tags);
    }
}