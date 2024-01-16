package com.ensak.connect.adapters.Profile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ensak.connect.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.model.Skill;
import com.ensak.connect.repository.profile.model.SkillResponse;

import java.util.List;

public class AddSkillsAdapter extends RecyclerView.Adapter<AddSkillsAdapter.SkillViewHolder> {
    public interface OnSkillDeleteListener {
        void onSkillDelete(int position);
    }

    private OnSkillDeleteListener deleteListener;

    public void setOnSkillDeleteListener(OnSkillDeleteListener listener) {
        this.deleteListener = listener;
    }

    private List<Skill> skillsList;

    public AddSkillsAdapter(List<Skill> skillsList) {
        this.skillsList = skillsList;
    }


    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_item_layout, parent, false);
        return new SkillViewHolder(itemView, deleteListener);
    }


    @Override
    public void onBindViewHolder(@NonNull AddSkillsAdapter.SkillViewHolder holder, int position) {
        Skill skill = skillsList.get(position);
        holder.txtTitle.setText(skill.getName());
    }
    @Override
    public int getItemCount() {
        return skillsList.size();
    }

    public void setSkills(List<Skill> newSkills) {
        this.skillsList = newSkills;
        notifyDataSetChanged();
    }


    static class SkillViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;

        ImageView deleteIcon;

        SkillViewHolder(View itemView, OnSkillDeleteListener deleteListener) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);

            deleteIcon = itemView.findViewById(R.id.iconDelete);

            deleteIcon.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && deleteListener != null) {
                    deleteListener.onSkillDelete(position);
                }
            });
        }

        void bind(SkillResponse skill) {
            txtTitle.setText(skill.getName());
        }
    }

}
