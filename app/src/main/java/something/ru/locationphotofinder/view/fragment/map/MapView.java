package something.ru.locationphotofinder.view.fragment.map;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface MapView extends MvpView {

    void showLoading();

    void initMap();

    void hideLoading();

    void showMap(GoogleMap googleMap);

    void drawMarker(LatLng latLang);

    void checkLocationPermission();

    void showLocation();

    void showNotPermissionsMessage();
}
