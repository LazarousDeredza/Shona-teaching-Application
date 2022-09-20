package com.example.shonateachingassistant;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

public class OptionObjects extends AppCompatActivity {

    DatabaseReference reference;
    TextView ic_exit;
    Button btnAnimals,btnFruits,btnFurniture,btnOther;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_objects);

        btnAnimals=findViewById(R.id.btnAnimals);
        btnFruits=findViewById(R.id.btnFruits);
        btnFurniture=findViewById(R.id.btnFurniture);
        btnOther=findViewById(R.id.btnOther);

        ic_exit=findViewById(R.id.ic_exit);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(OptionObjects.this, Home.class);

                startActivity(intent);
            }
        });
        btnAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(OptionObjects.this,LoadingObjects.class);
            intent.putExtra("objectClass","Animal");
            startActivity(intent);
            }
        });

        btnFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OptionObjects.this,LoadingObjects.class);
                intent.putExtra("objectClass","Fruit");
                startActivity(intent);
            }
        });

        btnFurniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OptionObjects.this,LoadingObjects.class);
                intent.putExtra("objectClass","Furniture");
                startActivity(intent);
            }
        });


        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OptionObjects.this,LoadingObjects.class);
                intent.putExtra("objectClass","Other");
                startActivity(intent);
            }
        });



       /* reference= FirebaseDatabase.getInstance().getReference("spellings").child("objects").child("Animal");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Toast.makeText(OptionObjects.this, String.valueOf(snapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
*/



    }
}