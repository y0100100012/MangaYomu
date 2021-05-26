package com.xwz.mangayomu.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

public class ProperUtil {
    private static final String configName = "appConfig.properties";

    public static String getProps(Context context, String key) throws IOException {
        Properties props = new Properties();
        InputStream in = context.getAssets().open(configName);
        props.load(in);
        String value = props.getProperty(key);
        in.close();
        return value;
    }

    public static void setProps(Context context, String key, String value) throws IOException {
        Properties props = new Properties();
        InputStream in = context.getAssets().open(configName);
        props.load(in);
        props.setProperty(key, value);
        FileOutputStream out = context.openFileOutput(configName, Context.MODE_PRIVATE);
        props.store(out, null);
        in.close();
        out.close();
    }


    private static String load(Context context) {
        InputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = context.getAssets().open(configName);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            Log.e("错误：", e.toString());
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

}
