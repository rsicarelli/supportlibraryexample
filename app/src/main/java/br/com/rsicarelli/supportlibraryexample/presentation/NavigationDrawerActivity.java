package br.com.rsicarelli.supportlibraryexample.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.com.rsicarelli.supportlibraryexample.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rodrigosicarelli on 2/27/16.
 */
public abstract class NavigationDrawerActivity extends AppCompatActivity implements
        DrawerCompositor.OnDrawerListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    private DrawerCompositor drawerCompositor;

    public abstract void initializeContentView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerCompositor = DrawerCompositor.attach(this);

        initializeContentView();
        ButterKnife.bind(this);
        setUpToolbar();
    }

    protected void setUpToolbar() {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    @Override
    public NavigationView getNavigationView() {
        return navigationView;
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
