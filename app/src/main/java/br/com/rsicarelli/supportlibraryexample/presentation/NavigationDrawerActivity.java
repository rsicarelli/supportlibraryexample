package br.com.rsicarelli.supportlibraryexample.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rodrigosicarelli on 2/27/16.
 */
public abstract class NavigationDrawerActivity extends AppCompatActivity implements
        DrawerCompositor.OnDrawerListener {

    private DrawerCompositor drawerCompositor;

    public abstract void initializeContentView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerCompositor = DrawerCompositor.attach(this);

        initializeContentView();
    }

    @Override
    public DrawerLayout getDrawerLayout() {
        return null;
    }

    @Override
    public NavigationView getNavigationView() {
        return null;
    }

    @Override
    public int getCurrentMenuItemId() {
        return 0;
    }

    @Override
    public void onBackPressed() {
        if (!drawerCompositor.isDrawerOpen()) {
            super.onBackPressed();
        }
    }
}
