package br.com.rsicarelli.supportlibraryexample.presentation;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Window;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import br.com.rsicarelli.supportlibraryexample.R;
import br.com.rsicarelli.supportlibraryexample.data.wonderplaces.Place;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class WonderPlaceDetailDialog extends AppCompatDialogFragment {

    public static final String ARG_PLACE = "place";

    @Bind(R.id.wonder_place_image)
    SimpleDraweeView wonderPlaceImage;

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.description)
    TextView description;

    @Bind(R.id.location)
    TextView location;

    private Place place;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        place = args.getParcelable(ARG_PLACE);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bottom_sheet);
        ButterKnife.bind(this, dialog);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(place.getPlaceUri())
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .build();
        wonderPlaceImage.setController(controller);

        name.setText(place.title);
        description.setText(place.description);
        location.setText(place.location);
        return dialog;
    }

    public static class Builder {
        private Place place;

        public Builder setPlace(Place place) {
            this.place = place;
            return this;
        }

        public WonderPlaceDetailDialog build() {
            WonderPlaceDetailDialog fragment = new WonderPlaceDetailDialog();
            Bundle args = new Bundle();
            args.putParcelable(ARG_PLACE, place);
            fragment.setArguments(args);
            return fragment;
        }
    }


}
