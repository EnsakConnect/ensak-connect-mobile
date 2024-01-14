package com.ensak.connect.presentation.About;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ensak.connect.R;
import android.view.View;
import android.content.Intent;
import android.net.Uri;

import com.ensak.connect.databinding.ActivityAboutBinding;



public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        binding=ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void openLinkedIn(View view) {
        String linkedInUrl = (String) view.getTag();

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl));
        startActivity(browserIntent);
    }
}