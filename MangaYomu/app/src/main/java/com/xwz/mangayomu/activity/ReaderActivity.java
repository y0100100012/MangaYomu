package com.xwz.mangayomu.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;

import com.xwz.mangayomu.R;

public class ReaderActivity extends Activity {

    private ImageView mainPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        mainPic = findViewById(R.id.mainPic);

        String fileName = "/data/data/com.test/aa.png";
        Bitmap bm = BitmapFactory.decodeFile(fileName);
        mainPic.setImageBitmap(bm);
    }
}
