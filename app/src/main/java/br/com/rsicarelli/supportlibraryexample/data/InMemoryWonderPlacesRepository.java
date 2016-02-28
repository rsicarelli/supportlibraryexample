package br.com.rsicarelli.supportlibraryexample.data;

import android.support.annotation.NonNull;

import br.com.rsicarelli.supportlibraryexample.data.wonderplaces.WonderPlaces;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class InMemoryWonderPlacesRepository implements WonderPlacesRepository {

    private final WonderPlacesServiceApi wonderPlacesServiceApi;
    WonderPlaces cachedWonderPlaces;

    public InMemoryWonderPlacesRepository(@NonNull WonderPlacesServiceApi wonderPlacesServiceApi) {
        this.wonderPlacesServiceApi = wonderPlacesServiceApi;
    }

    @Override
    public void getWonderPlaces(@NonNull final LoadPlacesCallback callback) {
        if (cachedWonderPlaces == null) {
            wonderPlacesServiceApi.getCachedWonderPlaces(new WonderPlacesServiceApi.WonderPlacesServiceCallback<WonderPlaces>() {
                @Override
                public void onLoaded(WonderPlaces places) {
                    cachedWonderPlaces = places;
                    callback.onWonderPlacesLoaded(cachedWonderPlaces);
                }
            });
        } else {
            callback.onWonderPlacesLoaded(cachedWonderPlaces);
        }
    }
}
