package com.example.shonateachingassistant;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.shonateachingassistant.AESHelper.encrypt;

public class SignUp extends  AppCompatActivity {
    EditText name,surname,username,email,phone,password;
    MaterialButton signupbtn;
    String regex;
    //Compile regular expression to get the pattern
    Pattern pattern;
    FirebaseDatabase rootNode;
    DatePickerDialog datePickerDialog;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        pattern = Pattern.compile(regex);


        name = (EditText) findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password= findViewById(R.id.password);

        signupbtn = findViewById(R.id.signupbtn);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString().trim();
                String Surname = surname.getText().toString().trim();
                String Username = username.getText().toString().trim().toLowerCase();
                String Email = email.getText().toString().trim();
                String Phone = phone.getText().toString().trim();
                String Password = password.getText().toString().trim();
                Matcher matcher = pattern.matcher(Email);

                if(Name.isEmpty() || Surname.isEmpty() || Username.isEmpty() || Email.isEmpty() || Phone.isEmpty() || Password.isEmpty()){
                    Toast.makeText(SignUp.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{

                    if (matcher.matches()) {
                        if(Password.length()<=4){
                            Toast.makeText(SignUp.this, "Password Too Short, Use More than  5 characters", Toast.LENGTH_SHORT).show();
                        }else {
                            ProgressDialog dialog=new ProgressDialog(SignUp.this);
                            dialog.setMessage("Creating Account ...");
                            dialog.show();
                            rootNode = FirebaseDatabase.getInstance();
                            reference = rootNode.getReference("users1");

                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.child(Username).exists()) {
                                        AlertDialog builder=new AlertDialog.Builder(SignUp.this)
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                //set title
                                                .setTitle("Error")
                                                //set message
                                                .setMessage("Username already exists")
                                                //set positive button
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                })

                                                .show();
                                        dialog.dismiss();
                                    } else {
                                        User user = new User(Username, Name, Surname, Email, Phone, Password,"","");
                                        user.setPassword(encrypt(Password));
                                        reference.child(Username).setValue(user);
                                        dialog.dismiss();
                                        AlertDialog builder=new AlertDialog.Builder(SignUp.this)
                                                .setIcon(android.R.drawable.ic_menu_save)
                                                //set title
                                                .setTitle("Success")
                                                //set message
                                                .setMessage("Sign Up successful")
                                                //set positive button
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        //set what would happen when positive button is clicked

                                                        name.setText(null);
                                                        surname.setText(null);
                                                        username.setText(null);
                                                        email.setText(null);
                                                        phone.setText(null);
                                                        password.setText(null);
                                                        finish();
                                                    }
                                                })

                                                .show();


                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }
                    else{
                        Toast.makeText(SignUp.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}