package com.ly.admin.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import lombok.Data;

import java.io.File;
import java.net.URL;

/**
 * @Author: liyang
 * @Date: 18-12-17 上午12:16
 */
public class ImageMateUtils {

    @Data
    public static class MateImage {
        String latitude, longitude;
    }


    /**
     * 读取照片里面的信息
     */
    public static MateImage getImageMate(File file) throws Exception {

        Metadata metadata = ImageMetadataReader.readMetadata(file);
        String lat = "";
        String log = "";
        MateImage mateImage = new MateImage();
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();  //标签名
                String desc = tag.getDescription(); //标签信息
                System.out.println(tagName + "   " + desc);//照片信息
                if (tagName.equals("GPS Latitude")) {
                    lat = pointToLatlong(desc);
                    mateImage.setLongitude(lat);
                } else if (tagName.equals("GPS Longitude")) {
                    log = pointToLatlong(desc);
                    mateImage.setLongitude(log);
                }

            }
        }

        return mateImage;
    }

    /**
     * 经纬度格式  转换
     *
     * @param point 坐标点
     * @return
     */
    public static String pointToLatlong(String point) {
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
        Double fen = Double.parseDouble(point.substring(point.indexOf("°") + 1, point.indexOf("'")).trim());
        Double miao = Double.parseDouble(point.substring(point.indexOf("'") + 1, point.indexOf("\"")).trim());
        Double duStr = du + fen / 60 + miao / 60 / 60;
        return duStr.toString();
    }

    /**
     * @param log 经度
     * @param lat 纬度
     * @return
     */
    public static String getAdd(String log, String lat) {
        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l=" + lat + "," + log + "&type=010";
        String res = "";
        try {
            URL url = new URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line + "\n";
            }
            in.close();
        } catch (Exception e) {
            System.out.println("error in wapaction,and e is " + e.getMessage());
        }
        //               System.out.println(res);
        return res;
    }
}
