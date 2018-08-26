package something.ru.locationphotofinder.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import something.ru.locationphotofinder.model.repository.photos.IPhotosRepository;
import something.ru.locationphotofinder.view.screen.photos.PhotosView;

@InjectViewState
public class PhotosPresenter extends MvpPresenter<PhotosView> {
    @Inject
    IPhotosRepository photosRepository;

    private Scheduler scheduler;

    public PhotosPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().loadData();
    }

    @SuppressLint("CheckResult")
    public void loadPhotos(double latitude, double longitude, double defaultValue) {
        if (longitude == latitude && longitude == defaultValue) {
            getViewState().showErrorSelectPlace();
            return;
        }

        getViewState().showLoading();
        photosRepository
                .loadPhotos(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(photos -> {
                    getViewState().hideLoading();
                }, throwable -> {
                    getViewState().hideLoading();
                    getViewState().showErrorLoadPhotosMessage();
                });
    }
}
