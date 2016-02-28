package br.com.rsicarelli.supportlibraryexample;

import android.app.Application;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class ExampleApplication extends Application {

    private static ExampleApplication exampleApplication;

    public static ExampleApplication getExampleApplication() {
        return exampleApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        exampleApplication = this;
    }
}
