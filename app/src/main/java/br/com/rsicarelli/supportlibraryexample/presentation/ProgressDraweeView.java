package br.com.rsicarelli.supportlibraryexample.presentation;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import br.com.rsicarelli.supportlibraryexample.ExampleApplication;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class ProgressDraweeView extends SimpleDraweeView {
    public ProgressDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ProgressDraweeView(Context context) {
        super(context);
    }

    public ProgressDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ProgressDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setUriWithProgress(Uri imageUri, CircularProgressView progressView) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(getController())
                .setControllerListener(new CustomDraweeListener(progressView))
                .setUri(imageUri)
                .build();
        setController(controller);
    }
}

class CustomDraweeListener extends BaseControllerListener {
    private final CircularProgressView progressView;

    public CustomDraweeListener(CircularProgressView progressView) {
        this.progressView = progressView;
    }

    @Override
    public void onFinalImageSet(String id, @Nullable Object imageInfo, @Nullable Animatable animatable) {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
        Log.d(ExampleApplication.class.getCanonicalName(), "Error downloading the image");
    }

}