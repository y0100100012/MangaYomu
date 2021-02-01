package com.xwz.mangayomu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xwz.mangayomu.activity.ReaderActivity;
import com.xwz.mangayomu.utils.FileUtils;

import java.nio.file.Path;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int FILE_REQUIRE_CODE = 1;

    private Button startBtn;
    private Button setSrcBtn;
    private TextView srcUrlText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        srcUrlText = findViewById(R.id.srcUrlText);
        setSrcBtn = findViewById(R.id.setSrcBtn);
        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(this);
        setSrcBtn.setOnClickListener(this);

        myRequirePermission();
    }

    private void myRequirePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }else {
            Toast.makeText(this,"您已经申请了权限!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.startBtn) {
            onStartClick(view);
        } else if (id == R.id.setSrcBtn) {
            onSettingClick(view);
        }
    }

    private void onStartClick(View view){
        startActivity(new Intent(MainActivity.this, ReaderActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
        switch (requestCode) {
            case FILE_REQUIRE_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String path = uri.getPath();
                    srcUrlText.setText(path != null ? path : "null");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onSettingClick(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, FILE_REQUIRE_CODE);
    }
}