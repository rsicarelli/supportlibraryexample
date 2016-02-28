package br.com.rsicarelli.supportlibraryexample.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.rsicarelli.supportlibraryexample.R;
import butterknife.ButterKnife;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class HomeFragment extends android.support.v4.app.Fragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(root, getActivity());

        initUi();

        return root;
    }

    private void initUi() {

    }
}
