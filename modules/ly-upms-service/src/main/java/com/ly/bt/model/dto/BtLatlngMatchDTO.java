package com.ly.bt.model.dto;

/**
 * query bt lat lng
 */
public class BtLatlngMatchDTO {


    String lat;
    String lng;

    String title;

    String imageUrl;

    String des;

    boolean match;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public boolean isMatch() {
        return match;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
