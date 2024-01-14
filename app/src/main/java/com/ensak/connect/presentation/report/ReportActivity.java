package com.ensak.connect.presentation.report;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ReportActivityBinding;
import com.ensak.connect.repository.report.ReportRequest;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReportActivity extends AppCompatActivity {

    final private String POST_ID = "postId";
    final private String POST_TYPE = "postType";
    private String postType;
    private int postId;
    private ReportActivityBinding binding;
    private ReportViewModel reportViewModel;
    private String flag;

    private void setPostType(String intentPostType){
        postType = intentPostType;
        if(intentPostType.equals("DOCTORATE") || intentPostType.equals("CDI") || intentPostType.equals("PFE"))
            postType = "JOB";
        if(intentPostType.equals("Q&A"))
            postType = "QUESTION";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.report_activity);
        binding = ReportActivityBinding.inflate(getLayoutInflater());
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

        if(getIntent() != null && getIntent().getExtras() != null){
            setPostType(getIntent().getStringExtra(POST_TYPE));
            postId = getIntent().getIntExtra(POST_ID,0);
        }

        initView();
        initViewModel();
    }

    private void initView(){
        binding.radioGroup.check(binding.radioOption1.getId());
        binding.radioGroup.setOnCheckedChangeListener((group,checkedId) -> {
            RadioButton radioButton =(RadioButton) findViewById(checkedId);
            flag = radioButton.getText().toString();
            Log.i("DEBUG","TEXT : "+ radioButton.getText().toString());
        });

        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });

        binding.btnSend.setOnClickListener(v-> {
            sendReport();
            Log.i("DEBUG","SENDING THE REPORT :"+Integer.toString(postId)+","+postType+","+flag);
        });
    }

    private void initViewModel(){
        reportViewModel = new ViewModelProvider(this)
                .get(ReportViewModel.class);

        reportViewModel.getIsSuccess().observe(this,success -> {
            if(success){
                Toast.makeText(this, "Report Sent", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        reportViewModel.getErrorMessage().observe(this, msg -> {
            if(msg.length() == 0) return;
            Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        });
    }

    private void sendReport(){
        ReportRequest request = new ReportRequest(postType,postId,flag);
        reportViewModel.SendReport(request);
    }
}