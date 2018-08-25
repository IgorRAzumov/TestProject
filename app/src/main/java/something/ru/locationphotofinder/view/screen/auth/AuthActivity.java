package something.ru.locationphotofinder.view.screen.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import butterknife.BindView;
import butterknife.ButterKnife;
import something.ru.locationphotofinder.App;
import something.ru.locationphotofinder.R;
import something.ru.locationphotofinder.presenter.AuthPresenter;
import something.ru.locationphotofinder.view.screen.main.MainActivity;

import static com.vk.sdk.VKAccessToken.SCOPE;

public class AuthActivity extends MvpAppCompatActivity implements AuthView {
    @BindView(R.id.bt_fr_main_sign_in)
    Button signInButton;
    @BindView(R.id.bt_fr_main_no_sign_in)
    Button noSignInButton;

    @InjectPresenter
    AuthPresenter authPresenter;

    @ProvidePresenter
    AuthPresenter providePresenter() {
        AuthPresenter presenter = new AuthPresenter();
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
    }

    @Override
    public void initUi() {
        signInButton.setOnClickListener(view -> {
            authPresenter.buttonSignInClick();
        });
    }

    @Override
    public void signIn(String[] scope) {
        VKSdk.login(this, SCOPE);
    }

    @Override
    public void showMainScreen() {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSignInError() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                authPresenter.onResult(res);
            }

            @Override
            public void onError(VKError error) {
                authPresenter.onError();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
