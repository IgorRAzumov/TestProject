package something.ru.locationphotofinder.model.repository.geo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IGeoRepository<L, A, P> {
    Observable<L> startListenLocation();

    Completable checkLocationResponse();

    List<A> loadAutocompletePlaces(String constraint);

    Single<P> loadPlaceById(String placeId);
}
