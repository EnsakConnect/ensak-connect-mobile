package com.ensak.connect.view.question.QuestionShow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ActivityQuestionShowBinding;

public class QuestionShowActivity extends AppCompatActivity {

    ActivityQuestionShowBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}