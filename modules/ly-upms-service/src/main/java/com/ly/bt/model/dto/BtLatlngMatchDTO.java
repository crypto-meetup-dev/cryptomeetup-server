package com.ly.bt.model.dto;

import java.util.List;

/**
 * query bt lat lng
 */
public class BtLatlngMatchDTO {


    private List<LatLng> latLngs;


    public List<LatLng> getLatLngs() {
        return latLngs;
    }

    public void setLatLngs(List<LatLng> latLngs) {
        this.latLngs = latLngs;
    }

    /**
     * 
     */
    public static class  LatLng{
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
}
