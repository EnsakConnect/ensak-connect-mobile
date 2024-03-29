package com.ensak.connect.presentation.job_post.create.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ensak.connect.R;
import com.ensak.connect.databinding.JobPostDetailsFragmentBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailsFragment extends Fragment {

    private JobPostDetailsFragmentBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = JobPostDetailsFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setupCategorySelect();



        binding.btnNext.setOnClickListener(v -> {
            navigateToDescriptionFragment();
        });

        return view;
    }

    private void navigateToDescriptionFragment() {

        String jobTitle=binding.jobTitle.getText().toString();
        String companyName=binding.companyName.getText().toString();
        String location=binding.locationLbl.getText().toString();
        String companyType=binding.companyType.getText().toString();
        String categoryselected= binding.slctCategory.getSelectedItem().toString();

        Bundle bundle = new Bundle();
        bundle.putString("jobTitle", jobTitle);
        bundle.putString("companyName", companyName);
        bundle.putString("location", location);
        bundle.putString("companyType", companyType);
        bundle.putString("category", categoryselected);



        DescriptionFragment descriptionFragment = new DescriptionFragment();
        descriptionFragment.setArguments(bundle);


        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.firstfragment, descriptionFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void setupCategorySelect() {
        String[] categories = {"PFE", "CDI", "Doctorate"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories);
        binding.slctCategory.setAdapter(adapter);
    }
}