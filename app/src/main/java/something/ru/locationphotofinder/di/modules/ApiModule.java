package something.ru.locationphotofinder.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import something.ru.locationphotofinder.model.remote.INetworkHelper;
import something.ru.locationphotofinder.model.remote.NetworkHelper;

@Singleton
@Module
public class ApiModule {
    @Provides
    public INetworkHelper networkHelper() {
        return new NetworkHelper();
    }
}
