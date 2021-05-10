package com.xwz.mangayomu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.xwz.mangayomu.R;
import com.xwz.mangayomu.adapter.BookAdapter;
import com.xwz.mangayomu.entity.BookInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookListActivity extends AppCompatActivity {
    private final List<BookInfo> bookInfoList = new ArrayList<>();
    private final Pattern pattern = Pattern.compile("(\\d+)(?=.)");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        ListView bookListView = findViewById(R.id.book_list);
        BookAdapter adapter = new BookAdapter(BookListActivity.this, R.layout.list_item_book, bookInfoList);
        bookListView.setAdapter(adapter);

        bookListView.setOnItemClickListener((parent, view, position, id) -> {
            //行点击事件
            BookInfo bookInfo = bookInfoList.get(position);
            Intent intent = new Intent(BookListActivity.this, ReaderActivity.class);
            intent.putExtra("bookInfo", bookInfo);
            startActivity(intent);
        });

        findAllBook(Environment.getExternalStorageDirectory() + "/Download");
    }

    private void findAllBook(String rootPath) {
        File bookRootFile = new File(rootPath);
        File[] allBookFiles = bookRootFile.listFiles();
        if(allBookFiles == null){
            return;
        }
        for (File dirTemp : allBookFiles) {
            BookInfo newBookInfo = new BookInfo();
            newBookInfo.setBookDir(dirTemp.getAbsolutePath());
            newBookInfo.setBookName(dirTemp.getName());

            File[] allPicFiles = dirTemp.listFiles();
            if(allPicFiles == null){
                continue;
            }
            newBookInfo.setPageNum(allPicFiles.length);
            //最小页码
            int minIndex = -1;
            int minPage = 1000000000;
            for (int i = 0; i < allPicFiles.length; i++) {
                Matcher matcher = pattern.matcher(allPicFiles[i].getName());
                if (matcher.find()) {
                    String page = matcher.group(0);
                    if(page == null){
                        continue;
                    }
                    if (Integer.parseInt(page) < minPage) {
                        minPage = Integer.parseInt(page);
                        minIndex = i;
                    }
                }
            }
            if(minIndex >= 0){
                newBookInfo.setCoverUri(Uri.fromFile(allPicFiles[minIndex]));
            }
            bookInfoList.add(newBookInfo);
        }
    }
}
