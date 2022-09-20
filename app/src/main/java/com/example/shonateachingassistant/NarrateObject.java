package com.example.shonateachingassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


import static com.example.shonateachingassistant.LoadingObjects.list;


public class NarrateObject extends AppCompatActivity {
    ObjectClass objectClass;
    MediaPlayer mediaPlayer;
     ArrayList<ObjectClass> allData;
    int index=0;
    LinearLayout nxtBtn;

    TextView card_optiona,card_optionb,txtAudioStatus,ic_exit;
    CardView CardOB;
    ImageView imgObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_narrate_object);
        ic_exit=findViewById(R.id.ic_exit);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(NarrateObject.this, Home.class);

                startActivity(intent);
            }
        });
        nxtBtn=findViewById(R.id.nxtBtn);
        card_optiona=findViewById(R.id.card_optiona);
        card_optionb=findViewById(R.id.card_optionb);
        CardOB=findViewById(R.id.CardOB);
        imgObject=findViewById(R.id.imgObject);
        txtAudioStatus=findViewById(R.id.txtAudioStatus);
        txtAudioStatus.setText(null);

        Bundle extras=getIntent().getExtras();
        String objClass=extras.getString("objectClass");
       // Toast.makeText(this, objClass, Toast.LENGTH_LONG).show();




        allData=list;
        Collections.shuffle(list);


        objectClass= list.get(index);

        setAllData();
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index<list.size()-1){
                    index++;
                    objectClass=list.get(index);
                    setAllData();

                }else{
                    txtAudioStatus.setText(null);
                   funFinish();

                }
            }
        });

    }



    private void setAllData() {
        mediaPlayer=new MediaPlayer();
        mediaPlayer.stop();
        card_optiona.setText("English : " +objectClass.getEngName().replaceAll("_"," "));
        card_optionb.setText("Shona  : " +objectClass.getShonaName());


        byte [] bytes= Base64.decode(objectClass.getImg(),Base64.DEFAULT);

        //Initialize bitmap
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        //Set bitmap on image view
        imgObject.setImageBitmap(bitmap);
    }


    private void funFinish() {
        mediaPlayer=new MediaPlayer();
        mediaPlayer.stop();
        AlertDialog builder=new AlertDialog.Builder(NarrateObject.this)

                //set title
                .setTitle("Notice")
                //set message
                .setMessage("You have Reached The End")
                //set positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(NarrateObject.this,OptionObjects.class);
                        startActivity(intent);

                    }
                })

                .show();

    }

    public void OptionBClick(View view) throws IOException {

        txtAudioStatus.setText("Loading Audio ..");
        CardOB.setBackgroundColor(getResources().getColor(R.color.lightGreen));
       funPlayAudio();

    }

    //TO-DO
    private void funPlayAudio() throws IOException {
        String song =objectClass.getEngName();
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/shonateachingapp.appspot.com/o/Audio1%2F"+song+".3gp?alt=media");

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