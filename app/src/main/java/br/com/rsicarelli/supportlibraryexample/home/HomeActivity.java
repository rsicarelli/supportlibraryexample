package br.com.rsicarelli.supportlibraryexample.home;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import br.com.rsicarelli.supportlibraryexample.R;
import br.com.rsicarelli.supportlibraryexample.presentation.BaseActivity;
import br.com.rsicarelli.supportlibraryexample.presentation.DrawerCompositor;
import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements DrawerCompositor.OnDrawerListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    private DrawerCompositor drawerCompositor;

    @Override
    public void initializeContentView() {
        setContentView(R.layout.activity_home);
        drawerCompositor = DrawerCompositor.attach(this);

        ButterKnife.bind(this);

        setUpToolbar();
        initFragment(HomeFragment.newInstance());
    }

    protected void setUpToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
    public void onBackPressed() {
        if (!drawerCompositor.isDrawerOpen()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        drawerLayout.openDrawer(GravityCompat.START);
        return super.onSupportNavigateUp();
    }

    @Override
    public int getCurrentMenuItemId() {
        return R.id.nav_home;
    }
}
