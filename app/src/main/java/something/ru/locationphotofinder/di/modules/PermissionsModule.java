package something.ru.locationphotofinder.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import something.ru.locationphotofinder.model.permissions.IPermissionsHelper;
import something.ru.locationphotofinder.model.permissions.android.PermissionsHelper;

@Singleton
@Module
public class PermissionsModule {

    @Provides
    public IPermissionsHelper<Context> permissionsHelper() {
        return new PermissionsHelper();
    }
}
