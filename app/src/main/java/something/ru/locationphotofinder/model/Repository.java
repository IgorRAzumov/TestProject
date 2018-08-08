package something.ru.locationphotofinder.model;


import android.location.Location;

import io.reactivex.Observable;
import something.ru.locationphotofinder.model.location.ILocationProvider;
import something.ru.locationphotofinder.model.remote.NetworkHelper;

public class Repository {
    private final NetworkHelper networkHelper;
    private final ILocationProvider locationProvider;


    private String startTime;
    private String endTime;
    private String radius;
    private String sort;

    public Repository(NetworkHelper networkHelper, ILocationProvider locationProvider) {
        this.networkHelper = networkHelper;
        this.locationProvider = locationProvider;
    }

    public Observable<Location> getLocation() {
        return locationProvider.getLocation();
    }
}
