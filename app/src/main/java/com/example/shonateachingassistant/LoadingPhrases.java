package com.example.shonateachingassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LoadingPhrases extends AppCompatActivity {
    DatabaseReference reference;
    TextView txtObject;
    public static ArrayList<PhraseClass> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_phrases);

        list =new ArrayList<>();


        reference= FirebaseDatabase.getInstance().getReference("phrases1");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    PhraseClass phr=dataSnapshot.getValue(PhraseClass.class);
                    list.add(phr);
                }

                if(list.size()==0){
                    AlertDialog builder=new AlertDialog.Builder(LoadingPhrases.this)

                            //set title
                            .setTitle("")
                            .setCancelable(false)
                            //set message
                            .setMessage("No Data Added Yet")
                            //set positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(LoadingPhrases.this,Home.class);
                                    startActivity(intent);

                                }
                            })

                            .show();
                }else {

                    Intent intent = new Intent(LoadingPhrases.this, phrase.class);
                    startActivity(intent);
                }
                //  Toast.makeText(LoadingObjects.this, String.valueOf(list.size()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}