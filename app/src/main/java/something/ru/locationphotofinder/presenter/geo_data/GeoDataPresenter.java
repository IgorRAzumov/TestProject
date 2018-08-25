package something.ru.locationphotofinder.presenter.geo_data;


import android.annotation.SuppressLint;
import android.location.Location;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import something.ru.locationphotofinder.model.repository.geo.IGeoRepository;
import something.ru.locationphotofinder.view.fragment.geo_data.GeoDataView;

@InjectViewState
public class GeoDataPresenter extends MvpPresenter<GeoDataView>
        implements IPlaceAutocompleteAdapterPresenter {
    private final Scheduler scheduler;
    @Inject
    IGeoRepository<Location, AutocompletePrediction, Place> geoRepository;
    private ArrayList<AutocompletePrediction> resultList;

    public GeoDataPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }

    @Override
    public int getCount() {
        return resultList == null ? 0 : resultList.size();
    }


    @Override
    public AutocompletePrediction getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public ArrayList<AutocompletePrediction> loadAutocompletePredictions(String constraint) {
        resultList = new ArrayList<>(geoRepository.loadAutocompletePlaces(constraint));
        return resultList;
    }


    @SuppressLint("CheckResult")
    public void onPlaceSelected(int position) {
        geoRepository.loadPlaceById(resultList.get(position).getPlaceId())
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(place -> {
                            LatLng latLng = place.getLatLng();
                            getViewState().showPhotos(latLng.latitude, latLng.longitude);
                        }
                        , throwable -> getViewState().errorLoadPlaceInfo());
    }
}
