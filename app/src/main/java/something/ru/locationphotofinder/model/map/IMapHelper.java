package something.ru.locationphotofinder.model.map;


import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Observable;

public interface IMapHelper<T, M, L> {

    void init(T map, M markerIcon);

    Observable<L> registerMapClickListener();

    void drawMarker(L latLang);

    void setEnabledLocation();
}
