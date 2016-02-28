package br.com.rsicarelli.supportlibraryexample.map;

import br.com.rsicarelli.supportlibraryexample.R;
import br.com.rsicarelli.supportlibraryexample.presentation.BaseActivity;

public class MapActivity extends BaseActivity {

    @Override
    public void initializeContentView() {
        setContentView(R.layout.activity_map);
        initFragment(MapFragment.newInstance());
    }

}
