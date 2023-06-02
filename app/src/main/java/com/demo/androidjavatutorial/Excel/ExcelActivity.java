package com.demo.androidjavatutorial.Excel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.demo.androidjavatutorial.MainActivity;
import com.example.androidjavatutorial.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ExcelActivity extends AppCompatActivity {

    LinearLayout ll_upload_file, ll_txt, ll_phone_email, ll_sample_txt;
    TextView txt_email_id, txt_ph_no, txt_file_name;
    File myFile;
    Uri uri;
    ImageView ic_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);

        ll_upload_file = findViewById(R.id.ll_upload_file);
        ic_close = findViewById(R.id.ic_close);
        txt_email_id = findViewById(R.id.txt_email_id);
        txt_ph_no = findViewById(R.id.txt_ph_no);
        txt_file_name = findViewById(R.id.txt_file_name);
        ll_txt = findViewById(R.id.ll_txt);
        ll_phone_email = findViewById(R.id.ll_phone_email);
        ll_sample_txt = findViewById(R.id.ll_sample_txt);

        ll_upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ExcelActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ExcelActivity.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    selectExcel();
                }
            }
        });

        ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_upload_file.setVisibility(View.VISIBLE);
                ll_sample_txt.setVisibility(View.VISIBLE);
                ll_phone_email.setVisibility(View.GONE);
                ll_txt.setVisibility(View.GONE);
                ic_close.setVisibility(View.GONE);
            }
        });

        ll_sample_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ExcelActivity.this, R.style.CustomBottomSheetDialogTheme);
                View inflate = LayoutInflater.from(ExcelActivity.this).inflate(R.layout.bottom_dialog, null);
                bottomSheetDialog.setContentView(inflate);

                ImageView bottom_img = inflate.findViewById(R.id.bottom_img);

                try {
                    InputStream ims = getAssets().open("sample_exe_file.png");
                    Drawable d = Drawable.createFromStream(ims, null);
                    bottom_img.setImageDrawable(d);
                } catch (IOException ex) {
                    return;
                }
                bottomSheetDialog.show();
            }
        });
    }

    private String getPathFromExtSD(String[] pathData) {
        final String relativePath = "/" + pathData[1];

        return relativePath;
    }

    private void selectExcel() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        startActivityForResult(intent, 1);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (myFile != null) {
            ic_close.setVisibility(View.VISIBLE);
            ll_upload_file.setVisibility(View.GONE);
        } else if (myFile == null) {
            Toast.makeText(this, "please_upload_file", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    uri = data.getData();
                    String uriString = uri.toString();
                    myFile = new File(uri.getPath());
                    String displayName = null;
                    onReadClick(uri);

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Log.e("nameeeee>>>>", displayName);
                                txt_file_name.setText(displayName);
                                ll_txt.setVisibility(View.VISIBLE);
                                ll_phone_email.setVisibility(View.VISIBLE);
                                ll_sample_txt.setVisibility(View.GONE);
                                ic_close.setVisibility(View.VISIBLE);
                                ll_upload_file.setVisibility(View.GONE);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        Log.d("nameeeee>>>>  ", displayName);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onReadClick(Uri uriString) {
        try {
            InputStream stream = getContentResolver().openInputStream(uriString);
            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();

//            ArrayList<String> email = new ArrayList<>();

//            int column_index_2 = 0;
//            Row row = sheet.getRow(0);
//            for (Cell cell : row) {
//                switch (cell.getStringCellValue()) {
//                    case "Email":
//                        column_index_2 = cell.getColumnIndex();
//                        break;
//                }
//            }
//
//            for (Row r : sheet) {
//                if (r.getRowNum() == 0) continue;
//                Cell c_2 = r.getCell(column_index_2);
//                if (c_2 != null) {
//                    System.out.print("" + c_2);
//
//                    email.add(String.valueOf(c_2));
//                    txt_email_id.setText(String.valueOf(email.size()));
//                }
//            }

            ArrayList<String> email = new ArrayList<>();
            ArrayList<String> pass = new ArrayList<>();

            int column_index_2 = 0;
            int column_index_3 = 0;
            Row row = sheet.getRow(0);

            for (Cell cell : row) {
                if (cell.getStringCellValue().equals("Email")) {
                    column_index_2 = cell.getColumnIndex();
                    for (Row r : sheet) {
                        if (r.getRowNum() == 0) continue;
                        Cell c_2 = r.getCell(column_index_2);
                        if (c_2 != null) {
                            System.out.print("" + c_2);

                            email.add(String.valueOf(c_2));
                            txt_email_id.setText(String.valueOf(email.size()));
                        }
                    }
                } else if (cell.getStringCellValue().equals("Phone Number")) {
                    column_index_3 = cell.getColumnIndex();
                    for (Row r1 : sheet) {
                        if (r1.getRowNum() == 0) continue;
                        Cell c_3 = r1.getCell(column_index_3);
                        if (c_3 != null) {
                            System.out.print("" + c_3);

                            pass.add(String.valueOf(c_3));
                            txt_ph_no.setText(String.valueOf(pass.size()));
                        }
                    }
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectExcel();
        } else {
            Toast.makeText(getApplicationContext(), "permission_denied", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ExcelActivity.this, MainActivity.class));
        finish();
    }
}