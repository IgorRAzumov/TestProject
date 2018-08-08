package something.ru.locationphotofinder.view.fragment.autocomplete;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import something.ru.locationphotofinder.R;
import something.ru.locationphotofinder.presenter.PlaceAutocompletePresenter;
import something.ru.locationphotofinder.view.adapter.PlaceAutocompleteAdapter;


public class PlaceAutocompleteFragment extends MvpAppCompatFragment implements PlaceAutocompleteView {
    public static final String TAG = PlaceAutocompleteFragment.class.getSimpleName();
    private static final LatLngBounds BOUNDS_RUSSIA = new LatLngBounds.Builder()
            .include(new LatLng(55.751244, 37.618423)).build();

    @BindView(R.id.atv_fr_place_ac_auto_complete)
    AutoCompleteTextView placeAutocompleteText;

    @InjectPresenter
    PlaceAutocompletePresenter placeAutocompletePresenter;

    private PlaceAutocompleteAdapter placeAdapter;
    private Unbinder unbinder;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    public PlaceAutocompleteFragment() {

    }

    public static PlaceAutocompleteFragment newInstance() {
        return new PlaceAutocompleteFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.error_implement_fr_interact));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_autocomplete, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    @Override
    public void init() {
        Context context = getContext();
        if (context == null) {
            return;
        }

        placeAdapter = new PlaceAutocompleteAdapter(context, Places.getGeoDataClient(context),
                BOUNDS_RUSSIA, null);
        placeAutocompleteText.setAdapter(placeAdapter);
        placeAutocompleteText.setThreshold(3);
        placeAutocompleteText.setOnItemClickListener(createPlaceClickListener());

    }

    private AdapterView.OnItemClickListener createPlaceClickListener() {
        return (parent, view, position, id) -> {
            onFragmentInteractionListener.hideKeyboard(view.getApplicationWindowToken());
            placeAutocompletePresenter.onPlaceSelected(placeAdapter.getItem(position));
        };
    }

    public interface OnFragmentInteractionListener {
        void hideKeyboard(IBinder applicationWindowToken);
    }
}
