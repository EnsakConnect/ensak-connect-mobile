package com.ensak.connect.presentation.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.adapters.Profile.AddSkillsAdapter;
import com.ensak.connect.adapters.Profile.SkillsAdapter;
import com.ensak.connect.databinding.ActivitySkillsEditBinding;
import com.ensak.connect.model.Skill;
import com.ensak.connect.repository.profile.model.SkillResponse;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class SkillsEditActivity extends AppCompatActivity {

    private ActivitySkillsEditBinding binding;
    private AddSkillsAdapter adapter;

    private SkillEditViewModel skillEditViewModel;
    private List<Skill> skillsList = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySkillsEditBinding.inflate(getLayoutInflater());
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



        adapter = new AddSkillsAdapter(skillsList);
        binding.skillsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.skillsRecyclerView.setAdapter(adapter);



        initViewModel();
        skillEditViewModel.fetchSkills();
        skillEditViewModel.getSkills().observe(this, skills -> {

            skillsList.clear();
            skillsList.addAll(skills);
            adapter.notifyDataSetChanged();
        });

        adapter.setOnSkillDeleteListener(position -> {

            Skill skillToDelete = skillsList.get(position);
            skillEditViewModel.deleteSkill(skillToDelete.getid());
            skillsList.remove(position);
            adapter.notifyItemRemoved(position);

        });

        binding.txtSkill.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String skillName = binding.txtSkill.getText().toString();
                if (!skillName.isEmpty()) {
                    addSkillToLinearLayout(skillName);
                    createSkill();
                    binding.txtSkill.setText(""); // Clear EditText
                }
                return true;
            }
            return false;
        });


        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });


        binding.btnCreate.setOnClickListener(v -> {
            createSkill();
            addSkillIfNotEmpty();
        });


    }


    private void addSkillIfNotEmpty() {
        String skillName = binding.txtSkill.getText().toString();
        if (!skillName.isEmpty()) {
            addSkillToLinearLayout(skillName);
            binding.txtSkill.setText(""); // Clear EditText

        }
    }
    private void addSkillToLinearLayout(String skillName) {

        View skillRow = LayoutInflater.from(this).inflate(R.layout.skill_item_layout, null, false);


        TextView skillTextView = skillRow.findViewById(R.id.txtTitle);
        skillTextView.setText(skillName);

        ImageView deleteIcon = skillRow.findViewById(R.id.iconDelete);
        deleteIcon.setOnClickListener(v -> {
            ((LinearLayout)skillRow.getParent()).removeView(skillRow);

        });


        binding.linearLayout.addView(skillRow);
    }



    private void createSkill() {


        String skill = binding.txtSkill.getText().toString().trim();
        String level = "BEGINNER";
        skillEditViewModel.createSkill(skill, level);

    }

    private void initViewModel() {
        skillEditViewModel = new ViewModelProvider(this)
                .get(SkillEditViewModel.class);

        skillEditViewModel.getIsSuccess().observe(this, success -> {
            if(success) {
                finish();
            }
        });

        skillEditViewModel.getSuccessMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        skillEditViewModel.getErrorMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        });
    }

}