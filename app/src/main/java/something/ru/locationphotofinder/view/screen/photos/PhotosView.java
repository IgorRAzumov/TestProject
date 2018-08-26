package something.ru.locationphotofinder.view.screen.photos;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface PhotosView extends MvpView {
    void loadData();

    void showErrorSelectPlace();

    void showErrorLoadPhotosMessage();

    void showLoading();

    void hideLoading();
}
