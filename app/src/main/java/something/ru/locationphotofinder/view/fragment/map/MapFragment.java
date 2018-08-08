package something.ru.locationphotofinder.view.fragment.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import something.ru.locationphotofinder.App;
import something.ru.locationphotofinder.R;
import something.ru.locationphotofinder.model.map.IMapHelper;
import something.ru.locationphotofinder.model.permissions.IPermissionsHelper;
import something.ru.locationphotofinder.presenter.MapPresenter;


public class MapFragment extends MvpAppCompatFragment implements MapView {
    public static final String TAG = MapFragment.class.getSimpleName();

    @BindView(R.id.pb_fragment_map_loading)
    ProgressBar loadingProgress;

    @InjectPresenter
    MapPresenter mapPresenter;

    @Inject
    IMapHelper<GoogleMap, BitmapDescriptor, LatLng> mapHelper;

    @Inject
    IPermissionsHelper<Context> permissionsHelper;

    private Unbinder unbinder;
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private Disposable disposable;

    public MapFragment() {

    }

    public static MapFragment newInstance() {
        return new MapFragment();
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
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        unbinder = ButterKnife.bind(this, view);
        App.getInstance().getAppComponent().inject(this);
        return view;
    }

    @Override
    public void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.gm_fragment_map_map);
        mapFragment.getMapAsync(createOnMapReadyCallback());
    }

    @SuppressLint("CheckResult")
    @Override
    public void showMap(GoogleMap googleMap) {
        mapHelper.init(googleMap,
                BitmapDescriptorFactory.fromResource(R.drawable.ic_selected_place_marker));
        mapHelper.registerMapClickListener()
                .subscribe(latLang -> mapPresenter.onMapClick(latLang));
    }

    @Override
    public void checkLocationPermission() {
        Context context = getContext();
        if (context == null) {
            return;
        }

        if (permissionsHelper.checkLocationPermission(context)) {
            onPermissionsGranted();
        } else {
            requestPermissions();
        }
    }

    @Override
    public void showLocation() {
        mapHelper.setEnabledLocation();
    }

    @Override
    public void showNotPermissionsMessage() {
     /*   new AlertDialog.Builder(this)
                .setTitle(R.string.permissons_required)
                .setMessage(R.string.permissions_required_message)
                .setPositiveButton("OK", (dialog, which) -> requestPermissions())
                .setOnCancelListener(dialog -> requestPermissions())
                .create()
                .show();*/
    }

    @Override
    public void drawMarker(LatLng latLang) {
        mapHelper.drawMarker(latLang);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (permissionsHelper.isPermissReqResultGranted(requestCode, permissions, grantResults)) {
            onPermissionsGranted();
        } else {
            onPermissionsNotGranted();
        }
    }

    @Override
    public void showLoading() {
        loadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingProgress.setVisibility(View.INVISIBLE);
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

    private void requestPermissions() {
        requestPermissions(permissionsHelper.getLocationPermiss(),
                permissionsHelper.getLocationPermissReqCode());
    }

    private void onPermissionsGranted() {
        mapPresenter.onPermissionsGranted();
    }

    private void onPermissionsNotGranted() {
        mapPresenter.onPermissionsNotGranted();
    }

    private OnMapReadyCallback createOnMapReadyCallback() {
        return googleMap -> mapPresenter.onMapReady(googleMap);
    }

    public interface OnFragmentInteractionListener {
    }
}
