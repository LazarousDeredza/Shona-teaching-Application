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

public class LoadingSpelling extends AppCompatActivity {
    DatabaseReference reference;
    TextView txtObject;
    public static ArrayList<SpellingsClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_spelling);

        list =new ArrayList<>();


        reference= FirebaseDatabase.getInstance().getReference("spellings1").child("objects");
        reference.child("Animal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                   SpellingsClass spellingsClass=new SpellingsClass();
                   spellingsClass.setEnglish(dataSnapshot.child("engName").getValue().toString());
                   spellingsClass.setShona(dataSnapshot.child("shonaName").getValue().toString());

                    list.add(spellingsClass);
                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        reference.child("Fruit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    SpellingsClass spellingsClass=new SpellingsClass();
                    spellingsClass.setEnglish(dataSnapshot.child("engName").getValue().toString());
                    spellingsClass.setShona(dataSnapshot.child("shonaName").getValue().toString());

                    list.add(spellingsClass);
                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        reference.child("Furniture").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    SpellingsClass spellingsClass=new SpellingsClass();
                    spellingsClass.setEnglish(dataSnapshot.child("engName").getValue().toString());
                    spellingsClass.setShona(dataSnapshot.child("shonaName").getValue().toString());

                    list.add(spellingsClass);
                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        reference.child("Other").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    SpellingsClass spellingsClass=new SpellingsClass();
                    spellingsClass.setEnglish(dataSnapshot.child("engName").getValue().toString());
                    spellingsClass.setShona(dataSnapshot.child("shonaName").getValue().toString());

                    list.add(spellingsClass);
                }
                if(list.size()==0){
                    AlertDialog builder=new AlertDialog.Builder(LoadingSpelling.this)

                            //set title
                            .setTitle("")
                            .setCancelable(false)
                            //set message
                            .setMessage("No Data Added Yet")
                            //set positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(LoadingSpelling.this,Home.class);
                                    startActivity(intent);

                                }
                            })

                            .show();
                }else {

                Intent intent= new Intent(LoadingSpelling.this,Spelling.class);
                startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });





    }
}