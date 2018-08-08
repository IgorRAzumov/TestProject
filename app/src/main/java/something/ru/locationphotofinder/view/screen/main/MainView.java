package something.ru.locationphotofinder.view.screen.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface MainView extends MvpView{

    void showMapFragment();

    void showAutocompleteFragment();

}
