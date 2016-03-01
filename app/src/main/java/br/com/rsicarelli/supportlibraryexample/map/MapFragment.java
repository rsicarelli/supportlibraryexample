package br.com.rsicarelli.supportlibraryexample.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
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

import br.com.rsicarelli.supportlibraryexample.presentation.DividerItemDecoration;
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
public class MapFragment extends Fragment implements
        MapContract.View {

    private static final float DEFAULT_ZOOM = 15;

    @Bind(R.id.bottom_sheet)
    LinearLayout bottomSheet;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private GoogleMap map;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private MapPresenter actionsListener;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    WonderPlacesAdapter.PlaceItemListener itemListener = new WonderPlacesAdapter.PlaceItemListener() {
        @Override
        public void onPlaceClick(Place place) {
            updateMapPositionUi(place);
            collapseBottomSheetView();
        }
    };

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
        actionsListener = new MapPresenter(
                WonderPlacesRepositories.getInMemoryRepoInstance(
                        new WonderPlacesServiceApiImpl()), this);
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
        if (map == null) {
            SupportMapFragment supportMapFragment = (SupportMapFragment)
                    getChildFragmentManager().findFragmentById(R.id.map);
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;
                    actionsListener.loadWonderPlaces();
                }
            });
        }
    }

    private void setUpBottomSheet() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.item_divider));

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
    }

    private void collapseBottomSheetView() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void setUpWonderPlacesUi(WonderPlaces wonderPlaces) {
        recyclerView.setAdapter(new WonderPlacesAdapter(wonderPlaces, itemListener));
    }

    @Override
    public void updateMapPositionUi(final Place place) {
        LatLng latLng = new LatLng(place.latLng.lat, place.latLng.lng);

        if (map != null) {
            map.addMarker(new MarkerOptions().position(latLng));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
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