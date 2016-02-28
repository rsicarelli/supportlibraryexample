package br.com.rsicarelli.supportlibraryexample.map;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.rsicarelli.supportlibraryexample.R;
import br.com.rsicarelli.supportlibraryexample.data.WonderPlacesRepositories;
import br.com.rsicarelli.supportlibraryexample.data.WonderPlacesServiceApiImpl;
import br.com.rsicarelli.supportlibraryexample.data.wonderplaces.Place;
import br.com.rsicarelli.supportlibraryexample.data.wonderplaces.WonderPlaces;
import br.com.rsicarelli.supportlibraryexample.presentation.WonderPlaceDetailDialog;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, MapContract.View {

    private static final float DEFAULT_ZOOM = 15;

    @Bind(R.id.bottom_sheet)
    LinearLayout bottomSheet;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private GoogleMap googleMap;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private BottomSheetDialog bottomSheetDialog;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    WonderPlacesAdapter.PlaceItemListener itemListener = new WonderPlacesAdapter.PlaceItemListener() {
        @Override
        public void onPlaceClick(Place place) {
            updateMapPosition(place);
            collapseBottomSheetView();
        }
    };

    private void collapseBottomSheetView() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, root);
        initUi();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MapPresenter actionsListener = new MapPresenter(WonderPlacesRepositories.getInMemoryRepoInstance(
                new WonderPlacesServiceApiImpl()), this);
        actionsListener.loadWonderPlaces();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void initUi() {
        setUpMapIfNeeded();
        setUpBottomSheet();
    }

    private void setUpMapIfNeeded() {
        if (googleMap == null) {
            SupportMapFragment supportMapFragment = (SupportMapFragment)
                    getChildFragmentManager().findFragmentById(R.id.map);
            supportMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    private void setUpBottomSheet() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    }

    @OnClick(R.id.select_a_place)
    public void clickSelectAPlace(View view) {
        switch (bottomSheetBehavior.getState()) {
            case BottomSheetBehavior.STATE_EXPANDED:
                collapseBottomSheetView();
                break;
            case BottomSheetBehavior.STATE_COLLAPSED:
            default:
                showBottomSheetView();
        }
    }

    private void showBottomSheetView() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        if (bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
        }
    }

    private void showBottomSheetDialog() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            collapseBottomSheetView();
        }

        bottomSheetDialog = new BottomSheetDialog(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
        //recyclerView.setAdapter(new WonderPlacesAdapter(createItems(), itemListener);

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomSheetDialog = null;
            }
        });
    }

    @Override
    public void showWonderPlaceDetail(LatLng latLng) {
        collapseBottomSheetView();

        //TODO move map and set up the map
    }

    @Override
    public void setUpWonderPlaces(WonderPlaces wonderPlaces) {
        recyclerView.setAdapter(new WonderPlacesAdapter(wonderPlaces, itemListener));
    }

    @Override
    public void updateMapPosition(final Place place) {
        LatLng latLng = new LatLng(place.latLng.lat, place.latLng.lng);

        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    collapseBottomSheetView();
                }

                WonderPlaceDetailDialog dialog = new WonderPlaceDetailDialog.Builder()
                        .setPlace(place)
                        .build();
                dialog.show(getChildFragmentManager(), MapFragment.class.getCanonicalName());
                return false;
            }
        });
    }

    public static class WonderPlacesAdapter extends RecyclerView.Adapter<WonderPlacesAdapter.ViewHolder> {

        private WonderPlaces wonderPlaces;
        private PlaceItemListener mListener;

        public WonderPlacesAdapter(WonderPlaces wonderPlaces, PlaceItemListener listener) {
            this.wonderPlaces = wonderPlaces;
            mListener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_place, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setData(wonderPlaces.places.get(position));
        }

        @Override
        public int getItemCount() {
            return wonderPlaces.places.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.title)
            TextView title;

            public Place place;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void setData(Place place) {
                this.place = place;
                title.setText(place.title);
            }

            @OnClick(R.id.place)
            public void onClick(View v) {
                mListener.onPlaceClick(place);
            }
        }

        public interface PlaceItemListener {
            void onPlaceClick(Place place);
        }
    }
}