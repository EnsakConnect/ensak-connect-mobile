package com.ensak.connect.presentation.question_post.show;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ensak.connect.R;
import com.ensak.connect.adapters.question_post.AnswerAdapter;
import com.ensak.connect.adapters.question_post.OnAnswerInteraction;
import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.databinding.QuestionPostCreateActivityBinding;
import com.ensak.connect.databinding.QuestionPostShowActivityBinding;
import com.ensak.connect.presentation.profile.ProfileActivity;
import com.ensak.connect.service.GlideAuthUrl;
import com.ensak.connect.utils.DateUtil;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShowQuestionPost extends AppCompatActivity implements OnAnswerInteraction {
    private QuestionPostShowActivityBinding binding;
    private ShowQuestionPostViewModel viewModel;
    private AnswerAdapter answerAdapter;
    private Integer questionPostId;
    public static final String KEY_QUESTION_POST_ID = "question_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = QuestionPostShowActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewModel();
        initView();

        questionPostId = (Integer) getIntent().getExtras().get(KEY_QUESTION_POST_ID);
        viewModel.setQuestionPostId(questionPostId);
        viewModel.fetchQuestionPost();
        viewModel.fetchAllAnswers();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        answerAdapter = new AnswerAdapter(this);
        binding.rvComments.setAdapter(answerAdapter);
        binding.rvComments.setVisibility(View.GONE);

        binding.btnAddAnswer.setOnClickListener(v -> {
            createAnswer();
        });
        binding.llLike.setOnClickListener(v -> {
            likeDislikeQuestionPost();
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ShowQuestionPostViewModel.class);

        viewModel.getQuestion().observe(this, questionPost -> {
            binding.tvUserName.setText(questionPost.getAuthor().getFullName());
            if(questionPost.getAuthor().getTitle() == null || questionPost.getAuthor().getTitle().isEmpty()){
                binding.tvUserTitle.setVisibility(View.GONE);
            } else {
                binding.tvUserTitle.setVisibility(View.VISIBLE);
                binding.tvUserTitle.setText(questionPost.getAuthor().getTitle());
            }
            if(questionPost.getAuthor().getProfilePicture() == null || questionPost.getAuthor().getProfilePicture().isEmpty()){
                Glide.with(this)
                        .load(R.drawable.profile_picture_placeholder)
                        .centerCrop()
                        .into(binding.ivUserImage);
            } else {
                Glide.with(this)
                        .load(GlideAuthUrl.getResource(this, questionPost.getAuthor().getProfilePicture()))
                        .error(R.drawable.profile_picture_placeholder)
                        .placeholder(R.drawable.profile_picture_placeholder)
                        .centerCrop()
                        .into(binding.ivUserImage);
            }

            binding.tvTimeAgo.setText(DateUtil.calculateTimeAgo(questionPost.getCreatedAt()));
            binding.tvBody.setText(questionPost.getQuestion());
            if (questionPost.getLiked()){
                Log.d("like", "already liked");
                binding.llLikeImage.setImageResource(R.drawable.ic_thumbs_up_filled);
            }else {
                Log.d("like", "not liked");
                binding.llLikeImage.setImageResource(R.drawable.ic_thumb_up);
            }
            String likesCount = questionPost.getLikesCount() + " Likes";
            binding.llLikeCount.setText(likesCount);
            String answersCount = questionPost.getAnswersCount() + " Answers";
            binding.tvPostAnswersCount.setText(answersCount);
            binding.tvTags.setText("#" + String.join(", #", questionPost.getTags()));
            binding.crdUserData.setOnClickListener(v -> {
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.putExtra(ProfileActivity.KEY_USER_ID, questionPost.getAuthor().getId());
                startActivity(intent);
            });
        });

        viewModel.getAnswers().observe(this, answers -> {
            answerAdapter.setAnswers(answers);
            binding.tvPostAnswersCount.setText(answers.size() + " Answers");
            if(answers.size() == 0){
                binding.tvNoAnswers.setVisibility(View.VISIBLE);
                binding.rvComments.setVisibility(View.GONE);
            } else {
                binding.rvComments.setVisibility(View.VISIBLE);
                binding.tvNoAnswers.setVisibility(View.GONE);
            }
        });

        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if(errorMessage == null || errorMessage.isEmpty()) return;
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        viewModel.getSuccessMessage().observe(this, successMessage -> {
            if(successMessage == null || successMessage.isEmpty()) return;
            Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show();
            binding.txtNewAnswer.setText("");
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            // TODO: show is loading state / disable actions
        });
    }

    private void createAnswer() {
        String answer = binding.txtNewAnswer.getText().toString();
        viewModel.createAnswer(answer);
    }

    private void likeDislikeQuestionPost() {
        viewModel.likeDislikeQuestionPost();
    }

    @Override
    public void interactUp(Integer id) {
        viewModel.interactAnswerUp(id);
    }

    @Override
    public void interactDown(Integer id) {
        viewModel.interactAnswerDown(id);
    }
}