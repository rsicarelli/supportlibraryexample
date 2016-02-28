package br.com.rsicarelli.supportlibraryexample.home;

import br.com.rsicarelli.supportlibraryexample.R;
import br.com.rsicarelli.supportlibraryexample.presentation.NavigationDrawerActivity;

public class HomeActivity extends NavigationDrawerActivity {

    @Override
    public void initializeContentView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    public int getCurrentMenuItemId() {
        return R.id.nav_home;
    }
}
