package something.ru.locationphotofinder.model.permissions;

public interface IPermissionsHelper<T> {
    boolean checkLocationPermission(T context);

    String[] getLocationPermiss();

    int getLocationPermissReqCode();

    boolean isPermissReqResultGranted(int requestCode, String[] permissions, int[] grantResults);
}
