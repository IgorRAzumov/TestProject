package something.ru.locationphotofinder.view.screen.main;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import something.ru.locationphotofinder.R;
import something.ru.locationphotofinder.presenter.MainPresenter;
import something.ru.locationphotofinder.view.fragment.autocomplete.PlaceAutocompleteFragment;
import something.ru.locationphotofinder.view.fragment.map.MapFragment;

public class MainActivity extends MvpAppCompatActivity implements MainView,
        MapFragment.OnFragmentInteractionListener,
        PlaceAutocompleteFragment.OnFragmentInteractionListener {

    @InjectPresenter
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void showMapFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_main_map_container, MapFragment.newInstance(),
                        MapFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void showAutocompleteFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_activity_main_frame, PlaceAutocompleteFragment.newInstance(),
                        PlaceAutocompleteFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void hideKeyboard(IBinder applicationWindowToken) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (in != null) {
            in.hideSoftInputFromWindow(applicationWindowToken, 0);
        }
    }
}
