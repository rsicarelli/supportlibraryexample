package br.com.rsicarelli.supportlibraryexample.data;

import android.support.annotation.NonNull;

import br.com.rsicarelli.supportlibraryexample.data.wonderplaces.WonderPlaces;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public interface WonderPlacesRepository {

    interface LoadPlacesCallback {
        void onWonderPlacesLoaded(WonderPlaces places);
    }

    void getWonderPlaces(@NonNull LoadPlacesCallback callback);
}
