package br.com.rsicarelli.supportlibraryexample.data.wonderplaces;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Random;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class WonderPlaces {
    @SerializedName("response")
    public List<Place> places;

    public LatLng getRandomPlaceLatLng() {
        if (places.size() > 0) {
            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(places.size());
            Place randomPlace = places.get(index);
            return new LatLng(randomPlace.latLng.lat, randomPlace.latLng.lng);
        } else {
            return null;
        }
    }
}
