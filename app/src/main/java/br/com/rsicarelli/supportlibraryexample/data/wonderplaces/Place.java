package br.com.rsicarelli.supportlibraryexample.data.wonderplaces;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class Place implements Parcelable {
    public String title;
    public String description;
    public String location;
    @SerializedName("image_uri")
    public  String imageUri;
    @SerializedName("lat_lng")
    public PlaceLatLng latLng;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.location);
        dest.writeString(this.imageUri);
        dest.writeParcelable(this.latLng, flags);
    }

    public Place() {
    }

    protected Place(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.location = in.readString();
        this.imageUri = in.readString();
        this.latLng = in.readParcelable(PlaceLatLng.class.getClassLoader());
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
