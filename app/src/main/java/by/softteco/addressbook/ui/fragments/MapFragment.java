package by.softteco.addressbook.ui.fragments;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import by.softteco.addressbook.R;
import by.softteco.addressbook.api.ApiRequest;
import by.softteco.addressbook.api.response.ResponseObject;
import by.softteco.addressbook.database.dao.PlaceDao;
import by.softteco.addressbook.database.daoimpl.PlaceDaoImpl;
import by.softteco.addressbook.database.entity.PlaceEntity;
import by.softteco.addressbook.utils.DecodePolylines;
import rx.Observable;
import rx.Observer;

public class MapFragment extends BaseFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final int LAYOUT = R.layout.fragment_map;

    private static final String TAG = MapFragment.class.getSimpleName();

    private final int INTERVAL = 5000;
    private final int FASTEST_INTERVAL = 3000;
    private final int ZOOM = 17;
    private static final String EXTRA_PLACE_NAME = "by.softteco.addressbook.ui.place_name";

    private PlaceDao mPlaceDao;
    private List<PlaceEntity> mPlaceEntities = new ArrayList<>();
    private String mName;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mLatLng;
    private MapView mMapView;
    private GoogleMap mGoogleMap;

    private SupportPlaceAutocompleteFragment mAutocompleteFragment;
    private ProgressDialog mProgressDialog;


    public static MapFragment newInstance(String name) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_PLACE_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        mPlaceDao = PlaceDaoImpl.getInstance();
        mName = getArguments().getString(EXTRA_PLACE_NAME);

        mAutocompleteFragment = (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        mAutocompleteFragment.setOnPlaceSelectedListener(onPlaceSelectedListener);
        mAutocompleteFragment.setFilter(new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE).build());
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                .setCountry("BY")
                .build();
        mAutocompleteFragment.setFilter(autocompleteFilter);

        mMapView = (MapView) view.findViewById(R.id.map_view);
        mMapView.setClickable(true);
        mMapView.setFocusable(true);
        mMapView.setDuplicateParentStateEnabled(false);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);
        return view;
    }

    private PlaceSelectionListener onPlaceSelectedListener = new PlaceSelectionListener() {
        @Override
        public void onPlaceSelected(Place place) {

            createMarker(place.getLatLng().latitude, place.getLatLng().longitude, place.getName().toString());

            PlaceEntity placeEntity = new PlaceEntity();
            placeEntity.setAddress(place.getAddress().toString());
            placeEntity.setName(place.getName().toString());
            placeEntity.setLatitude(place.getLatLng().latitude);
            placeEntity.setLongitude(place.getLatLng().longitude);
            mPlaceEntities.add(placeEntity);
        }

        @Override
        public void onError(Status status) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.tool_tip_layout, null);

                LatLng latLng = marker.getPosition();

                TextView name = (TextView) view.findViewById(R.id.address);
                name.setText(marker.getSnippet());

                TextView coordinates = (TextView) view.findViewById(R.id.coordinates);
                coordinates.setText(getString(R.string.coordinates, latLng.latitude, latLng.longitude));

                return view;
            }
        });
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //TODO
                for (PlaceEntity placeEntity : mPlaceEntities) {
                    if (placeEntity.getName().equals(marker.getSnippet())) {
//                        String msg;
//                        if (mPlaceDao.getPlaceByName(placeEntity.getName()) == null) {
//                            mPlaceDao.addPlace(placeEntity);
//                            msg = getString(R.string.address_was_saved) + placeEntity.getName();
//                        } else {
//                            msg = getString(R.string.already_in_db);
//                        }
//                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                        buildPath(placeEntity.getName());

                    }
                }

                marker.hideInfoWindow();
            }
        });

        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    private void buildPath(String endAddress) {
        mProgressDialog = ProgressDialog.show(getContext(), getString(R.string.query_executed), getString(R.string.wait));
        final List<LatLng> routes = new ArrayList<>();
        Observable<ResponseObject> observable = ApiRequest.getInstance().getApi().getPath("vulica Karvata 29a", endAddress, getString(R.string.google_maps_key));
        Observer<ResponseObject> observer = new Observer<ResponseObject>() {
            @Override
            public void onCompleted() {
                mProgressDialog.dismiss();
                buildDirection(routes);
            }

            @Override
            public void onError(Throwable e) {
                mProgressDialog.dismiss();
                Log.i(TAG, "Error: " + e);
            }

            @Override
            public void onNext(ResponseObject responseObject) {
                routes.clear();
                routes.addAll(DecodePolylines.decodePolylines(responseObject.getRoute().get(0).getPolyline().getPoints()));
            }
        };
        bindObservable(observable, observer);
    }

    private void buildDirection(List<LatLng> routes) {
        PolylineOptions polylineOptions = new PolylineOptions()
                .geodesic(true)
                .color(Color.BLUE)
                .width(10f);
        for (int i = 0; i < routes.size(); i++)
            polylineOptions.add(routes.get(i));

        mGoogleMap.addPolyline(polylineOptions);
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null && mName == null) {
            mLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            moveCamera(mLatLng);
        }
        if (mName != null) {
            PlaceEntity placeEntity = mPlaceDao.getPlaceByName(mName);
            double lat = placeEntity.getLatitude();
            double lon = placeEntity.getLongitude();

            moveCamera(new LatLng(lat, lon));
            createMarker(lat, lon, placeEntity.getName());
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    private void moveCamera(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(ZOOM)
                .build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    protected void createMarker(double latitude, double longitude, String title) {
        mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .snippet(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        moveCamera(new LatLng(latitude, longitude));

    }

}
