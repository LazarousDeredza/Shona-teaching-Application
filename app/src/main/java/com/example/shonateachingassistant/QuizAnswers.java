package com.example.shonateachingassistant;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import java.util.ArrayList;

import static com.example.shonateachingassistant.Quiz.list;
public class QuizAnswers extends AppCompatActivity {
    ArrayList<Modelclass> allQuestionsList;
    Modelclass modelclass;
    int index=0;
    TextView txtAnswer,ic_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_answers);
        ic_exit=findViewById(R.id.ic_exit);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QuizAnswers.this, Home.class);
                startActivity(intent);
            }
        });
        txtAnswer=findViewById(R.id.txtAnswer);
        allQuestionsList=list;
        String ans="";
        for (Modelclass modelclass:list){
            ans+="\nQuestion:\n"+modelclass.getQuestion().toString()+"\n Answer : \n"+modelclass.getAns().toString();
        }
        txtAnswer.setText(ans);

    }
}