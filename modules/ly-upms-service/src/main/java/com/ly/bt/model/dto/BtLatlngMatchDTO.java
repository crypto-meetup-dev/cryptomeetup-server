package com.ly.bt.model.dto;

import java.util.List;

/**
 * query bt lat lng
 */
public class BtLatlngMatchDTO {


    private List latLngs;


    public List getLatLngs() {
        return latLngs;
    }

    public void setLatLngs(List latLngs) {
        this.latLngs = latLngs;
    }

    public static class  LatLng{
        String lat;
        String lng;

        boolean match;



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
