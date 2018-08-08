package something.ru.locationphotofinder.model.permissions.android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import something.ru.locationphotofinder.model.permissions.IPermissionsHelper;

public class PermissionsHelper implements IPermissionsHelper<Context> {
    private static final int LOCATION_PERMISSIONS_REQUEST_CODE = 1;
    private static final String[] LOCATION_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    private boolean locationPermissionsGranted;

    @Override
    public boolean checkLocationPermission(Context context) {
        if (locationPermissionsGranted) {
            return true;
        }

        for (String permission : LOCATION_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String[] getLocationPermiss() {
        return LOCATION_PERMISSIONS;
    }

    @Override
    public int getLocationPermissReqCode() {
        return LOCATION_PERMISSIONS_REQUEST_CODE;
    }

    @Override
    public boolean isPermissReqResultGranted(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != LOCATION_PERMISSIONS_REQUEST_CODE ||
                grantResults.length != LOCATION_PERMISSIONS.length) {
            return false;
        }

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        locationPermissionsGranted = true;
        return true;
    }


}
