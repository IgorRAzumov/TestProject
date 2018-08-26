package something.ru.locationphotofinder.model.repository.photos;

import com.vk.sdk.api.model.VKApiPhoto;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import something.ru.locationphotofinder.model.remote.INetworkHelper;
import something.ru.locationphotofinder.model.repository.auth.IAuthRepository;

public class PhotosRepository implements IPhotosRepository {
    private static final String START_TIME="";
    private static final String RADIUS="";
    private static final String SORT_BY="";

    private final INetworkHelper networkHelper;
    private final IAuthRepository authRepository;

    private String endTime;

    public PhotosRepository(INetworkHelper networkHelper, IAuthRepository authRepository) {
        this.networkHelper = networkHelper;
        this.authRepository = authRepository;
        endTime = String.valueOf(new Date().getTime());
    }

    @Override
    public Single<List<VKApiPhoto>> loadPhotos(double latitude, double longitude) {
        return networkHelper.loadPhotos(latitude, longitude, START_TIME, endTime, RADIUS, SORT_BY);
    }
}
