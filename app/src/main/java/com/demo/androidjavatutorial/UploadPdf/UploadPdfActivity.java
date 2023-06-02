package com.demo.androidjavatutorial.UploadPdf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.demo.androidjavatutorial.MainActivity;
import com.example.androidjavatutorial.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UploadPdfActivity extends AppCompatActivity {

    TextView txt_uri;
    RelativeLayout rl_upload;
    ImageView img_add_brochure;
    LinearLayout lin_add_images;
    public static final int PICK_FILE = 99;
    private static final int REQUEST_CODE = 101;
    PdfRenderer renderer;
    int total_pages = 0;
    int display_page = 0;
    String path;
    Button btn;
    String finalDisplayName;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        txt_uri = findViewById(R.id.txt_uri);
        rl_upload = findViewById(R.id.rl_upload);
        img_add_brochure = findViewById(R.id.img_add_brochure);
        lin_add_images = findViewById(R.id.lin_add_images);
        btn = findViewById(R.id.btn);

        rl_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });
    }

    private void selectPdf() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICK_FILE);
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.getData();
                Log.e("nameeeee>>>>", uri.toString());
                String uriString = uri.toString();
                File myFile = new File(uri.getPath());
                lin_add_images.setVisibility(View.GONE);

                String displayName = null;
                try {
                    ParcelFileDescriptor parcelFileDescriptor = getContentResolver()
                            .openFileDescriptor(uri, "r");
                    renderer = new PdfRenderer(parcelFileDescriptor);
                    total_pages = renderer.getPageCount();
                    display_page = 0;
                    _display(display_page);

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Log.e("nameeeee>>>>", displayName);
                                txt_uri.setText(displayName);
                                finalDisplayName = displayName;

                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();

                        Log.d("nameeeee>>>>  ", displayName);
                    }
                } catch (FileNotFoundException fnfe) {

                } catch (IOException e) {

                }
            }
        }
    }

    private void _display(int _n) {
        if (renderer != null) {
            PdfRenderer.Page page = renderer.openPage(_n);
            Bitmap mBitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
            page.render(mBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            img_add_brochure.setImageBitmap(mBitmap);
            page.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPdf();
        } else {
            Toast.makeText(getApplicationContext(), "permission_denied", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(UploadPdfActivity.this, MainActivity.class));
        finish();
    }
}