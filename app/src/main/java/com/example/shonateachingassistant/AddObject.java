package com.example.shonateachingassistant;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class AddObject extends AppCompatActivity {
    private StorageReference mStorage;
    private String Ctgry;
    private static final String LOG_TAG = "record_log";
    private String recordPermission =Manifest.permission.RECORD_AUDIO ;
    private static final int REQUEST_OPEN_IMAGE_SELECT_DIALOGUE= 100;

    // Requesting permission to RECORD_AUDIO

    private boolean permissionToRecordAccepted = false;
    private boolean permissionToOenFilesAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private boolean Record=false;

    private MediaRecorder recorder;
    String fileName=null;
    EditText txtEnglishName,txtShonaName;
    boolean isRecoding=false;
    private ProgressDialog mprogress;
    DatabaseReference reference;
    RadioGroup radioCategoryGroup;
    RadioButton radioCategoryButton;
    ImageView imgProduct;
    Button btnUploadImage,btnRecord,btnSave;
    TextView txtRecordingStatus,ic_exit;
    boolean uploaded;

    String sImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);
        ic_exit=findViewById(R.id.ic_exit);
        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AddObject.this, AdminHome.class);

                startActivity(intent);
            }
        });
        btnUploadImage=findViewById(R.id.btnUploadImage);
        imgProduct=findViewById(R.id.imgProduct);
        btnRecord=findViewById(R.id.btnRecord);
        btnSave=findViewById(R.id.btnSave);
        txtRecordingStatus=findViewById(R.id.txtRecordingStatus);
        radioCategoryGroup=findViewById(R.id.radioCategoryGroup);

        uploaded=false;
        sImage="";
        Ctgry="";
        txtEnglishName=findViewById(R.id.txtEnglishName);
        txtShonaName=findViewById(R.id.txtShonaName);

        reference= FirebaseDatabase.getInstance().getReference("spellings1");

        mprogress=new ProgressDialog(this);

        mStorage= FirebaseStorage.getInstance().getReference();


      /*  btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EnglishName=txtEnglishName.getText().toString().trim().toLowerCase();
                if(EnglishName.isEmpty()){
                    Toast.makeText(AddObject.this, "Object English Name is Required Before your record", Toast.LENGTH_SHORT).show();
                }else{
                    if(EnglishName.contains(" ")){
                        Toast.makeText(AddObject.this, "Object English Name is should only be one word e.g banana ,orange etc", Toast.LENGTH_SHORT).show();
                    }else{
                        fileName = getExternalCacheDir().getAbsolutePath();
                        fileName += "/"+fileName+".3gp";

                         String btnText=btnRecord.getText().toString();


                        if(){}
                        }else{


                            if(btnText.isEmpty()){
                                startRecording();
                                txtRecordingStatus.setVisibility(View.VISIBLE);
                                txtRecordingStatus.setText("Recording");
                                btnRecord.setText(".");

                            }else {
                                stopRecording();
                                txtRecordingStatus.setText("Recording Finished");
                                btnRecord.setText("");
                            }

                        }





                    }
                }


            }
        });
*/

    /*    btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                String EnglishName=txtEnglishName.getText().toString().trim().toLowerCase();
                if(EnglishName.isEmpty()){
                    Toast.makeText(AddObject.this, "Object English Name is Required Before your record", Toast.LENGTH_SHORT).show();
                }else{
                    if(EnglishName.contains(" ")){
                        Toast.makeText(AddObject.this, "Object English Name is should only be one word e.g banana ,orange etc", Toast.LENGTH_SHORT).show();
                    }else{
                        fileName = getExternalCacheDir().getAbsolutePath();
                        fileName += "/"+fileName+".3gp";



                        if(ContextCompat.checkSelfPermission(AddObject.this,
                                Manifest.permission.RECORD_AUDIO)
                                != PackageManager.PERMISSION_GRANTED) {
                            //When permission not granted , Request
                            ActivityCompat.requestPermissions(AddObject.this,
                                    new String[]{Manifest.permission.RECORD_AUDIO}, 200);
                        }else{

                            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                                startRecording();
                                txtRecordingStatus.setVisibility(View.VISIBLE);
                                txtRecordingStatus.setText("Recording");

                            } else if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
                                stopRecording();
                                txtRecordingStatus.setText("Recording Finished");
                            }
                        }





                    }
                }


                return false;
            }
        });*/

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check if permission to read storage
                if(ContextCompat.checkSelfPermission(AddObject.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                    //When permission not granted , Request
                    ActivityCompat.requestPermissions(AddObject.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                }else{
                    //When permission is granted

                    selectImage();
                }
            }
        });
       /* btnRecord.setOnTouchListener(new View.OnTouchListener() {
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
                    String engFileName=txtEnglishName.getText().toString().trim().toLowerCase();
                    String shonaFileName=txtShonaName.getText().toString().trim().toLowerCase();
                    if(engFileName.isEmpty()||shonaFileName.isEmpty() ){
                        Toast.makeText(AddObject.this, "English and Shona name are reqiured before you record ", Toast.LENGTH_SHORT).show();
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

                if(isRecoding){

                    stopRecording();

                }else{
                    String engFileName=txtEnglishName.getText().toString().trim().toLowerCase();
                    String shonaFileName=txtShonaName.getText().toString().trim().toLowerCase();
                  if(engFileName.isEmpty()||shonaFileName.isEmpty() ){
                      Toast.makeText(AddObject.this, "English and Shona name are reqiured before you record ", Toast.LENGTH_SHORT).show();
                  }else{

                          if(checkPermissions()){
                              startRecording();


                      }
                  }

                }
            }
        });



   btnSave.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           int selectedCategory=radioCategoryGroup.getCheckedRadioButtonId();
           radioCategoryButton=(RadioButton)findViewById(selectedCategory);



           String EnglishName=txtEnglishName.getText().toString().toLowerCase().trim().replaceAll(" ","_");
           String ShonaName=txtShonaName.getText().toString().toLowerCase().trim();




           if(EnglishName.isEmpty()){
               Toast.makeText(AddObject.this, "English Name is Missing", Toast.LENGTH_SHORT).show();
           }else if(ShonaName.isEmpty()){
               Toast.makeText(AddObject.this, "Shona name is missing", Toast.LENGTH_SHORT).show();
           }else if (radioCategoryGroup.getCheckedRadioButtonId()==-1){
               Toast.makeText(AddObject.this, "Please Pick a category", Toast.LENGTH_SHORT).show();
           }else if (sImage.isEmpty()) {
               Toast.makeText(AddObject.this, "Please Upload Object Image", Toast.LENGTH_SHORT).show();
           }else if(!Record){
               Toast.makeText(AddObject.this, "Please Upload Audio", Toast.LENGTH_SHORT).show();
           }else {
               upladAudio();
               mprogress.setMessage("Saving Data");
               mprogress.show();
               reference.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       String img=sImage;
                       String category=radioCategoryButton.getText().toString();
                       ObjectClass obj = new ObjectClass(EnglishName,ShonaName,img,category);

                       reference.child("objects").child(category).child(EnglishName).setValue(obj);
                       mprogress.dismiss();
                       AlertDialog builder=new AlertDialog.Builder(AddObject.this)
                               .setIcon(android.R.drawable.ic_menu_save)
                               //set title
                               .setTitle("Success")
                               //set message
                               .setMessage("Object added successfully ")
                               //set positive button
                               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {
                                       //set what would happen when positive button is clicked

                                       sImage="";
                                       txtEnglishName.setText(null);
                                       txtShonaName.setText(null);
                                       radioCategoryGroup.clearCheck();
                                       imgProduct.setImageResource(R.drawable.noimage);
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



    private void selectImage() {
        //Clear previous data
        imgProduct.setImageBitmap(null);
        //initialise intent
        Intent intent= new Intent(Intent.ACTION_GET_CONTENT);

        //Set type
        intent.setType("image/*");


        startActivityForResult(Intent.createChooser(intent,"Select Image"),
                100);

       /* ActivityResultLauncher<Intent> startActivityForResult=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode()==AppCompatActivity.RESULT_OK && result.getData()!=null){
                        //when result is ok ,initialize uri
                        Uri uri =result.getData().getData();

                        try {
                            //initialize bitmap
                            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            //set image view to the selected image
                            imgProduct.setImageBitmap(bitmap);
                            //initialize byte array stream
                            ByteArrayOutputStream stream=new ByteArrayOutputStream();
                            //compress bitmap
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                            //initialize byte array
                            byte [] bytes = stream.toByteArray();

                            //get base 64 encoded String

                            sImage= Base64.encodeToString(bytes,Base64.DEFAULT);
                            //Set Encoded String to textView
                            Toast.makeText(AddObject.this, sImage, Toast.LENGTH_SHORT).show();


                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }
        );*/



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Check condition
        if(requestCode==100 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            //when permission granted
            selectImage();
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Check condition
        if(requestCode==100&&resultCode==RESULT_OK&&data!=null){
            //when result is ok ,initialize uri
            Uri uri =data.getData();



            try {
                //initialize bitmap
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
               //set image view to the selected image
                imgProduct.setImageBitmap(bitmap);
                //initialize byte array stream
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                //compress bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                //initialize byte array
                byte [] bytes = stream.toByteArray();

                //get base 64 encoded String

                sImage= Base64.encodeToString(bytes,Base64.DEFAULT);
                //Set Encoded String to textView
                //Toast.makeText(AddObject.this, sImage, Toast.LENGTH_SHORT).show();



            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }



    //Funtion to decode string back to image

    private void decode(){
        //initialise byte array from encoded string
        byte [] bytes=Base64.decode(sImage,Base64.DEFAULT);

        //Initialize bitmap
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        //Set bitmap on image view
        imgProduct.setImageBitmap(bitmap);
    }



    private void startRecording() {
        fileName=txtEnglishName.getText().toString().trim().toLowerCase();
        String Newfname = fileName.replaceAll(" ","_");
        String filePath=getExternalFilesDir("/").getAbsolutePath();

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(filePath+"/"+Newfname+".3gp");
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
            txtRecordingStatus.setVisibility(View.VISIBLE);
            txtRecordingStatus.setText("Recording ");
            isRecoding=true;

        } catch (Exception e) {
            recorder.release();
            recorder = null;
            Log.e(LOG_TAG, "prepare() failed");
            Toast.makeText(AddObject.this, "Recording failed\n There is a problem with your microphone\n Try restarting your device", Toast.LENGTH_LONG).show();

        }


    }

    private void stopRecording() {
        try{
            recorder.stop();
            recorder.release();
            recorder = null;
            Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show();
            Record=true;
            txtRecordingStatus.setVisibility(View.VISIBLE);
            txtRecordingStatus.setText("Recording Stopped");
            isRecoding=false;
        } catch (Exception e) {
            recorder.release();
            recorder = null;
            Log.e(LOG_TAG, "prepare() failed");
            Toast.makeText(AddObject.this, "Recording failed\n There is a problem with your microphone\n Try restarting your device", Toast.LENGTH_LONG).show();

        }
    }

    private void upladAudio() {

        mprogress.setMessage("Uploading Audio to Database");
        mprogress.show();

        fileName=txtEnglishName.getText().toString().trim().toLowerCase();
       String Newfname = fileName.replaceAll(" ","_");
        String Path=getExternalFilesDir("/").getAbsolutePath();

        StorageReference filepath=mStorage.child("Audio1").child(Newfname+".3gp");
        Uri uri=Uri.fromFile(new File(Path+"/"+Newfname+".3gp"));
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

        if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            ActivityCompat.requestPermissions(AddObject.this,permissions,200);
            return false;
        }


    }
}