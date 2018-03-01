package org.tyaa.furniturehelper.manager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.tyaa.furniturehelper.manager.common.Utility;

public class PermissionActivity extends AppCompatActivity {
    private int PERMISSION_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permission);

        checkPermission();

        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && permissions.length == 1){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(
                        this
                        , "Application won't be able reading the phone's status"
                        , Toast.LENGTH_LONG
                ).show();
            }
            if (grantResults[1] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(
                        this
                        , "Application won't be able working with contacts"
                        , Toast.LENGTH_LONG
                ).show();
            }
            if (grantResults[2] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(
                        this
                        , "Application won't be able sending sms"
                        , Toast.LENGTH_LONG
                ).show();
            }
            if (grantResults[3] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(
                        this
                        , "Application won't be able using camers"
                        , Toast.LENGTH_LONG
                ).show();
            }

            finish();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void checkPermission(){
        if (!Utility.isThereAPermission(this, Manifest.permission.READ_PHONE_STATE)
            || !Utility.isThereAPermission(this, Manifest.permission.READ_CONTACTS)
            || !Utility.isThereAPermission(this, Manifest.permission.SEND_SMS)
            || !Utility.isThereAPermission(this, Manifest.permission.CAMERA)){

            ActivityCompat.requestPermissions(
                    this
                    , new String[]{
                            Manifest.permission.READ_PHONE_STATE
                            , Manifest.permission.READ_CONTACTS
                            , Manifest.permission.SEND_SMS
                            , Manifest.permission.CAMERA
                    }
                    , PERMISSION_REQUEST_CODE
            );
        } else {
            finish();
        }
    }
}
