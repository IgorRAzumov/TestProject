package something.ru.locationphotofinder.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;

import javax.inject.Inject;

import something.ru.locationphotofinder.model.repository.auth.IAuthRepository;
import something.ru.locationphotofinder.view.screen.auth.AuthView;

@InjectViewState
public class AuthPresenter extends MvpPresenter<AuthView> {
    private final static String[] SCOPE = new String[]{VKScope.PHOTOS, VKScope.OFFLINE};

    @Inject
    IAuthRepository authRepository;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initUi();

    }

    public void buttonSignInClick() {
        getViewState().signIn(SCOPE);
    }

    public void onResult(VKAccessToken res) {
        authRepository.setToken(res.accessToken);
        getViewState().showMainScreen();
    }

    public void onError() {
        getViewState().showSignInError();
    }
}
