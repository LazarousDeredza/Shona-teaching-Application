package com.example.shonateachingassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LoadingObjects extends AppCompatActivity {
    DatabaseReference reference;
    TextView txtObject;
    public static ArrayList<ObjectClass> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_objects);
        Bundle extras=getIntent().getExtras();
        String objClass=extras.getString("objectClass");
        txtObject=findViewById(R.id.txtObject);
        txtObject.setText(txtObject.getText().toString()+ ' '+objClass+"s");
        list =new ArrayList<>();


        reference= FirebaseDatabase.getInstance().getReference("spellings1").child("objects").child(objClass);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ObjectClass obj=dataSnapshot.getValue(ObjectClass.class);
                    list.add(obj);
                }
                if(list.size()==0){
                    AlertDialog builder=new AlertDialog.Builder(LoadingObjects.this)

                            //set title
                            .setTitle("")
                            .setCancelable(false)
                            //set message
                            .setMessage("No Data Added Yet")
                            //set positive button
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(LoadingObjects.this,Home.class);
                                    startActivity(intent);

                                }
                            })

                            .show();
                }else {
                    Intent intent = new Intent(LoadingObjects.this, NarrateObject.class);
                    intent.putExtra("objectClass", objClass);
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