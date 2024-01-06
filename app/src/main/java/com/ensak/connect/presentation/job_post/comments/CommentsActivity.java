package com.ensak.connect.presentation.job_post.comments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.adapters.comments.CommentsAdapter;
import com.ensak.connect.databinding.JobPostCommentsActivityBinding;
import com.ensak.connect.repository.job_post.model.JobPostCommentResponse;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    private JobPostCommentsActivityBinding binding;
    private CommentViewModel commentViewModel;

    private ArrayList<JobPostCommentResponse> comments;
    private CommentsAdapter adapter;
    private RecyclerView rvComments;
    private CardView cardSendComment;
    private EditText etComment;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = JobPostCommentsActivityBinding.inflate(getLayoutInflater());
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

        Bundle extras = getIntent().getExtras();
        postId = "1";
        if (extras != null) {
            postId = String.valueOf(extras.getInt("postId"));
        }

        initRecyclerView();
        initSendNewComment();
        loadComments(this, postId);
    }

    private void initSendNewComment() {
        etComment = binding.etComment;
        cardSendComment = binding.cardSendComment;

        cardSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = etComment.getText().toString();
                if (comment.isEmpty()) {
                    Toast.makeText(CommentsActivity.this, "Please enter a comment.", Toast.LENGTH_LONG).show();
                } else {
                    // send comment to API
                    sendNewComment(postId, etComment.getText().toString().trim());

                    // Clear input
                    etComment.setText("");
                    etComment.clearFocus();

                    // Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etComment.getWindowToken(), 0);
                }
            }
        });
    }

    private void sendNewComment(String postId, String comment) {
        try {
            commentViewModel.sendComment(postId, comment).observe((LifecycleOwner) this, response -> {
                if (response != null) {

                    String message = response.getComment();
                    Log.d("Main Log", message);

                    comments.add(0, response);
                    adapter.notifyItemInserted(0);
                }
            });
        } catch (Throwable ex) {
            Toast.makeText(this, "Error sending comment.", Toast.LENGTH_LONG);
        }
    }

    private void initRecyclerView() {
        comments = new ArrayList<>();

        rvComments = binding.rvComments;
        adapter = new CommentsAdapter(comments);
        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadComments(Context context, String postId) {
        commentViewModel = ViewModelProviders.of(this).get(CommentViewModel.class);
        try {
            commentViewModel.getComments(postId).observe((LifecycleOwner) context, responses -> {
                if (responses != null) {

                    String message = responses.get(0).getUser().getFirstname();
                    Log.d("Main Log", message);

                    comments.clear();
                    comments.addAll(responses);
                    adapter.notifyDataSetChanged();
                }
            });
        } catch (Throwable ex) {
            Toast.makeText(context, "Error getting posts.", Toast.LENGTH_LONG);
        }

    }
}