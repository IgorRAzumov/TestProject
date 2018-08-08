package something.ru.locationphotofinder.model.entity;


import com.vk.sdk.api.model.VKPhotoArray;

public class PhotosResponse {
    private VKPhotoArray photoArray;
    private int totalPhotosCount;

    public PhotosResponse(int totalPhotosCount, VKPhotoArray photoArray) {
        this.photoArray = photoArray;
        this.totalPhotosCount = totalPhotosCount;
    }

    public VKPhotoArray getPhotoArray() {
        return photoArray;
    }

    public int getTotalPhotosCount() {
        return totalPhotosCount;
    }

}
