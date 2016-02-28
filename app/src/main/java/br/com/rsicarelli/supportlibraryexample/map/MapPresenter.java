/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.rsicarelli.supportlibraryexample.map;

import android.support.annotation.NonNull;

import br.com.rsicarelli.supportlibraryexample.data.WonderPlacesRepository;
import br.com.rsicarelli.supportlibraryexample.data.wonderplaces.WonderPlaces;

public class MapPresenter implements MapContract.ActionsListener {

    private final MapContract.View mapView;
    private final WonderPlacesRepository wonderPlacesRepository;

    public MapPresenter(@NonNull WonderPlacesRepository wonderPlacesRepository, @NonNull MapContract.View mapView) {
        this.mapView = mapView;
        this.wonderPlacesRepository = wonderPlacesRepository;
    }

    @Override
    public void loadWonderPlaces() {
        wonderPlacesRepository.getWonderPlaces(new WonderPlacesRepository.LoadPlacesCallback() {
            @Override
            public void onWonderPlacesLoaded(WonderPlaces places) {
                mapView.setUpWonderPlacesUi(places);
                mapView.updateMapPositionUi(places.getRandomPlace());
            }
        });
    }
}
