package something.ru.locationphotofinder.model.repository.photos;

import com.vk.sdk.api.model.VKApiPhoto;

import java.util.List;

import io.reactivex.Single;

public interface IPhotosRepository {
    Single<List<VKApiPhoto>> loadPhotos(double latitude, double longitude);
}
