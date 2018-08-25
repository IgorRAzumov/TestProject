package something.ru.locationphotofinder.model.map.google;

import android.annotation.SuppressLint;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.Observable;
import something.ru.locationphotofinder.model.map.IMapHelper;

public class GoogleMapHelper implements IMapHelper<GoogleMap, BitmapDescriptor, LatLng> {
    private static final int STREET_ZOOM = 15;
    private GoogleMap googleMap;
    private Marker marker;
    private MarkerOptions icon;
    private boolean myLocationEnabled;

    @Override
    public void init(GoogleMap map, BitmapDescriptor markerIcon) {
        if (googleMap != null) {
            googleMap.setOnMapClickListener(null);
        }
        icon = new MarkerOptions().icon(markerIcon);
        googleMap = map;
        setupMap();
    }

    @SuppressLint("MissingPermission")
    private void setupMap() {
        if (googleMap != null) {
            UiSettings uiSettings = googleMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(true);
            googleMap.setMyLocationEnabled(myLocationEnabled);
        }
    }

    @Override
    public Observable<LatLng> registerMapClickListener() {
        return Observable
                .create(emitter -> googleMap.setOnMapClickListener(emitter::onNext));
    }

    @Override
    public void drawMarker(LatLng latLang) {
        if (marker == null) {
            marker = googleMap.addMarker(icon.position(latLang));
        } else {
            marker.setPosition(latLang);
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLang));
    }

    @Override
    public void drawMarker(double longitude, double latitude) {
        drawMarker(new LatLng(latitude, longitude));
    }


    @SuppressLint("MissingPermission")
    @Override
    public void setEnabledLocation() {
        myLocationEnabled = true;
        if (googleMap != null) {
            googleMap.setMyLocationEnabled(myLocationEnabled);
        }
    }
}
