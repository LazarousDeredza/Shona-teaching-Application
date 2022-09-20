package com.example.shonateachingassistant;

/*import android.app.Dialog;*/

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.shonateachingassistant.Quiz.list;

public class DashboardActivity extends AppCompatActivity {


    CountDownTimer countDownTimer;
    ProgressBar progressBar;
    ArrayList<Modelclass> allQuestionsList;
    Modelclass modelclass;
    int index=0;
    int timerValue  = 20;
    LinearLayout nxtBtn;

    TextView card_question,optiona,optionb,optionc,optiond,ic_exit;
    CardView cardOA, cardOB, cardOC, cardOD;

    int correctCount=0;
    int wrongCount=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ic_exit=findViewById(R.id.ic_exit);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(DashboardActivity.this, Home.class);

                startActivity(intent);
            }
        });

         Hooks();


        allQuestionsList= list;

        Collections.shuffle(allQuestionsList);
        modelclass= list.get(index);

        cardOA.setBackgroundColor(getResources().getColor(R.color.white));
        cardOB.setBackgroundColor(getResources().getColor(R.color.white));
        cardOC.setBackgroundColor(getResources().getColor(R.color.white));
        cardOD.setBackgroundColor(getResources().getColor(R.color.white));

        nxtBtn.setClickable(false);
        countDownTimer=new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long l) {
                timerValue-=1;
                progressBar.setProgress(timerValue);
            }

            @Override
            public void onFinish() {
                Dialog dialog=new Dialog(DashboardActivity.this)/*,R.style.Dialoge)*/;
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setContentView(R.layout.time_out_dialog);
                dialog.setCancelable(false);
                dialog.findViewById(R.id.btn_tryagain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent(DashboardActivity.this,Quiz.class);
                        startActivity(intent);
                    }
                });
                dialog.show();


            }
        }.start();

        setAllData();
    }

    private void setAllData() {

        card_question.setText(modelclass.getQuestion());

        optiona.setText(modelclass.getoA());
        optionb.setText(modelclass.getoB());
        optionc.setText(modelclass.getoC());
        optiond.setText(modelclass.getoD());

        timerValue=20;
        enableButton();
        nxtBtn.setClickable(false);
        countDownTimer.cancel();
        countDownTimer.start();

    }

    private void Hooks() {
        progressBar=findViewById(R.id.quiz_timer);


        card_question=findViewById(R.id.card_question);
        optiona=findViewById(R.id.card_optiona);
        optionb=findViewById(R.id.card_optionb);
        optionc=findViewById(R.id.card_optionc);
        optiond=findViewById(R.id.card_optiond);



        cardOA=findViewById(R.id.CardOA);
        cardOB=findViewById(R.id.CardOB);
        cardOC=findViewById(R.id.CardOC);
        cardOD=findViewById(R.id.CardOD);


        nxtBtn=findViewById(R.id.nxtBtn);

    }


    public void Correct(CardView cardView){
        cardView.setBackgroundColor(getResources().getColor(R.color.lightGreen));
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctCount++;
                if (index<list.size()-1){
                    index++;
                    modelclass=list.get(index);
                    setAllData();
                    resetColor();
                }else {

                    GameWon();

                }
            }
        });



    }

    public void Wrong(CardView cardView){

        cardView.setBackgroundColor(getResources().getColor(R.color.lightRed));

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongCount++;
                if (index<list.size()-1){
                    index++;
                    modelclass=list.get(index);
                    setAllData();
                    resetColor();
                }else{

                    GameWon();

                }
            }
        });


    }

    private void GameWon() {
        Intent intent=new Intent(DashboardActivity.this,WonActivity.class);
        intent.putExtra("correct",correctCount);
        intent.putExtra("wrong",wrongCount);
        startActivity(intent);
    }


    public void enableButton(){
        cardOA.setEnabled(true);
        cardOB.setEnabled(true);
        cardOC.setEnabled(true);
        cardOD.setEnabled(true);
    }

    public void disableButton(){
        cardOA.setEnabled(false);
        cardOB.setEnabled(false);
        cardOC.setEnabled(false);
        cardOD.setEnabled(false);
    }


    public void resetColor(){
        cardOA.setBackgroundColor(getResources().getColor(R.color.white));
        cardOB.setBackgroundColor(getResources().getColor(R.color.white));
        cardOC.setBackgroundColor(getResources().getColor(R.color.white));
        cardOD.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void OptionAClick(View view) {
        nxtBtn.setClickable(true);
        disableButton();

        if(modelclass.getoA().equals(modelclass.getAns())){
            cardOA.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            if(index<list.size()-1){

                Correct(cardOA);
            }else{
                correctCount++;
                GameWon();
            }

        }else{
            Wrong(cardOA);
        }
    }

    public void OptionBClick(View view) {

        nxtBtn.setClickable(true);
        disableButton();

        if(modelclass.getoB().equals(modelclass.getAns())){
            cardOB.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            if(index<list.size()-1){
                Correct(cardOB);
            }else{
                correctCount++;
                GameWon();
            }

        }else{
            Wrong(cardOB);
        }
    }

    public void OptionCClick(View view) {

        nxtBtn.setClickable(true);
        disableButton();

        if(modelclass.getoC().equals(modelclass.getAns())){
            cardOC.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            if(index<list.size()-1){
                Correct(cardOC);
            }else{
                correctCount++;
                GameWon();
            }

        }else{
            Wrong(cardOC);
        }
    }

    public void OptionDClick(View view) {

        nxtBtn.setClickable(true);
        disableButton();


        if(modelclass.getoD().equals(modelclass.getAns())){
            cardOD.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            if(index<list.size()-1){

                Correct(cardOD);

            }else{
                correctCount++;
                GameWon();
            }

        }else{
            Wrong(cardOD);
        }
    }
}