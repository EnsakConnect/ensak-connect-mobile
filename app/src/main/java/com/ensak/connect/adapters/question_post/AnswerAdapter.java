package com.ensak.connect.adapters.question_post;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ensak.connect.R;
import com.ensak.connect.presentation.profile.ProfileActivity;
import com.ensak.connect.presentation.question_post.show.ShowQuestionPostViewModel;
import com.ensak.connect.repository.question_post.model.QuestionPostAnswerResponse;
import com.ensak.connect.service.GlideAuthUrl;
import com.ensak.connect.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

public class AnswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  OnAnswerInteraction onAnswerInteraction;
    private List<QuestionPostAnswerResponse> answers = new ArrayList<>();

    public AnswerAdapter(OnAnswerInteraction onAnswerInteraction) {
        this.onAnswerInteraction = onAnswerInteraction;
    }

    public void setAnswers(List<QuestionPostAnswerResponse> answers) {
        this.answers.clear();
        this.answers.addAll(answers);
        notifyDataSetChanged();
    }

    public void addAnswer(QuestionPostAnswerResponse answer) {
        this.answers.add(0, answer);
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_post_answer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        QuestionPostAnswerResponse answer = answers.get(position);
        Context context = holder.itemView.getContext();

        TextView tvUserName = holder.itemView.findViewById(R.id.tv_user_name);
        TextView tvUserTitle = holder.itemView.findViewById(R.id.tv_user_title);
        TextView txtCommentBody = holder.itemView.findViewById(R.id.txtCommentBody);
        ImageView upImg = holder.itemView.findViewById(R.id.ll_up_image);
        TextView interactionsCount = holder.itemView.findViewById(R.id.ll_interaction_count);
        ImageView downImg = holder.itemView.findViewById(R.id.ll_down_image);
        TextView tvTimeAgo = holder.itemView.findViewById(R.id.tv_time_ago);
        ImageView imgProfilePicture = holder.itemView.findViewById(R.id.iv_user_image);

        tvUserName.setText(answer.getAuthor().getFullName());
        tvUserTitle.setText(answer.getAuthor().getTitle());
        txtCommentBody.setText(answer.getContent());
        String interactionCount = String.valueOf(answer.getInteractionsCount());
        interactionsCount.setText(interactionCount);

        if (answer.getUp() != null) {
            if (answer.getUp()){
                Log.d("tagIsUp", "true");
                upImg.setImageResource(R.drawable.ic_up_fill);
                downImg.setImageResource(R.drawable.ic_down);
            }else {
                Log.d("tagIsUp", "false");
                upImg.setImageResource(R.drawable.ic_up);
                downImg.setImageResource(R.drawable.ic_down_fill);
            }
        }

        tvTimeAgo.setText(DateUtil.calculateTimeAgo(answer.getCreatedAt()));
        if(answer.getAuthor().getProfilePicture() == null || answer.getAuthor().getProfilePicture().isEmpty()){
            Glide.with(context)
                    .load(R.drawable.profile_picture_placeholder)
                    .centerCrop()
                    .into(imgProfilePicture);
        } else {
            Glide.with(context)
                    .load(GlideAuthUrl.getResource(context, answer.getAuthor().getProfilePicture()))
                    .placeholder(R.drawable.profile_picture_placeholder)
                    .error(R.drawable.profile_picture_placeholder)
                    .centerCrop()
                    .into(imgProfilePicture);
        }

        holder.itemView.findViewById(R.id.ll_up_image).setOnClickListener(v ->
                onAnswerInteraction.interactUp(answer.getId()));
        holder.itemView.findViewById(R.id.ll_down_image).setOnClickListener(v ->
                onAnswerInteraction.interactDown(answer.getId()));

        holder.itemView.findViewById(R.id.crdUserData).setOnClickListener(v -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra(ProfileActivity.KEY_USER_ID, answer.getAuthor().getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
