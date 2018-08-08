package something.ru.locationphotofinder.model.location;

import android.location.Location;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface ILocationProvider {

    Observable<Location> getLocation();
}
