package something.ru.locationphotofinder;

import android.app.Application;

import something.ru.locationphotofinder.di.AppComponent;
import something.ru.locationphotofinder.di.DaggerAppComponent;
import something.ru.locationphotofinder.di.modules.AppModule;

public class App extends Application {
    private static App instance;

    private AppComponent appComponent;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
