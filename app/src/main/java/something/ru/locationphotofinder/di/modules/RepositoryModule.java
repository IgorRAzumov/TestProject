package something.ru.locationphotofinder.di.modules;

import android.location.Location;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import something.ru.locationphotofinder.model.location.ILocationProvider;
import something.ru.locationphotofinder.model.place.IPlaceHelper;
import something.ru.locationphotofinder.model.remote.INetworkHelper;
import something.ru.locationphotofinder.model.repository.auth.AuthRepository;
import something.ru.locationphotofinder.model.repository.auth.IAuthRepository;
import something.ru.locationphotofinder.model.repository.geo.GeoRepository;
import something.ru.locationphotofinder.model.repository.geo.IGeoRepository;
import something.ru.locationphotofinder.model.repository.photos.IPhotosRepository;
import something.ru.locationphotofinder.model.repository.photos.PhotosRepository;

@Singleton
@Module(includes = {ApiModule.class, LocationModule.class, PlaceHelperModule.class})
public class RepositoryModule {

    @Singleton
    @Provides
    IGeoRepository<Location, AutocompletePrediction, Place> geoRepository(ILocationProvider locationProvider,
                                                                          IPlaceHelper<AutocompletePrediction, Place> placeHelper) {
        return new GeoRepository(locationProvider, placeHelper);
    }

    @Singleton
    @Provides
    IPhotosRepository photosRepository(INetworkHelper networkHelper, IAuthRepository authRepository) {
        return new PhotosRepository(networkHelper, authRepository);
    }

    @Singleton
    @Provides
    IAuthRepository authRepository() {
        return new AuthRepository();
    }
}
