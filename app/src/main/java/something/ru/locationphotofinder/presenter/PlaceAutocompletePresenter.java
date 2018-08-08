package something.ru.locationphotofinder.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.location.places.AutocompletePrediction;

import something.ru.locationphotofinder.view.fragment.autocomplete.PlaceAutocompleteView;

@InjectViewState
public class PlaceAutocompletePresenter extends MvpPresenter<PlaceAutocompleteView> {
    private String selectedPlaceId;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }

    public void onPlaceSelected(AutocompletePrediction item) {
        selectedPlaceId = item.getPlaceId();
        //отправная точка
    }

}
