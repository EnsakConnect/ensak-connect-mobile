package com.ensak.connect.presentation.question_post.show;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.ensak.connect.R;
import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.databinding.QuestionPostCreateActivityBinding;
import com.ensak.connect.databinding.QuestionPostShowActivityBinding;
import com.ensak.connect.service.GlideAuthUrl;
import com.ensak.connect.utils.DateUtil;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShowQuestionPost extends AppCompatActivity {
    private QuestionPostShowActivityBinding binding;
    private ShowQuestionPostViewModel viewModel;
    private Integer questionPostId;
    public static final String KEY_QUESTION_POST_ID = "question_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = QuestionPostShowActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initViewModel();
        questionPostId = (Integer) getIntent().getExtras().get(KEY_QUESTION_POST_ID);
        viewModel.setQuestionPostId(questionPostId);
        viewModel.fetchQuestionPost();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ShowQuestionPostViewModel.class);

        viewModel.getQuestion().observe(this, questionPost -> {
            binding.tvUserName.setText(questionPost.getAuthor().getFullName());
            binding.tvUserTitle.setText(questionPost.getAuthor().getTitle());
            Glide.with(this)
                    .load(GlideAuthUrl.getUrl(this, AppConstants.BASE_URL + "resources/" + questionPost.getAuthor().getProfilePicture()))
                    .error(R.drawable.profile_picture_placeholder)
                    .placeholder(R.drawable.profile_picture_placeholder)
                    .centerCrop()
                    .into(binding.ivUserImage);
            binding.tvTimeAgo.setText(DateUtil.calculateTimeAgo(questionPost.getCreatedAt()));
            binding.tvBody.setText(questionPost.getQuestion());
            binding.tvTags.setText("#" + String.join(", #", questionPost.getTags()));
        });
    }
}