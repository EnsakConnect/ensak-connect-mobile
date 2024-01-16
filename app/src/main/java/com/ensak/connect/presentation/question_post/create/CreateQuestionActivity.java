package com.ensak.connect.presentation.question_post.create;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.ensak.connect.databinding.QuestionPostCreateActivityBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateQuestionActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private QuestionPostCreateActivityBinding binding;
    private CreateQuestionViewModel createQuestionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = QuestionPostCreateActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        initViewModel();

        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });

        binding.btnCreate.setOnClickListener(v -> {
            createQuestionPost();
        });
    }

    private void initViewModel() {
        createQuestionViewModel = new ViewModelProvider(this).get(CreateQuestionViewModel.class);

        createQuestionViewModel.getIsSuccess().observe(this, success -> {
            if(success) {
                finish();
            }
        });

        createQuestionViewModel.getSuccessMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        createQuestionViewModel.getErrorMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        });
    }

    private void createQuestionPost() {
        String question = binding.txtQuestion.getText().toString().trim();
        String tags = binding.txtTags.getText().toString().trim();
        createQuestionViewModel.createQuestionPost(question, tags);
    }
}