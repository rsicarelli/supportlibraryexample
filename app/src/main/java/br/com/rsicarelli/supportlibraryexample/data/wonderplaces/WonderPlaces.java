package br.com.rsicarelli.supportlibraryexample.data.wonderplaces;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Random;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class WonderPlaces implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(places);
    }

    public WonderPlaces() {
    }

    protected WonderPlaces(Parcel in) {
        this.places = in.createTypedArrayList(Place.CREATOR);
    }

    public static final Parcelable.Creator<WonderPlaces> CREATOR = new Parcelable.Creator<WonderPlaces>() {
        public WonderPlaces createFromParcel(Parcel source) {
            return new WonderPlaces(source);
        }

        public WonderPlaces[] newArray(int size) {
            return new WonderPlaces[size];
        }
    };
}
