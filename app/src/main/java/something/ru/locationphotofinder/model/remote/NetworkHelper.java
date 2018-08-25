package something.ru.locationphotofinder.model.remote;

import android.annotation.SuppressLint;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;

import java.util.List;

import io.reactivex.Single;
import something.ru.locationphotofinder.model.entity.PhotosResponse;

public class NetworkHelper implements INetworkHelper {
    private static final String ALL_ATTEMPT_FAILED = "all attempt failed";
    private static final int FIST_REQUEST_PARAMETR = 0;

    @SuppressLint("StaticFieldLeak")
    public Single<PhotosResponse> loadPhotos(double latitude, double longitude,
                                             String startTime, String endTime, String sort,
                                             String offset,
                                             String count, String radius)  {
        return Single.create(emitter -> {
           getPequest().executeSyncWithListener(new VKRequest.VKRequestListener() {
                        @Override
                        public void onComplete(VKResponse response) {
                            super.onComplete(response);
                            emitter.onSuccess((PhotosResponse) response.parsedModel);
                        }

                        @Override
                        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                            super.attemptFailed(request, attemptNumber, totalAttempts);
                            if(totalAttempts==attemptNumber){
                                emitter.onError(new RuntimeException(ALL_ATTEMPT_FAILED));
                            }
                        }

                        @Override
                        public void onError(VKError error) {
                            super.onError(error);
                            emitter.onError(new RuntimeException(error.errorMessage));
                        }
                    });
           });
    }

    public static VKResponse getPhotos(double latitude, double longitude,
                                       String startTime, String endTime, String sort, String offset,
                                       String count, String radius) {
        VKRequest request = getNoAuthPhotoRequest(latitude, longitude, startTime, endTime,
                sort, offset, count, radius);
        return executePhotoRequest(request);
    }

    private static VKResponse executePhotoRequest(VKRequest request) {
        final VKResponse[] returnedVkResponse = new VKResponse[1];
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                returnedVkResponse[0] = response;
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }

        });
        return returnedVkResponse[0];
    }


    private static VKRequest getNoAuthPhotoRequest( double latitude, double longitude,
                                                    String startTime, String endTime, String sort,
                                                  String offset, String count, String radius) {
       /*   VKRequest request = new VKRequest(R.string.vk_photo_search_request_method_name_api),
                VKParameters.from(
                        VKApiConst.LAT, latitude,
                        VKApiConst.LONG, longitude,
                        R.string.vk_api_const_start_time, startTime,
                        R.string.vk_api_const_end_time, endTime,
                        VKApiConst.SORT, sort,
                        VKApiConst.OFFSET, offset,
                        VKApiConst.COUNT, count,
                        R.string.vk_api_const_radius, radius,
                        VKApiConst.VERSION, R.string.vk_api_version));
        request.setModelClass(VKPhotoArray.class);
        return request;*/return null;
    }

    public VKRequest getPequest() {
        return null;
    }

    @Override
    public Single<List<VKApiPhoto>> loadPhotos(double latitude, double longitude,
                                               String startTime, String endTime,
                                               String radius, String sort) {
        return null;

    }
}
