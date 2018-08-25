package something.ru.locationphotofinder.view.screen.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import something.ru.locationphotofinder.R;
import something.ru.locationphotofinder.presenter.MainPresenter;
import something.ru.locationphotofinder.view.fragment.geo_data.GeoDataFragment;
import something.ru.locationphotofinder.view.fragment.map.MapFragment;
import something.ru.locationphotofinder.view.screen.photos.PhotosActivity;

public class MainActivity extends MvpAppCompatActivity implements MainView,
        MapFragment.OnFragmentInteractionListener,
        GeoDataFragment.OnFragmentInteractionListener {

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
                .replace(R.id.fl_activity_main_frame, GeoDataFragment.newInstance(),
                        GeoDataFragment.TAG)
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

    @Override
    public void showPhotos(double latitude, double longitude) {
        Intent intent = new Intent(this, PhotosActivity.class);
        intent.putExtra(getString(R.string.latitude_key), latitude);
        intent.putExtra(getString(R.string.longitude_key), longitude);
        startActivity(intent);
    }
}
