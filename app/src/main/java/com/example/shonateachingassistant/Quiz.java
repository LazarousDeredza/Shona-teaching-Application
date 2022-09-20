package com.example.shonateachingassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Quiz extends AppCompatActivity {

   public static ArrayList<Modelclass> list;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);



        reference= FirebaseDatabase.getInstance().getReference("questions1");
        list =new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                   Modelclass modelclass=dataSnapshot.getValue(Modelclass.class);
                    list.add(modelclass);
                }

                if(list.size()==0){
                    AlertDialog builder=new AlertDialog.Builder(Quiz.this)

                            //set title
                            .setTitle("")
                            .setCancelable(false)
                            //set message
                            .setMessage("No Data Added Yet")
                            //set positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(Quiz.this,Home.class);
                                    startActivity(intent);

                                }
                            })

                            .show();
                }else {
                    Intent intent = new Intent(Quiz.this, DashboardActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              /*  Intent intent=new Intent(Quiz.this,DashboardActivity.class);
                startActivity(intent);*/
            }
        },1500);
    }
}