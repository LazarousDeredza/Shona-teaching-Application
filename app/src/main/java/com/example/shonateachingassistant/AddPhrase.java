package com.example.shonateachingassistant;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import de.hdodenhof.circleimageview.CircleImageView;

import java.io.File;
import java.io.IOException;

public class AddPhrase extends AppCompatActivity {
    EditText txtEnglish,txtShona;

    int totalRecords=0;
    Button btnSave,btnRecord;
    String fileName;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    boolean Record,recording=false;
    MediaRecorder recorder;
    TextView txtRecordingStatus;
    DatabaseReference reference;
    StorageReference mStorage;
    boolean isRecoding=false;
    private static final String LOG_TAG = "record_log";
    private ProgressDialog mprogress;
    boolean uploaded;
TextView ic_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phrase);

        txtEnglish=findViewById(R.id.txtEnglish);
        txtShona=findViewById(R.id.txtShona);
        btnSave=findViewById(R.id.btnSave);
        btnRecord=findViewById(R.id.btnRecord);
        txtRecordingStatus=findViewById(R.id.txtRecordingStatus);


        ic_exit=findViewById(R.id.ic_exit);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AddPhrase.this, AdminHome.class);

                startActivity(intent);
            }
        });
        reference= FirebaseDatabase.getInstance().getReference("phrases1");

        mprogress=new ProgressDialog(this);

        mStorage= FirebaseStorage.getInstance().getReference();

        String English= txtEnglish.getText().toString().trim();
        String Shona= txtShona.getText().toString().trim();

/*        btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(isRecoding){

                        stopRecording();
                        txtRecordingStatus.setVisibility(View.VISIBLE);
                        Record=true;
                        txtRecordingStatus.setText("Recording Stopped");
                        isRecoding=false;
                    }
                }else if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    String engFileName=txtEnglish.getText().toString().trim().toLowerCase();
                    String shonaFileName=txtShona.getText().toString().trim().toLowerCase();
                    if(engFileName.isEmpty()||shonaFileName.isEmpty() ){
                        Toast.makeText(AddPhrase.this, "English and Shona Phrases are reqiured before you record ", Toast.LENGTH_SHORT).show();
                    }else{

                        if(checkPermissions()){
                            startRecording();
                            txtRecordingStatus.setVisibility(View.VISIBLE);
                            txtRecordingStatus.setText("Recording ");
                            isRecoding=true;

                        }
                    }
                }
                return false;
            }
        });*/


        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                   if(btnRecord.getText().toString().equals("Record")){

                       String engFileName=txtEnglish.getText().toString().trim().toLowerCase();
                       String shonaFileName=txtShona.getText().toString().trim().toLowerCase();
                       if(engFileName.isEmpty()||shonaFileName.isEmpty() ){
                           Toast.makeText(AddPhrase.this, "English and Shona Phrases are reqiured before you record ", Toast.LENGTH_SHORT).show();
                       }else{
                           //Start recording
                           if(checkPermissions()){
                                   startRecording();
                           }
                       }
                   }else{
                       //Stop Recording
                       recording=false;
                       stopRecording();
                       txtRecordingStatus.setVisibility(View.VISIBLE);
                       Record=true;
                       txtRecordingStatus.setText("Recording Stopped");
                       isRecoding=false;
                       btnRecord.setText("Record");
                   }

            }
        });
       


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String EnglishName=txtEnglish.getText().toString().trim();
                String ShonaName=txtShona.getText().toString().trim();

                if(EnglishName.isEmpty()){
                    Toast.makeText(AddPhrase.this, "English Phrase is Missing", Toast.LENGTH_SHORT).show();
                }else if(ShonaName.isEmpty()){
                    Toast.makeText(AddPhrase.this, "Shona Phrase is missing", Toast.LENGTH_SHORT).show();

                }else if(!Record){
                    Toast.makeText(AddPhrase.this, "Please Upload Audio", Toast.LENGTH_SHORT).show();
                }else {
                    upladAudio();
                    mprogress.setMessage("Saving Data");
                    mprogress.setCancelable(false);
                    mprogress.show();

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            PhraseClass obj = new PhraseClass(fileName,EnglishName,ShonaName);

                            reference.child(fileName).setValue(obj);
                            mprogress.dismiss();
                            AlertDialog builder=new AlertDialog.Builder(AddPhrase.this)
                                    .setIcon(android.R.drawable.ic_menu_save)
                                    //set title
                                    .setTitle("Success")
                                    //set message
                                    .setMessage("Phrase added successfully ")
                                    //set positive button
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //set what would happen when positive button is clicked


                                            txtEnglish.setText(null);
                                            txtShona.setText(null);
                                            Record=false;
                                            txtRecordingStatus.setText("");
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




    private void startRecording() {

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalRecords =Integer.parseInt(String.valueOf(snapshot.getChildrenCount()))+1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        fileName="phrase"+totalRecords;

        String filePath=getExternalFilesDir("/").getAbsolutePath();

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(filePath+"/"+fileName+".3gp");
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {

                assert recorder != null;
                recorder.prepare();
                recorder.start();
                btnRecord.setText("Stop Recording");
                txtRecordingStatus.setVisibility(View.VISIBLE);
                txtRecordingStatus.setText("Recording ");
                isRecoding = true;
                recording = true;

        } catch (Exception e) {
            recorder.release();
            recorder = null;
            Log.e(LOG_TAG, "prepare() failed");
            Toast.makeText(AddPhrase.this, "Recording failed\n There is a problem with your microphone\n Try restarting your device", Toast.LENGTH_LONG).show();

        }



    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        Toast.makeText(this, "Recording Finished", Toast.LENGTH_SHORT).show();
        Record=true;

    }

    private void upladAudio() {

        mprogress.setMessage("Uploading Audio to Database");
        mprogress.show();


        String Path=getExternalFilesDir("/").getAbsolutePath();

        StorageReference filepath=mStorage.child("Phrases1").child(fileName+".3gp");
        Uri uri=Uri.fromFile(new File(Path+"/"+fileName+".3gp"));
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mprogress.dismiss();
                txtRecordingStatus.setText("Audio Uploaded");
                uploaded=true;

            }
        });

    }


    private boolean checkPermissions() {

        if(ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            ActivityCompat.requestPermissions(AddPhrase.this,permissions,200);
            return false;
        }


    }
}