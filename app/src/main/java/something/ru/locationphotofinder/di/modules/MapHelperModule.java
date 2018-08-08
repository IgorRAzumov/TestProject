package something.ru.locationphotofinder.di.modules;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

import dagger.Module;
import dagger.Provides;
import something.ru.locationphotofinder.model.map.IMapHelper;
import something.ru.locationphotofinder.model.map.google.GoogleMapHelper;

@Module
public class MapHelperModule {

    @Provides
    IMapHelper<GoogleMap, BitmapDescriptor, LatLng> googleMapHelper() {
        return new GoogleMapHelper();
    }
}
