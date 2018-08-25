package something.ru.locationphotofinder.view.fragment.geo_data;

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
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import something.ru.locationphotofinder.App;
import something.ru.locationphotofinder.R;
import something.ru.locationphotofinder.presenter.geo_data.GeoDataPresenter;
import something.ru.locationphotofinder.view.adapter.PlaceAutocompleteAdapter;


public class GeoDataFragment extends MvpAppCompatFragment implements GeoDataView {
    public static final String TAG = GeoDataFragment.class.getSimpleName();

    @BindView(R.id.atv_fr_place_ac_auto_complete)
    AutoCompleteTextView placeAutocompleteText;

    @InjectPresenter
    GeoDataPresenter geoDataPresenter;

    private Unbinder unbinder;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    public GeoDataFragment() {

    }

    public static GeoDataFragment newInstance() {
        return new GeoDataFragment();
    }

    @ProvidePresenter
    GeoDataPresenter providePresenter() {
        GeoDataPresenter presenter = new GeoDataPresenter(
                AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
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
    public void init() {
        Context context = getContext();
        if (context == null) {
            return;
        }

        placeAutocompleteText.setAdapter(new PlaceAutocompleteAdapter(context, geoDataPresenter));
        placeAutocompleteText.setThreshold(3);
        placeAutocompleteText.setOnItemClickListener(createPlaceClickListener());
    }

    @Override
    public void showPhotos(double latitude, double longitude) {
        onFragmentInteractionListener.showPhotos(latitude, longitude);
    }

    @Override
    public void errorLoadPlaceInfo() {

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


    private AdapterView.OnItemClickListener createPlaceClickListener() {
        return (parent, view, position, id) -> {
            onFragmentInteractionListener.hideKeyboard(view.getApplicationWindowToken());
            geoDataPresenter.onPlaceSelected(position);
        };
    }

    public interface OnFragmentInteractionListener {
        void hideKeyboard(IBinder applicationWindowToken);

        void showPhotos(double latitude, double longitude);
    }
}
