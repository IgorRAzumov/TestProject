package something.ru.locationphotofinder.view.screen.photos;

import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import something.ru.locationphotofinder.App;
import something.ru.locationphotofinder.R;
import something.ru.locationphotofinder.presenter.PhotosPresenter;

public class PhotosActivity extends MvpAppCompatActivity implements PhotosView {

    @InjectPresenter
    PhotosPresenter presenter;

    @ProvidePresenter
    PhotosPresenter providePresenter() {
        PhotosPresenter presenter = new PhotosPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
    }

    @Override
    public void loadData() {
        Intent intent = getIntent();
        double noPhotosValue = getResources().getInteger(R.integer.no_photos_value);
        presenter.loadPhotos(intent.getDoubleExtra(getString(R.string.latitude_key), noPhotosValue),
                intent.getDoubleExtra(getString(R.string.longitude_key), noPhotosValue),
                noPhotosValue);
    }

    @Override
    public void showErrorSelectPlace() {

    }

    @Override
    public void showErrorLoadPhotosMessage() {

    }
}
