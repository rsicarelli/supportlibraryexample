package br.com.rsicarelli.supportlibraryexample.data;

import android.support.annotation.NonNull;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class WonderPlacesRepositories {

    private static WonderPlacesRepository repository = null;

    public synchronized static WonderPlacesRepository getInMemoryRepoInstance(
            @NonNull WonderPlacesServiceApi wonderPlacesServiceApi) {
        if (null == repository) {
            repository = new InMemoryWonderPlacesRepository(wonderPlacesServiceApi);
        }
        return repository;
    }
}
