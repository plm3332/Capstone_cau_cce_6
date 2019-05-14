package org.techtown.carchap_v11;

public class Listview_location_Item {
    private String place_name;
    private String x;
    private String y;


    public String getPlace_name() {
        return place_name;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public Listview_location_Item(String place_name,String x,String y){
        this.place_name=place_name;
        this.x=x;
        this.y=y;
    }
}


