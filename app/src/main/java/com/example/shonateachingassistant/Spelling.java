package com.example.shonateachingassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.shonateachingassistant.LoadingSpelling.list;
public class Spelling extends AppCompatActivity {
    SpellingsClass spellingsClass;

    ArrayList<SpellingsClass> allData;
    int index=0;
    LinearLayout nxtBtn;

    TextView card_optiona,card_optionb,ic_exit;
    CardView CardOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling);


        nxtBtn=findViewById(R.id.nxtBtn);
        card_optiona=findViewById(R.id.card_optiona);
        card_optionb=findViewById(R.id.card_optionb);
        CardOB=findViewById(R.id.CardOB);
        ic_exit=findViewById(R.id.ic_exit);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Spelling.this, Home.class);
                startActivity(intent);
            }
        });

        Toast.makeText(this,"Loaded",Toast.LENGTH_LONG).show();

        allData= list;
        Collections.shuffle(allData);


        spellingsClass= list.get(index);

        setAllData();
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index< list.size()-1){
                    index++;
                    spellingsClass= list.get(index);
                    setAllData();

                }else{

                    funFinish();

                }
            }
        });
    }

    private void funFinish() {
        AlertDialog builder=new AlertDialog.Builder(Spelling.this)

                //set title
                .setTitle("Notice")
                //set message
                .setMessage("You have Reached The End Of Our Spellings")
                //set positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(Spelling.this,Home.class);
                        startActivity(intent);

                    }
                })

                .show();
    }

    private void setAllData() {
        card_optiona.setText("English  : " +spellingsClass.getEnglish());
        card_optionb.setText("Shona  : " +spellingsClass.getShona());
    }
}