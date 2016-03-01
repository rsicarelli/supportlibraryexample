package br.com.rsicarelli.supportlibraryexample.home;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import br.com.rsicarelli.supportlibraryexample.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class HomeFragment extends android.support.v4.app.Fragment {

    private DayNightListener dayNightListener;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Bind(R.id.image_view)
    ImageView imageView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof DayNightListener) {
            dayNightListener = (DayNightListener) context;
        } else {
            throw new IllegalArgumentException(String.format(
                    "%s must implements %s",
                    context.getClass().getSimpleName(),
                    DayNightListener.class.getSimpleName()));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }

        return root;
    }

    @OnClick(R.id.change_day_night_theme)
    public void onClick(View view) {
        dayNightListener.changeDayNightTheme();
    }

    public interface DayNightListener {
        void changeDayNightTheme();
    }
}
