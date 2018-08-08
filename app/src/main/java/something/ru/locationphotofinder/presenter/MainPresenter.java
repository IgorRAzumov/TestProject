package something.ru.locationphotofinder.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import something.ru.locationphotofinder.view.screen.main.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showMapFragment();
        getViewState().showAutocompleteFragment();
    }
}
