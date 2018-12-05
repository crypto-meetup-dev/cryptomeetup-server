package com.ly.admin;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @auther Administrator liyang
 * @create 2018/11/19 4:46
 */
public class DateTest {
    @Test
    public void testDateInit(){
        String str = "2018-11-18T18:52:50.000+0000";
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
