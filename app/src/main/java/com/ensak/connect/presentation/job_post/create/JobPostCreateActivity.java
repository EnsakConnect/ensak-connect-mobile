package com.ensak.connect.presentation.job_post.create;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.ensak.connect.R;
import com.ensak.connect.adapters.fragmentAdapter.FragmentAdapter;
import com.ensak.connect.databinding.JobPostCreateActivityBinding;
import com.ensak.connect.presentation.job_post.create.fragments.DetailsFragment;

public class JobPostCreateActivity extends AppCompatActivity {

    private JobPostCreateActivityBinding binding;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_job_post);
        binding = JobPostCreateActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarA);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbarA.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewPager = findViewById(R.id.viewpager);

        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentAdapter.addFragment(new DetailsFragment());
        viewPager.setAdapter(fragmentAdapter);




    }
}