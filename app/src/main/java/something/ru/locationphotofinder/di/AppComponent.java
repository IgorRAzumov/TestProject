package something.ru.locationphotofinder.di;

import javax.inject.Singleton;

import dagger.Component;
import something.ru.locationphotofinder.di.modules.AppModule;
import something.ru.locationphotofinder.di.modules.LocationModule;
import something.ru.locationphotofinder.di.modules.MapHelperModule;
import something.ru.locationphotofinder.di.modules.PermissionsModule;
import something.ru.locationphotofinder.di.modules.RepositoryModule;
import something.ru.locationphotofinder.view.fragment.map.MapFragment;

@Singleton
@Component(modules = {AppModule.class, RepositoryModule.class, MapHelperModule.class,
        PermissionsModule.class, LocationModule.class})
public interface AppComponent {

    void inject(MapFragment mapFragment);
}
