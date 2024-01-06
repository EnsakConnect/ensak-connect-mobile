package com.ensak.connect.adapters.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.R;
import com.ensak.connect.repository.profile.model.SkillResponse;

import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.SkillViewHolder> {

    private List<SkillResponse> skillsList;

    public SkillsAdapter(List<SkillResponse> skillsList) {
        this.skillsList = skillsList;
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.skills_fragment, parent, false);
        return new SkillViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        SkillResponse skill = skillsList.get(position);
        holder.bind(skill);
    }

    @Override
    public int getItemCount() {
        return skillsList != null ? skillsList.size() : 0;
    }

    static class SkillViewHolder extends RecyclerView.ViewHolder {
        TextView skillTextView;

        SkillViewHolder(View itemView) {
            super(itemView);
            skillTextView = itemView.findViewById(R.id.skillTextView); // ID from your skill_item.xml
        }

        void bind(SkillResponse skill) {
            skillTextView.setText(skill.getName());
//
        }
    }
}
