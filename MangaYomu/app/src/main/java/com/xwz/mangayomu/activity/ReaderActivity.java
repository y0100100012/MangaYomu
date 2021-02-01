package com.xwz.mangayomu.activity;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.xwz.mangayomu.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ReaderActivity extends Activity {

    private ImageView mainPic;

    private List<String> allDir = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        mainPic = findViewById(R.id.mainPic);

        getAllDir();
        String fileName = Environment.getExternalStorageDirectory()+"/Download/manga1/p1.jpg";
        Bitmap bm = BitmapFactory.decodeFile(fileName);
        mainPic.setImageBitmap(bm);
    }

    private void getAllDir(){
        String fileName = Environment.getExternalStorageDirectory()+"/Download";
        Log.e("info", fileName);
        File[] files = new File(fileName).listFiles();
        Log.e("info", String.valueOf(files.length));
        List<String> allDirName = new ArrayList<>();

        for (File file : files) {
            String tmp = file.getAbsolutePath();
            if (file.isDirectory()) {
                allDirName.add(tmp);
            }
        }
        allDir = allDirName;
    }
}
