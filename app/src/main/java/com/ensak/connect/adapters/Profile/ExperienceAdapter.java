package com.ensak.connect.adapters.Profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ensak.connect.R;
import com.ensak.connect.model.Education;
import com.ensak.connect.model.Experience;
import com.ensak.connect.utils.DateUtil;
import com.ensak.connect.presentation.profile.ExperienceEditActivity;

import java.util.ArrayList;
import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> {

    private List<Experience> experienceList;
    private Context context;

    public interface OnExperienceDeleteListener {
        void onExperienceDelete(int experienceId);
    }

    private ExperienceAdapter.OnExperienceDeleteListener deleteListener;
    public void setOnExperienceDeleteListener(ExperienceAdapter.OnExperienceDeleteListener listener) {
        this.deleteListener = listener;
    }


    public ExperienceAdapter(Context context, List<Experience> experienceList) {
        this.context = context;
        this.experienceList = experienceList != null ? experienceList : new ArrayList<>();
    }

    @NonNull
    @Override
    public ExperienceAdapter.ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_expereince_fragment, parent, false);
        return new ExperienceAdapter.ExperienceViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceViewHolder holder, int position) {
        Experience experience = experienceList.get(position);
        holder.bind(experience, context);
    }

    @Override
    public int getItemCount() {
        return experienceList.size();
    }

    static class ExperienceViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, companyTextView,
        locationTextView, periodTextView, descriptionTextView;

        ImageView iconEdit, iconDelete;
        private ExperienceAdapter.OnExperienceDeleteListener deleteListener;
        private List<Experience> experienceList;
        private final ExperienceAdapter adapter;


        ExperienceViewHolder(View itemView, Context context, ExperienceAdapter experienceAdapter) {
            super(itemView);
            this.adapter = experienceAdapter;
            titleTextView = itemView.findViewById(R.id.experienceTitle);
            companyTextView = itemView.findViewById(R.id.companyLocation);
//            locationTextView = itemView.findViewById(R.id.companyLocation);
            periodTextView = itemView.findViewById(R.id.experiencePeriod);
            descriptionTextView = itemView.findViewById(R.id.experienceDescription);
            iconEdit = itemView.findViewById(R.id.iconEdit);
            iconDelete = itemView.findViewById(R.id.iconDelete);
        }

        void bind(Experience experience, Context context) {
            titleTextView.setText(experience.getPositionTitle());
            companyTextView.setText(experience.getCompanyName());
//          locationTextView.setText(experience.getLocation());
            periodTextView.setText(DateUtil.formatPeriod(experience.getStartDate(), experience.getEndDate()));
            descriptionTextView.setText(experience.getDescription());
            if (iconEdit != null) {
                iconEdit.setOnClickListener(v -> {
                    Intent intent = new Intent(context, ExperienceEditActivity.class);
                    intent.putExtra("id", String.valueOf(experience.getId()));
                    intent.putExtra("title", titleTextView.getText().toString());
                    intent.putExtra("company", companyTextView.getText().toString());
                    intent.putExtra("startDate", experience.getStartDate().toString());
                    intent.putExtra("endDate", experience.getEndDate().toString());
                    intent.putExtra("description", descriptionTextView.getText().toString());
                    intent.putExtra("isUpdate", true);
                    context.startActivity(intent);
                });
            }

            if (iconDelete != null) {
                iconDelete.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && adapter.deleteListener != null) {
                        Experience experienceToDelete = adapter.experienceList.get(position);
                        adapter.deleteListener.onExperienceDelete(experienceToDelete.getId());
                    }
                });
            }
        }

    }
}

