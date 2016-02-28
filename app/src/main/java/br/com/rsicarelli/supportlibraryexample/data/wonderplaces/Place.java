package br.com.rsicarelli.supportlibraryexample.data.wonderplaces;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class Place {
    public String title;
    public String description;
    public String location;
    @SerializedName("image_uri")
    public  String imageUri;
    @SerializedName("lat_lng")
    public PlaceLatLng latLng;
}
