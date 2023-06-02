package com.demo.androidjavatutorial.CameraImage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.androidjavatutorial.MainActivity;

import com.demo.androidjavatutorial.AllUtils.RealPathUtil;
import com.example.androidjavatutorial.R;


import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class CameraImageActivity extends AppCompatActivity {

    CircleImageView image;
    Button btn_click;
    private static int CAMERA_PERMISSION_CODE = 100;
    static final int REQUEST_VIDEO_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_image);

        image = findViewById(R.id.image);
        btn_click = findViewById(R.id.btn_click);

        if (isCameraPresent()) {
            cameraPermission();
        } else {
            Log.i("Camera Phone", "Camera not detected");
        }


        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri selectImage = data.getData();
            InputStream inputStream = null;
            String path = RealPathUtil.getRealPath(CameraImageActivity.this,selectImage);
            System.out.println("SetFilePath:==" + path);
            try {
                assert selectImage != null;
                inputStream = getContentResolver().openInputStream(selectImage);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            BitmapFactory.decodeStream(inputStream);
            image.setImageURI(selectImage);

        }
    }

    private boolean isCameraPresent() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


    private void cameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CameraImageActivity.this, MainActivity.class));
        finish();
    }
}