package something.ru.locationphotofinder.model.repository.geo;


import android.location.Location;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import something.ru.locationphotofinder.model.location.ILocationProvider;
import something.ru.locationphotofinder.model.place.IPlaceHelper;

public class GeoRepository implements IGeoRepository<Location, AutocompletePrediction, Place> {

    private final ILocationProvider locationProvider;
    private final IPlaceHelper<AutocompletePrediction, Place> placeHelper;


    public GeoRepository(ILocationProvider locationProvider, IPlaceHelper placeHelper) {
        this.locationProvider = locationProvider;
        this.placeHelper = placeHelper;
    }

    @Override
    public Observable<Location> startListenLocation() {
        return locationProvider
                .listenLocation();
    }

    @Override
    public Completable checkLocationResponse() {
        return locationProvider
                .checkLocationResponse();
    }

    @Override
    public ArrayList<AutocompletePrediction> loadAutocompletePlaces(String constraint) {
        return placeHelper.loadAutocompletePlaces(constraint);
    }

    @Override
    public Single<Place> loadPlaceById(String placeId) {
        return placeHelper.loadPlaceById(placeId);
    }
}
