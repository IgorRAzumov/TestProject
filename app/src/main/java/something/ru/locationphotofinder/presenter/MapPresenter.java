package something.ru.locationphotofinder.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import something.ru.locationphotofinder.view.fragment.map.MapView;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showLoading();
        getViewState().initMap();
    }

    public void onMapReady(GoogleMap googleMap) {
        getViewState().hideLoading();
        getViewState().checkLocationPermission();

        getViewState().showMap(googleMap);
    }

    public void onMapClick(LatLng latLang) {
        getViewState().drawMarker(latLang);
    }


    public void onPermissionsGranted() {
        getViewState().showLocation();
    }

    public void onPermissionsNotGranted() {
        getViewState().showNotPermissionsMessage();
    }
}
