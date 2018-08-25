package something.ru.locationphotofinder.presenter;

import android.annotation.SuppressLint;
import android.location.Location;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import something.ru.locationphotofinder.model.repository.geo.IGeoRepository;
import something.ru.locationphotofinder.view.fragment.map.MapView;
import timber.log.Timber;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {
    @Inject
    IGeoRepository<Location, AutocompletePrediction, Place> geoRepository;

    private Disposable geoListenDisposable;
    private GoogleMap map;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showLoading();
        getViewState().initMap();
    }

    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getViewState().hideLoading();
        getViewState().checkLocationPermission();
    }

    public void onPermissionsGranted() {
        getViewState().showMap(map);
        startLocationDetect();
    }

    public void onPermissionsNotGranted() {
        getViewState().showNotPermissionsMessage();
        getViewState().showMap(map);
    }

    public void onMapClick(LatLng latLang) {
        getViewState().drawMarker(latLang);
        getViewState().showPhotos(latLang.latitude, latLang.longitude);
    }

    @SuppressLint("CheckResult")
    private void startLocationDetect() {
        geoRepository.checkLocationResponse()
                .subscribe(this::startListenGeo
                        , throwable -> {
                            if (!(throwable instanceof ApiException)) {
                                getViewState().showLocationSettingsError();
                                Timber.e(throwable);
                                return;
                            }
                            ApiException apiException = (ApiException) throwable;
                            if (apiException.getStatusCode() != LocationSettingsStatusCodes
                                    .RESOLUTION_REQUIRED) {
                                getViewState().showLocationSettingsError();
                                Timber.e(throwable);
                                return;
                            }
                            getViewState().resolveLocationException(apiException);
                        });
    }

    private void startListenGeo() {
        geoListenDisposable = geoRepository
                .startListenLocation()
                .subscribe(location -> getViewState().drawMarker(location.getLongitude(),
                        location.getLatitude())
                        , Timber::e);
    }

    public void locationErrorResolve() {
        startListenGeo();
    }

    public void locationErrorNotResolve() {
        getViewState().showLocationSettingsError();
    }
}
