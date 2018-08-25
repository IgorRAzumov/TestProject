package something.ru.locationphotofinder.model.place.google;

import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Single;
import something.ru.locationphotofinder.model.place.IPlaceHelper;
import timber.log.Timber;

public class GooglePlaceHelper implements IPlaceHelper<AutocompletePrediction, Place> {
    private static final int TIME_FOR_TASK_AWAIT = 60;
    private static final int SELECTED_PLACE_NUMBER = 0;
    private static final LatLngBounds BOUNDS_RUSSIA = new LatLngBounds.Builder()
            .include(new LatLng(55.751244, 37.618423)).build();

    private final GeoDataClient geoDataClient;
    private AutocompleteFilter placeFilter;

    public GooglePlaceHelper(GeoDataClient geoDataClient) {
        this.geoDataClient = geoDataClient;
        placeFilter = null;
    }

    @Override
    public ArrayList<AutocompletePrediction> loadAutocompletePlaces(String constraint) {
        Task<AutocompletePredictionBufferResponse> results = geoDataClient
                .getAutocompletePredictions(constraint, BOUNDS_RUSSIA, placeFilter);
        try {
            Tasks.await(results, TIME_FOR_TASK_AWAIT, TimeUnit.SECONDS);
            return DataBufferUtils.freezeAndClose(results.getResult());
        } catch (ExecutionException | InterruptedException | TimeoutException | RuntimeException e) {
            Timber.e(e);
            return null;
        }
    }

    @Override
    public Single<Place> loadPlaceById(String selectedPlaceId) {
        return Single.create(emitter -> {
            geoDataClient.getPlaceById(selectedPlaceId)
                    .addOnSuccessListener(places -> {
                        List<Place> placesList = DataBufferUtils.freezeAndClose(places);
                        emitter.onSuccess(placesList.get(SELECTED_PLACE_NUMBER));
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }
}
