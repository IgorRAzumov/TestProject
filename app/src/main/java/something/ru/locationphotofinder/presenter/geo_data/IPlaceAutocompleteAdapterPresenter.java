package something.ru.locationphotofinder.presenter.geo_data;

import com.google.android.gms.location.places.AutocompletePrediction;

import java.util.ArrayList;

public interface IPlaceAutocompleteAdapterPresenter {

    int getCount();

    AutocompletePrediction getItem(int position);

    ArrayList<AutocompletePrediction> loadAutocompletePredictions(String constraint);
}
