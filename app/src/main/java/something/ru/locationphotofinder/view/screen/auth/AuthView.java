package something.ru.locationphotofinder.view.screen.auth;

import com.arellomobile.mvp.MvpView;

public interface AuthView extends MvpView {
    void initUi();

    void signIn(String[] scope);

    void showMainScreen();

    void showSignInError();
}
