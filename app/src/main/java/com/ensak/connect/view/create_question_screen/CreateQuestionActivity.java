package com.ensak.connect.view.create_question_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ActivityCreateQuestionBinding;

public class CreateQuestionActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private ActivityCreateQuestionBinding binding;
    private CreateQuestionViewModel createQuestionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });
    }
}