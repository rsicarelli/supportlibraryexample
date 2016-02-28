package br.com.rsicarelli.supportlibraryexample.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.lang.ref.WeakReference;

import br.com.rsicarelli.supportlibraryexample.R;
import br.com.rsicarelli.supportlibraryexample.home.HomeActivity;
import br.com.rsicarelli.supportlibraryexample.map.MapActivity;

/**
 * Created by rodrigosicarelli on 2/27/16.
 */
public class DrawerCompositor extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private static final String FRAG_TAG = DrawerCompositor.class.getCanonicalName();

    protected WeakReference<DrawerLayout> drawerLayout;
    protected WeakReference<NavigationView> navigationView;
    protected int currentItemId;

    private OnDrawerListener drawerListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnDrawerListener) {
            drawerListener = (OnDrawerListener) context;
        } else {
            throw new IllegalArgumentException(String.format(
                    "%s must implements %s",
                    context.getClass().getSimpleName(),
                    OnDrawerListener.class.getSimpleName()));
        }
    }

    public static <ParentActivity extends AppCompatActivity & OnDrawerListener> DrawerCompositor attach(ParentActivity parent) {
        FragmentManager fragmentManager = parent.getSupportFragmentManager();

        DrawerCompositor frag = (DrawerCompositor) fragmentManager.findFragmentByTag(FRAG_TAG);
        if (frag == null) {
            frag = new DrawerCompositor();
            fragmentManager.beginTransaction().add(frag, FRAG_TAG).commit();
        }
        return frag;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        drawerLayout = new WeakReference<>(drawerListener.getDrawerLayout());
        navigationView = new WeakReference<>(drawerListener.getNavigationView());
        currentItemId = drawerListener.getCurrentMenuItemId();

        navigationView.get().setNavigationItemSelectedListener(this);

        navigationView.get().setCheckedItem(currentItemId);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (drawerLayout.get().isDrawerOpen(GravityCompat.START)) {
            drawerLayout.get().closeDrawers();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationView.get().setCheckedItem(drawerListener.getCurrentMenuItemId());
    }

    public boolean isDrawerOpen() {
        if (drawerLayout.get().isDrawerOpen(GravityCompat.START)) {
            drawerLayout.get().closeDrawers();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.get().closeDrawers();

        if (currentItemId != item.getItemId()) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    navigateToMenuItem(HomeActivity.class);
                    break;
                case R.id.nav_map:
                    navigateToMenuItem(MapActivity.class);
                    break;
            }
        }
        return true;
    }

    public interface OnDrawerListener {
        DrawerLayout getDrawerLayout();

        NavigationView getNavigationView();

        int getCurrentMenuItemId();
    }

    private void navigateToMenuItem(java.lang.Class<?> activity) {
        Intent intent = new Intent(getActivity(), activity);
        getActivity().startActivity(intent);
    }
}