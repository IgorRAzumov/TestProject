package something.ru.locationphotofinder;

import android.app.Application;
import android.content.Intent;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;

import something.ru.locationphotofinder.di.AppComponent;
import something.ru.locationphotofinder.di.DaggerAppComponent;
import something.ru.locationphotofinder.di.modules.AppModule;
import something.ru.locationphotofinder.view.screen.auth.AuthActivity;
import timber.log.Timber;

public class App extends Application {
    private static App instance;

    private final VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    private AppComponent appComponent;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        instance = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        vkAccessTokenTracker.startTracking();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
