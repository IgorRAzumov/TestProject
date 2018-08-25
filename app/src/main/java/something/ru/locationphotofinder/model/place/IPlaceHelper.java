package something.ru.locationphotofinder.model.place;

import java.util.ArrayList;

import io.reactivex.Single;

public interface IPlaceHelper<A, P> {
    ArrayList<A> loadAutocompletePlaces(String constraint);

    Single<P> loadPlaceById(String selectedPlaceId);

}
