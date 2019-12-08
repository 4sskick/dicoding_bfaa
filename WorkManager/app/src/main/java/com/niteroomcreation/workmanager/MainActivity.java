package com.niteroomcreation.workmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.niteroomcreation.workmanager.utils.WorkerUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnOneTimeTask;
    EditText editCity;
    TextView textStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOneTimeTask = findViewById(R.id.btnOneTimeTask);
        editCity = findViewById(R.id.editCity);
        textStatus = findViewById(R.id.textStatus);

        btnOneTimeTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOneTimeTask:
                startOneTimeTask();
                break;
        }
    }

    private void startOneTimeTask() {
        textStatus.setText(getString(R.string.status));
        Data data = new Data.Builder()
                .putString(WorkerUtils.EXTRA_CITY, editCity.getText().toString())
                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkerUtils.class)
                .setInputData(data)
                .build();

        WorkManager.getInstance()
                .enqueue(oneTimeWorkRequest);
        WorkManager.getInstance()
                .getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(MainActivity.this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        String status = workInfo.getState().name();
                        textStatus.setText("\n" + status);
                    }
                });
    }
}
