package com.xwz.mangayomu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xwz.mangayomu.activity.BookListActivity;
import com.xwz.mangayomu.utils.ProperUtil;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int READ_REQUEST_CODE = 1;

    private TextView srcUrlText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        srcUrlText = findViewById(R.id.srcUrlText);
        Button setSrcBtn = findViewById(R.id.setSrcBtn);
        Button startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(this);
        setSrcBtn.setOnClickListener(this);

        loadProps();
    }

    private void loadProps(){
        try {
            String mangaUri = ProperUtil.getProps(getApplicationContext(), "mangaUri");
            srcUrlText.setText(mangaUri);
            Toast.makeText(getApplicationContext(), "配置："+mangaUri, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.startBtn) {
            onStartClick();
        } else if (id == R.id.setSrcBtn) {
            onSettingClick();
        }
    }

    private void onStartClick(){
        startActivity(new Intent(MainActivity.this, BookListActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
        if (requestCode == READ_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String path = uri.getPath();
                try {
                    ProperUtil.setProps(getApplicationContext(), "mangaUri", path != null ? path : "");
                    loadProps();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "读写配置文件失败", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onSettingClick(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
}