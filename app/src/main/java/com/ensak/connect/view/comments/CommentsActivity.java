package com.ensak.connect.view.comments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.ensak.connect.databinding.ActivityCommentsBinding;
import com.ensak.connect.reponse.CommentResponse;
import com.ensak.connect.reponse.UserResponse;
import com.ensak.connect.view_model.CommentViewModel;

import java.util.ArrayList;
import java.util.Date;

public class CommentsActivity extends AppCompatActivity {

    private ActivityCommentsBinding binding;
    private CommentViewModel commentViewModel;

    private ArrayList<CommentResponse> comments;
    private CommentsAdapter adapter;
    private RecyclerView rvComments;
    private CardView cardSendComment;
    private EditText etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        Bundle extras = getIntent().getExtras();
        String postId = "1";
        if (extras != null) {
            postId = extras.getString("postId");
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
                    comments.add(0, new CommentResponse(new UserResponse(), etComment.getText().toString(), new Date()));
                    adapter.notifyDataSetChanged();

                    // Clear input
                    Toast.makeText(CommentsActivity.this, "Comment sent.", Toast.LENGTH_LONG).show();
                    etComment.setText("");
                    etComment.clearFocus();

                    // Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etComment.getWindowToken(), 0);
                }
            }
        });
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
                    adapter.notifyDataSetChanged();
                }
            });
        } catch (Throwable ex) {
            Toast.makeText(context, "Error getting posts.", Toast.LENGTH_LONG);
        }

    }
}