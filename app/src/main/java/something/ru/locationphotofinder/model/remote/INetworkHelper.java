package something.ru.locationphotofinder.model.remote;

import com.vk.sdk.api.model.VKApiPhoto;

import java.util.List;

import io.reactivex.Single;

public interface INetworkHelper {
    Single<List<VKApiPhoto>> loadPhotos(double latitude, double longitude,
                                        String startTime, String endTime, String radius, String sort);
}
