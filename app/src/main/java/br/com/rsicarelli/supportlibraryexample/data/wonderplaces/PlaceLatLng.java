package br.com.rsicarelli.supportlibraryexample.data.wonderplaces;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class PlaceLatLng implements Parcelable {
    public double lat;
    public double lng;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
    }

    public PlaceLatLng() {
    }

    protected PlaceLatLng(Parcel in) {
        this.lat = in.readDouble();
        this.lng = in.readDouble();
    }

    public static final Parcelable.Creator<PlaceLatLng> CREATOR = new Parcelable.Creator<PlaceLatLng>() {
        public PlaceLatLng createFromParcel(Parcel source) {
            return new PlaceLatLng(source);
        }

        public PlaceLatLng[] newArray(int size) {
            return new PlaceLatLng[size];
        }
    };
}
