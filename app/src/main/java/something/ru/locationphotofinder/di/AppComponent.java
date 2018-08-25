package something.ru.locationphotofinder.di;

import javax.inject.Singleton;

import dagger.Component;
import something.ru.locationphotofinder.di.modules.AppModule;
import something.ru.locationphotofinder.di.modules.LocationModule;
import something.ru.locationphotofinder.di.modules.MapHelperModule;
import something.ru.locationphotofinder.di.modules.PermissionsModule;
import something.ru.locationphotofinder.di.modules.RepositoryModule;
import something.ru.locationphotofinder.presenter.AuthPresenter;
import something.ru.locationphotofinder.presenter.MapPresenter;
import something.ru.locationphotofinder.presenter.PhotosPresenter;
import something.ru.locationphotofinder.presenter.geo_data.GeoDataPresenter;
import something.ru.locationphotofinder.view.fragment.map.MapFragment;

@Singleton
@Component(modules = {AppModule.class, RepositoryModule.class, MapHelperModule.class,
        PermissionsModule.class, LocationModule.class})
public interface AppComponent {

    void inject(MapFragment mapFragment);

    void inject(MapPresenter presenter);

    void inject(GeoDataPresenter presenter);

    void inject(PhotosPresenter presenter);

    void inject(AuthPresenter presenter);
}
