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

package br.com.rsicarelli.supportlibraryexample.data;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;

import br.com.rsicarelli.supportlibraryexample.ExampleApplication;
import br.com.rsicarelli.supportlibraryexample.R;
import br.com.rsicarelli.supportlibraryexample.data.wonderplaces.WonderPlaces;

public class WonderPlacesServiceApiImpl implements WonderPlacesServiceApi {

    @Override
    public void getCachedWonderPlaces(final WonderPlacesServiceCallback<WonderPlaces> callback) {
        AsyncTask<Void, Void, WonderPlaces> asyncTask = new AsyncTask<Void, Void, WonderPlaces>() {
            @Override
            protected WonderPlaces doInBackground(Void... params) {
                InputStream inputStream = ExampleApplication.getExampleApplication().
                        getResources().openRawResource(R.raw.places);
                final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                Gson gson = new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        .serializeNulls()
                        .create();

                return gson.fromJson(reader, WonderPlaces.class);
            }

            @Override
            protected void onPostExecute(WonderPlaces places) {
                callback.onLoaded(places);
            }
        };
        asyncTask.execute();
    }
}
