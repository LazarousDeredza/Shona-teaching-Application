package com.example.shonateachingassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.room.Database;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Home extends AppCompatActivity {

    TextView ic_exit;
    Button btnQuiz,btnSpelling,btnObjects,btnPhrases,btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
          Objects.requireNonNull(getSupportActionBar()).setTitle("English-Shona Assistant");



        btnObjects=findViewById(R.id.btnObjects);
        btnQuiz=findViewById(R.id.btnQuiz);
        btnSpelling=findViewById(R.id.btnSpelling);
        btnPhrases=findViewById(R.id.btnPhrases);

       // reference= FirebaseDatabase.getInstance().getReference("questions");


        ic_exit=findViewById(R.id.ic_exit);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Home.this, MainActivity.class);

                startActivity(intent);
            }
        });

/*

        Bundle extras=getIntent().getExtras();
        String data=extras.getString("data");

        AlertDialog builder=new AlertDialog.Builder(Home.this)
                .setIcon(android.R.drawable.ic_menu_save)
                //set title
                .setTitle("Loaded")
                .setCancelable(false)
                //set message
                .setMessage(data)
                //set positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked

                    }
                })

                .show();
*/


        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Quiz.class);
                startActivity(intent);
            }
        });
        btnObjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,OptionObjects.class);
                startActivity(intent);
            }
        });

        btnPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,LoadingPhrases.class);
                startActivity(intent);
            }
        });




        btnSpelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,LoadingSpelling.class);
                startActivity(intent);
            }
        });

    }
}