package com.example.shonateachingassistant;

import android.content.Intent;
import android.service.quickaccesswallet.QuickAccessWalletService;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class WonActivity extends AppCompatActivity {

    CircularProgressBar circularProgressBar;
    int correct,wrong;
    TextView resultText,ic_exit;
    LinearLayout btnShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);

        circularProgressBar=findViewById(R.id.circularProgressBar);
        resultText=findViewById(R.id.resultText);

        correct=getIntent().getIntExtra("correct",0);
        wrong=getIntent().getIntExtra("wrong",0);

        String total=String.valueOf(correct+wrong);
        circularProgressBar.setProgress(correct);
        circularProgressBar.setProgressMax(Integer.parseInt(total));
        resultText.setText(correct +"/"+total );
        ic_exit=findViewById(R.id.ic_exit);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WonActivity.this, Home.class);
                startActivity(intent);
            }
        });

        btnShare=findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WonActivity.this, QuizAnswers.class);
                startActivity(intent);
            }
        });

    }
}