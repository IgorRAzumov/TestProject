package something.ru.locationphotofinder.view.fragment.map;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface MapView extends MvpView {

    void showLoading();

    void initMap();

    void hideLoading();

    void showMap(GoogleMap googleMap);

    void drawMarker(LatLng latLang);

    void drawMarker(double longitude, double latitude);

    void checkLocationPermission();

    void showLocation();

    void showNotPermissionsMessage();

    void showLocationSettingsError();

    void resolveLocationException(ApiException apiException);

    void showPhotos(double latitude, double longitude);
}
