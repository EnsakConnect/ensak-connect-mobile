package com.ensak.connect.adapters.question_post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.R;
import com.ensak.connect.repository.question_post.model.QuestionPostAnswerResponse;
import com.ensak.connect.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<QuestionPostAnswerResponse> answers = new ArrayList<>();

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
        QuestionPostAnswerResponse answer = answers.get(position);
        TextView tvUserName = holder.itemView.findViewById(R.id.tv_user_name);
        TextView tvUserTitle = holder.itemView.findViewById(R.id.tv_user_title);
        TextView txtCommentBody = holder.itemView.findViewById(R.id.txtCommentBody);
        TextView tvTimeAgo = holder.itemView.findViewById(R.id.tv_time_ago);
        tvUserName.setText(answer.getAuthor().getFullName());
        tvUserTitle.setText(answer.getAuthor().getTitle());
        txtCommentBody.setText(answer.getContent());
        tvTimeAgo.setText(DateUtil.calculateTimeAgo(answer.getCreatedAt()));
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
