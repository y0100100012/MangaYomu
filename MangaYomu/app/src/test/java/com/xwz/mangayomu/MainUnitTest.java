package com.xwz.mangayomu;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class MainUnitTest {
    @Test
    public void addition_isCorrect() {
        String str = "/0/Ma/C_1112_005.jpg";


        Pattern pattern = Pattern.compile("\\d+((?=\\.))");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }

    }
}