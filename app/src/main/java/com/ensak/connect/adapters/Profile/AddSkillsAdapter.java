package com.ensak.connect.adapters.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ensak.connect.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.model.Skill;

import java.util.List;

public class AddSkillsAdapter extends RecyclerView.Adapter<AddSkillsAdapter.SkillViewHolder> {

    private List<Skill> skillsList;

    public AddSkillsAdapter(List<Skill> skillsList) {
        this.skillsList = skillsList;
    }


    @Override
    public SkillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_item_layout, parent, false);
        return new SkillViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddSkillsAdapter.SkillViewHolder holder, int position) {
        Skill skill = skillsList.get(position);
        holder.skillTextView.setText(skill.getName());
    }
    @Override
    public int getItemCount() {
        return skillsList.size();
    }

    public void setSkills(List<Skill> newSkills) {
        this.skillsList = newSkills;
        notifyDataSetChanged();
    }


    public static class SkillViewHolder extends RecyclerView.ViewHolder {
        public TextView skillTextView;
        public ImageView editImageView;
        public ImageView deleteImageView;

        public SkillViewHolder(View v) {
            super(v);
            skillTextView = v.findViewById(R.id.txtTitle);
            editImageView = v.findViewById(R.id.iconEdit);
            deleteImageView = v.findViewById(R.id.iconDelete);
        }
    }
}
