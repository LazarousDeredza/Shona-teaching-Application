package com.example.shonateachingassistant;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.shonateachingassistant.AESHelper.encrypt;

public class AddQuiz extends AppCompatActivity {

    EditText Question,ansA,ansB,ansC,ansD;
    String CorrectAnswer;
    TextView ic_exit;
    MaterialButton btnSave;
    RadioGroup radioCategoryGroup;
    RadioButton radioCategoryButton;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);

        ic_exit=findViewById(R.id.ic_exit);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AddQuiz.this, AdminHome.class);

                startActivity(intent);
            }
        });
        Question = (EditText) findViewById(R.id.Question);
        ansA = findViewById(R.id.Answer1);
        ansB = findViewById(R.id.Answer2);
        ansC = findViewById(R.id.Answer3);
        ansD = findViewById(R.id.Answer4);
        radioCategoryGroup=findViewById(R.id.radioCategoryGroup);

        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedCategory=radioCategoryGroup.getCheckedRadioButtonId();
                radioCategoryButton=(RadioButton)findViewById(selectedCategory);

                String question = Question.getText().toString().trim();
                String ansa = ansA.getText().toString().trim();
                String ansb = ansB.getText().toString().trim().toLowerCase();
                String ansc = ansC.getText().toString().trim();
                String ansd = ansD.getText().toString().trim();
                CorrectAnswer="";

                if(question.isEmpty() || ansa.isEmpty() || ansb.isEmpty() || ansc.isEmpty() || ansd.isEmpty()){
                    Toast.makeText(AddQuiz.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else if (radioCategoryGroup.getCheckedRadioButtonId()==-1) {
                    Toast.makeText(AddQuiz.this, "Please Pick a Correct Answer Position", Toast.LENGTH_SHORT).show();


                }else{

                    String asnwer=radioCategoryButton.getText().toString();
                    String Q=  question.replaceAll("[^a-zA-Z0-9\\s]","");
                    String a=ansa.replaceAll("[^a-zA-Z0-9\\s]","");
                    String b= ansb.replaceAll("[^a-zA-Z0-9\\s]","");
                    String c= ansc.replaceAll("[^a-zA-Z0-9\\s]","");
                    String d = ansd.replaceAll("[^a-zA-Z0-9\\s]","");

                    if(asnwer.equals("1")){
                        CorrectAnswer=ansa;
                    } else if (asnwer.equals("2")) {
                        CorrectAnswer=ansb;
                    } else if (asnwer.equals("3")) {
                        CorrectAnswer=ansc;
                    }else {
                        CorrectAnswer=ansd;
                    }

                    ProgressDialog dialog=new ProgressDialog(AddQuiz.this);
                            dialog.setMessage("Adding To Quiz Database ...");
                            dialog.setCancelable(false);
                            dialog.show();

                            reference = FirebaseDatabase.getInstance().getReference("questions1");

                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    questions quiz = new questions(Q, a, b, c, d, CorrectAnswer);

                                        reference.child(Q).setValue(quiz);
                                        dialog.dismiss();
                                        AlertDialog builder=new AlertDialog.Builder(AddQuiz.this)
                                                .setIcon(android.R.drawable.ic_menu_save)
                                                //set title
                                                .setTitle("Success")
                                                //set message
                                                .setMessage("Quiz successful")
                                                //set positive button
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        //set what would happen when positive button is clicked

                                                        Question.setText(null);
                                                        ansA.setText(null);
                                                        ansB.setText(null);
                                                        ansC.setText(null);
                                                        ansD.setText(null);
                                                        radioCategoryGroup.clearCheck();
                                                       // finish();
                                                    }
                                                })

                                                .show();


                                    }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }



            }

        });






    }
}