package br.com.rsicarelli.supportlibraryexample.map;

import br.com.rsicarelli.supportlibraryexample.data.wonderplaces.Place;
import br.com.rsicarelli.supportlibraryexample.data.wonderplaces.WonderPlaces;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class MapContract {

    interface View {
        void setUpWonderPlacesUi(WonderPlaces wonderPlaces);

        void updateMapPositionUi(Place place);
    }

    interface ActionsListener {
        void loadWonderPlaces();
    }
}
