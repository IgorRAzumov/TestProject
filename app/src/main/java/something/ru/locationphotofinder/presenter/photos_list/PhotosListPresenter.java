package something.ru.locationphotofinder.presenter.photos_list;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import something.ru.locationphotofinder.view.fragment.photos_list.PhotosListView;

@InjectViewState
public class PhotosListPresenter extends MvpPresenter<PhotosListView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }
}
