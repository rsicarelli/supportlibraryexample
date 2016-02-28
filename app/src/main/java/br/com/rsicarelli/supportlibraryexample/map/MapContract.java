package br.com.rsicarelli.supportlibraryexample.map;

import com.google.android.gms.maps.model.LatLng;

import br.com.rsicarelli.supportlibraryexample.data.wonderplaces.Place;
import br.com.rsicarelli.supportlibraryexample.data.wonderplaces.WonderPlaces;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class MapContract {

    interface View {
        void showWonderPlaceDetail(LatLng latLng);

        void setUpWonderPlaces(WonderPlaces wonderPlaces);

        void updateMapPosition(Place place);
    }

    interface UserActionsListener {
        void loadWonderPlaces();
    }
}
