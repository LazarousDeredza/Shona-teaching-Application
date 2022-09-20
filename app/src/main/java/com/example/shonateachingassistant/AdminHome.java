package com.example.shonateachingassistant;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class AdminHome extends AppCompatActivity {
    Button btnObjects,btnPhrases,btnAddQuiz;
    TextView ic_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        ic_exit=findViewById(R.id.ic_exit);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AdminHome.this, MainActivity.class);

                startActivity(intent);
            }
        });

        btnObjects=findViewById(R.id.btnObjects);

        btnPhrases=findViewById(R.id.btnPhrases);
        btnAddQuiz=findViewById(R.id.btnAddQuiz);

        btnPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHome.this,AddPhrase.class);
                startActivity(intent);
            }
        });

        btnObjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHome.this,AddObject.class);
                startActivity(intent);
            }
        });

        btnAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHome.this,AddQuiz.class);
                startActivity(intent);
            }
        });
    }


}