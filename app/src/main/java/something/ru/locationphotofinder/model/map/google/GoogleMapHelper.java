package something.ru.locationphotofinder.model.map.google;

import android.annotation.SuppressLint;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.Observable;
import something.ru.locationphotofinder.model.map.IMapHelper;

public class GoogleMapHelper implements IMapHelper<GoogleMap, BitmapDescriptor, LatLng> {
    private GoogleMap googleMap;
    private Marker marker;
    private MarkerOptions icon;
    private boolean myLocationEnabled;


    private void setupMap() {
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        setupMyLocationLayer();
    }

    @Override
    public void init(GoogleMap map, BitmapDescriptor markerIcon) {
        if (googleMap != null) {
            googleMap.setOnMapClickListener(null);
        }
        icon = new MarkerOptions().icon(markerIcon);
        googleMap = map;
        setupMap();
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
    }


    @Override
    public void setEnabledLocation() {
        myLocationEnabled = true;
        if (googleMap != null) {
            setupMyLocationLayer();
        }
    }

    @SuppressLint("MissingPermission")
    private void setupMyLocationLayer() {
        googleMap.setMyLocationEnabled(myLocationEnabled);
    }
}
