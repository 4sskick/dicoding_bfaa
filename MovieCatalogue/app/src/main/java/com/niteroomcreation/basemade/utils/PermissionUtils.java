package com.niteroomcreation.basemade.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Septian Adi Wijaya on 05/11/19
 *
 * NOT used yet, only access internal storage for now
 */
public class PermissionUtils implements EasyPermissions.PermissionCallbacks {

    private Context context;
    private PermissionUtilsListener listener;

    public PermissionUtils(Context context, PermissionUtilsListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {

    }

    interface PermissionUtilsListener {
        void granted();

        void denied();
    }
}
