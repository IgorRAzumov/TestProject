package something.ru.locationphotofinder.di.modules;

import android.content.Context;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import something.ru.locationphotofinder.model.place.IPlaceHelper;
import something.ru.locationphotofinder.model.place.google.GooglePlaceHelper;

@Singleton
@Module(includes = AppModule.class)
public class PlaceHelperModule {

    @Singleton
    @Provides
    IPlaceHelper<AutocompletePrediction, Place> placeHelper(GeoDataClient geoDataClient) {
        return new GooglePlaceHelper(geoDataClient);
    }

    @Singleton
    @Provides
    GeoDataClient geoDataClient(Context context) {
        return Places.getGeoDataClient(context);
    }
}
