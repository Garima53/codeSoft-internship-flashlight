package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageButton toggleButton;

    boolean CamFlash= false;
    boolean on=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toggleButton= findViewById(R.id.toggleButton);

        CamFlash=getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        toggleButton.setOnClickListener(new View.OnClickListener() {

        @Override
               public void onClick(View v) {
               if(CamFlash){
                    if(on){
                        on=false;

                        toggleButton.setImageResource(R.drawable.off);
                        try {
                            flashOff();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        on=true;
                        toggleButton.setImageResource(R.drawable.on);
                        try {
                            flashOn();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,
                            "flash is not available",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
        private void flashOn() throws CameraAccessException {
        CameraManager cameraManager=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager !=null;
        String cameraId=cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId,true);
        Toast.makeText(MainActivity.this,"Flash is ON" ,Toast.LENGTH_LONG).show();

    }
        private void flashOff() throws CameraAccessException {
        CameraManager cameraManager=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
        String cameraId=cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId,false);
        Toast.makeText(MainActivity.this,"Flash is OFF" ,Toast.LENGTH_LONG).show();

    }
}