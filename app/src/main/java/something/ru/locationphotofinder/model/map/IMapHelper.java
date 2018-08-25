package something.ru.locationphotofinder.model.map;


import io.reactivex.Observable;

public interface IMapHelper<T, M, L> {

    void init(T map, M markerIcon);

    Observable<L> registerMapClickListener();

    void drawMarker(L latLang);

    void drawMarker(double longitude, double latitude);

    void setEnabledLocation();
}
