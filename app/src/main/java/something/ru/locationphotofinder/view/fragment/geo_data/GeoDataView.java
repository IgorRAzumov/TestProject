package something.ru.locationphotofinder.view.fragment.geo_data;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface GeoDataView extends MvpView {

    void init();

    void showPhotos(double latitude, double longitude);

    void errorLoadPlaceInfo();
}
