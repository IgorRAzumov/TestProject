package something.ru.locationphotofinder.model.repository.photos;

import com.vk.sdk.api.model.VKApiPhoto;

import java.util.List;

import io.reactivex.Single;
import something.ru.locationphotofinder.model.remote.INetworkHelper;
import something.ru.locationphotofinder.model.repository.auth.IAuthRepository;

public class PhotosRepository implements IPhotosRepository {
    private final INetworkHelper networkHelper;
    private final IAuthRepository authRepository;

    private String startTime;
    private String endTime;
    private String radius;
    private String sort;

    public PhotosRepository(INetworkHelper networkHelper, IAuthRepository authRepository) {
        this.networkHelper = networkHelper;
        this.authRepository = authRepository;
    }

    @Override
    public Single<List<VKApiPhoto>> loadPhotos(double latitude, double longitude) {
        return networkHelper.loadPhotos(latitude, longitude, startTime, endTime, radius, sort);
    }
}
