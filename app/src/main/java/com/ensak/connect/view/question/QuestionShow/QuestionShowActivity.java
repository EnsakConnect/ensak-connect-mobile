package com.ensak.connect.view.question.QuestionShow;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;

import com.ensak.connect.databinding.ActivityQuestionShowBinding;

import java.util.stream.Collectors;

public class QuestionShowActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    public final static String EXTRA_ID = "question_id";
    private ActivityQuestionShowBinding binding;
    private QuestionShowViewModel questionShowViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        initViewModel();

        Integer id = getIntent().getIntExtra(QuestionShowActivity.EXTRA_ID, 0);
        questionShowViewModel.fetchPost(id);
    }

    private void initViewModel() {
        questionShowViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(QuestionShowViewModel.class);

        questionShowViewModel.getIsLoading().observe(this, loading -> {
            if(loading){
                binding.toolbarTitle.setText("Loading...");
            } else {
                binding.toolbarTitle.setText("Question");
            }
        });

        questionShowViewModel.getPost().observe(this, post -> {
            binding.tvBody.setText(post.getQuestion());
            binding.tvUserName.setText(post.getAuthor().getName());
            binding.tvUserTitle.setText(post.getAuthor().getTitle());
            binding.tvTags.setText(
                    post.getTags().stream().map(tag -> "#" + tag)
                            .collect(Collectors.joining(", "))
            );
        });
    }
}