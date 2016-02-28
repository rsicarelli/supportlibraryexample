package br.com.rsicarelli.supportlibraryexample.home;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import br.com.rsicarelli.supportlibraryexample.R;
import br.com.rsicarelli.supportlibraryexample.presentation.NavigationDrawerActivity;

public class HomeActivity extends NavigationDrawerActivity {

    @Override
    public void initializeContentView() {
        setContentView(R.layout.activity_home);

        initFragment(Fragment.newInstance());
    }

    private void initFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public int getCurrentMenuItemId() {
        return R.id.nav_home;
    }
}
