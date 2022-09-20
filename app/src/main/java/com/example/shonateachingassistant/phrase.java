package com.example.shonateachingassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.shonateachingassistant.LoadingPhrases.list;

public class phrase extends AppCompatActivity {
    PhraseClass phraseClass;
    MediaPlayer mediaPlayer;
    ArrayList<PhraseClass> allData;
    int index=0;
    LinearLayout nxtBtn;

    TextView card_optiona,card_optionb,txtAudioStatus,ic_exit;
    CardView CardOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase);
        ic_exit=findViewById(R.id.ic_exit);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(phrase.this, Home.class);

                startActivity(intent);
            }
        });

        nxtBtn=findViewById(R.id.nxtBtn);
        card_optiona=findViewById(R.id.card_optiona);
        card_optionb=findViewById(R.id.card_optionb);
        CardOB=findViewById(R.id.CardOB);
        txtAudioStatus=findViewById(R.id.txtAudioStatus);
        txtAudioStatus.setText(null);



        allData=list;
        Collections.shuffle(list);


        phraseClass= list.get(index);

        setAllData();
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index<list.size()-1){
                    index++;
                    phraseClass=list.get(index);
                    setAllData();

                }else{
                    txtAudioStatus.setText(null);
                    funFinish();

                }
            }
        });
    }

    private void funFinish() {
        mediaPlayer=new MediaPlayer();
        mediaPlayer.stop();
        AlertDialog builder=new AlertDialog.Builder(phrase.this)

                //set title
                .setTitle("Notice")
                //set message
                .setMessage("You have Reached The End")
                //set positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(phrase.this,Home.class);
                        startActivity(intent);

                    }
                })

                .show();
    }

    private void setAllData() {
        mediaPlayer=new MediaPlayer();
        mediaPlayer.stop();
        card_optiona.setText("English Phrase : " +phraseClass.getEnglish());
        card_optionb.setText("Shona  : " +phraseClass.getShona());
    }

    public void OptionBClick(View view) throws IOException {
        txtAudioStatus.setText("Loading Audio ..");
        CardOB.setBackgroundColor(getResources().getColor(R.color.lightGreen));
        funPlayAudio();
    }

    private void funPlayAudio() throws IOException {
        String song =phraseClass.getId();
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/shonateachingapp.appspot.com/o/Phrases1%2F"+song+".3gp?alt=media");

     /*   https://console.firebase.google.com/u/1/project/shonateachingapp/storage/shonateachingapp.appspot.com/files/~2FAudio

        https://firebasestorage.googleapis.com/v0/b/shonateachingapp.appspot.com/o/Audio%2Fbench.3gp?alt=media*/

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                txtAudioStatus.setText("Playing");
            }
        });
        mediaPlayer.prepare();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                txtAudioStatus.setText("Audio Stopped");

                CardOB.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

    }

}