package com.ensak.connect.adapters.Profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ensak.connect.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.model.Education;
import com.ensak.connect.utils.DateUtil;
import com.ensak.connect.presentation.profile.EducationEditActivity;

import java.util.ArrayList;
import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.EducationViewHolder> {

    private List<Education> educationList;
    private Context context;

    public interface OnEducationDeleteListener {
        void onEducationDelete(int educationId);
    }

    private OnEducationDeleteListener deleteListener;
    public void setOnEducationDeleteListener(OnEducationDeleteListener listener) {
        this.deleteListener = listener;
    }


    public EducationAdapter(Context context, List<Education> educationList) {
        this.context = context;
        this.educationList = educationList != null ? educationList : new ArrayList<>();
    }


    @NonNull
    @Override
    public EducationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_expereince_fragment, parent, false);
        return new EducationViewHolder(itemView, context, this);
    }


    @Override
    public void onBindViewHolder(@NonNull EducationViewHolder holder, int position) {
        Education education = educationList.get(position);
        holder.bind(education, context);
    }

    @Override
    public int getItemCount() {
        return educationList.size();
    }

    static class EducationViewHolder extends RecyclerView.ViewHolder {
        TextView fieldTextView, degreeTextView, schoolTextView,locationTextView, descriptionTextView,periodTextView;

        ImageView iconEdit, iconDelete;
        private OnEducationDeleteListener deleteListener;
        private List<Education> educationList;
        private final EducationAdapter adapter;




        EducationViewHolder(View itemView, Context context, EducationAdapter  educationAdapter){
            super(itemView);
            this.adapter = educationAdapter;
            fieldTextView = itemView.findViewById(R.id.experienceTitle);
            schoolTextView = itemView.findViewById(R.id.companyLocation);
//            locationTextView = itemView.findViewById(R.id.companyLocation);
            periodTextView = itemView.findViewById(R.id.experiencePeriod);
            descriptionTextView = itemView.findViewById(R.id.experienceDescription);
            iconEdit = itemView.findViewById(R.id.iconEdit);
            iconDelete = itemView.findViewById(R.id.iconDelete);
        }

        void bind(Education education, Context context) {
            if (education == null) {
                return;
            }
            fieldTextView.setText(education.getField());
            schoolTextView.setText(education.getSchool());
//            locationTextView.setText(education.getSchool());
            periodTextView.setText(DateUtil.formatPeriod(education.getStartDate(), education.getEndDate()));
            descriptionTextView.setText(education.getDescription());
            if (iconEdit != null) {
                iconEdit.setOnClickListener(v -> {
                    Intent intent = new Intent(context, EducationEditActivity.class);
                    intent.putExtra("education", String.valueOf(education.getId()));
                    intent.putExtra("field", fieldTextView.getText().toString());
                    intent.putExtra("school", schoolTextView.getText().toString());
//                    intent.putExtra("location", locationTextView.getText().toString());
                    intent.putExtra("startDate", education.getStartDate().toString());
                    intent.putExtra("endDate", education.getEndDate().toString());
                    intent.putExtra("description", descriptionTextView.getText().toString());
                    intent.putExtra("isUpdate", true);
                    context.startActivity(intent);
                });
            }

            if (iconDelete != null) {
                iconDelete.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && adapter.deleteListener != null) {
                        Education educationToDelete = adapter.educationList.get(position);
                        adapter.deleteListener.onEducationDelete(educationToDelete.getId());
                    }
                });
            }

                }
        }

}