package com.niteshjha.videoconverter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static int CAMERA_PERMISSION_CODE = 100;
    private static int VIDEO_RECORDING_CODE = 101;

    private Uri videoPath ;

    private MediaRecorder mediaRecorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaRecorder = new MediaRecorder();

        if (isCameraPresent()){
            Log.i("VIDEO_RECORDING_TAG", "Camera is available");
            getCameraPermission();
        }
        else {
            Log.i("VIDEO_RECORDING_TAG", "Camera is not available");
        }
    }

    private boolean isCameraPresent() {

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        }
        else
            return false;
    }

    private void getCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

        }
    }
    private void recordVideo(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_RECORDING_CODE);
    }

    public void recordVideoButtonPressed(View view) {
        recordVideo();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("VIDEO_RECORDING_TAG", "Executing onActivityResult");



            if (resultCode == RESULT_OK) {

                Log.i("VIDEO_RECORDING_TAG", "Executing RESULT_OK");

                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//                mediaRecorder.setOutputFile();





//                MediaRecorder.VideoEncoder.H263;
//
//                         MediaRecorder.setOutputFormat
//                         MediaRecorder.setOutputFile
//                         MediaRecorder.setVideoEncodingBitRate
//                         MediaRecorder.setVideoFrameRate
//                         MediaRecorder.setVideoSize
//                         MediaRecorder.setVideoEncoder
//                         MediaRecorder.setOrientationHint
//                         MediaRecorder.prepare

                        //# Create a new instance of MediaRecorder
//                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);


                try {

//                # Video settings
                    mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263); //contained inside MP4
//                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
//                    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    mediaRecorder.setVideoSize(640, 480); //width 640, height 480
                    mediaRecorder.setVideoFrameRate(30);  //30 FPS
                    mediaRecorder.setVideoEncodingBitRate(3000000); //adjust this for picture quality

//                # Audio settings
//                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC); //must always be AAC
//                    mediaRecorder.setAudioEncoder(MediaRecorder.getAudioSourceMax());
//                    mediaRecorder.setAudioEncodingBitRate(16);
//                    mediaRecorder.setAudioSamplingRate(44100);
                    mediaRecorder.prepare();
                    Log.i("VIDEO_RECORDING_TAG", "Executing Try block");

                } catch (IOException e) {
                    e.printStackTrace();
                }


                videoPath = data.getData();

                Toast.makeText(this, "Recorded Successfully at" +videoPath, Toast.LENGTH_SHORT).show();
                Log.i("VIDEO_RECORDING_TAG", "Recorded Successfully at" +videoPath);


            } else if (requestCode == RESULT_CANCELED) {

                Toast.makeText(this, "Recorded Video Was Unsuccessful", Toast.LENGTH_SHORT).show();
                Log.i("VIDEO_RECORDING_TAG", "Recorded Video Was Unsuccessful");

            } else {

                Toast.makeText(this, "Something Went Wrong" , Toast.LENGTH_SHORT).show();
                Log.i("VIDEO_RECORDING_TAG", "Something Went Wrong");



        }
    }
}