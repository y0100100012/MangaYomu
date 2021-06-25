package com.xwz.mangayomu.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xwz.mangayomu.R;
import com.xwz.mangayomu.entity.BookInfo;
import com.xwz.mangayomu.entity.PageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReaderActivity extends Activity {
    private ImageView mainPic;
    private SeekBar pageControlBar;
    private TextView pageText;
    private TextView pageFileName;

    private List<String> allDir = new ArrayList<>();

    private int currentPage = -1;
    private int totalPage = 0;
    private boolean rotatePic = false;
    private boolean showUi = true;

    private final Pattern pattern = Pattern.compile("\\d+((?=\\.))");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        mainPic = findViewById(R.id.mainPic);
        pageControlBar = findViewById(R.id.pageControlBar);
        Button leftShift = findViewById(R.id.leftShift);
        Button rightShift = findViewById(R.id.rightShift);
        Button returnBtn = findViewById(R.id.returnBtn);
        Button turnPicBtn = findViewById(R.id.turnPicBtn);
        Button centerBtn = findViewById(R.id.centerBtn);
        pageText = findViewById(R.id.pageText);
        pageFileName = findViewById(R.id.pageFileName);

        Intent intent = getIntent();
        //获取书信息
        BookInfo bookInfo = intent.getParcelableExtra("bookInfo");
        //初始化书对象
        initBookPageInfo(bookInfo);

        if (allDir != null && !allDir.isEmpty()) {
            showPic(0);
        }

        leftShift.setOnClickListener((view) -> shiftPage(-1));
        rightShift.setOnClickListener((view) -> shiftPage(1));
        returnBtn.setOnClickListener((view) -> finish());
        turnPicBtn.setOnClickListener((view) -> togglePicTurn());
        centerBtn.setOnClickListener((view) -> onCenterClick());
        pageControlBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showPic(progress - 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //初始化书对象
    private void initBookPageInfo(BookInfo bookInfo) {
        File[] files = new File(bookInfo.getBookDir()).listFiles();
        if (files == null) {
            return;
        }
        List<String> allDirNameList = new ArrayList<>();

        for (File file : files) {
            String tmp = file.getAbsolutePath();
            if (file.isFile()) {
                allDirNameList.add(tmp);
            }
        }
        // 排序
        List<PageInfo> pageInfoList = new ArrayList<>();
        for(String path: allDirNameList){
            Matcher matcher = pattern.matcher(path);
            if (matcher.find()) {
                String page = matcher.group(0);
                if(page == null){
                    continue;
                }
                pageInfoList.add(new PageInfo(Integer.parseInt(page), path));
            }else{
                pageInfoList.add(new PageInfo(0, path));
            }
        }
        pageInfoList.sort((a, b) -> a.getPageNo() - b.getPageNo());
        allDirNameList = pageInfoList.stream().map(PageInfo::getPath).collect(Collectors.toList());

        totalPage = allDirNameList.size();
        allDir = allDirNameList;
        pageControlBar.setMax(totalPage);
    }

    private void shiftPage(int num) {
        if (currentPage < 0) {
            return;
        }
        int targetPage = currentPage + num;
        if (targetPage <= 0) {
            targetPage = 1;
        }
        if (targetPage > totalPage) {
            targetPage = totalPage;
        }

        showPic(targetPage - 1);
        if (pageControlBar.getProgress() != currentPage) {
            pageControlBar.setProgress(currentPage);
        }
    }

    private void showPic(int index) {
        Bitmap nowBitmap = BitmapFactory.decodeFile(allDir.get(index));
        if(rotatePic){
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            nowBitmap = Bitmap.createBitmap(nowBitmap, 0, 0, nowBitmap.getWidth(), nowBitmap.getHeight(), matrix, false);
        }
        mainPic.setImageBitmap(nowBitmap);
        pageFileName.setText(allDir.get(index));
        currentPage = index + 1;

        updatePageText();
    }

    private void togglePicTurn() {
        rotatePic = !rotatePic;
        showPic(currentPage - 1);
    }

    private void onCenterClick(){
        showUi = !showUi;
        pageControlBar.setVisibility(showUi ? View.VISIBLE : View.INVISIBLE);
        pageFileName.setVisibility(showUi ? View.VISIBLE : View.INVISIBLE);
        pageText.setVisibility(showUi ? View.VISIBLE : View.INVISIBLE);
    }

    private void updatePageText(){
        String pageTextStr = currentPage + " / " + totalPage;
        pageText.setText(pageTextStr);
    }
}
