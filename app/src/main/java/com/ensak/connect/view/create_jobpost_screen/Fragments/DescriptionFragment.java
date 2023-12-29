package com.ensak.connect.view.create_jobpost_screen.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.ensak.connect.R;
import com.ensak.connect.databinding.FragmentDescriptionBinding;
import com.ensak.connect.databinding.FragmentDetailsBinding;
import com.ensak.connect.view.create_jobpost_screen.CreateJobPostViewModel;
import com.ensak.connect.view.LoadingScreen.LoadingViewModel;


public class DescriptionFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    public String jobTitle,companyName,location,companyType,category;
    private FragmentDescriptionBinding binding;
    private CreateJobPostViewModel createJobPostViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false);
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
        createJobPostViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(CreateJobPostViewModel.class);

        createJobPostViewModel.getIsSuccess().observe(this, success -> {
            if(success) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        createJobPostViewModel.getSuccessMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        });

        createJobPostViewModel.getErrorMessage().observe(this, msg -> {
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
        createJobPostViewModel.createJobPost(jobTitle,description,companyName,location,companyType,category,tags);
    }
}