package net.jfun.legato;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.jfun.legato.login.LoginActivity;
import net.jfun.legato.login.SignupActivity;
import net.jfun.legato.util.PermissionUtils;


public class PermissionActivity extends AppCompatActivity {

    Context mContext;

    public static final String[] PERMISSION_LIST = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        mContext = PermissionActivity.this;

        if(isCheckPermission() == false){
            requestPermissions(PERMISSION_LIST);
        } else {
//            Intent intent = new Intent(mContext, LoginActivity.class);
            Intent intent = new Intent(mContext, SplashActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean grant = true;
        for(int result : grantResults) {
            if(result == PackageManager.PERMISSION_DENIED) {
                grant = false;
                break;
            }
        }
        if(!grant) {
            Toast.makeText(this, getString(R.string.permission_require), Toast.LENGTH_SHORT).show();
            requestPermissions(PERMISSION_LIST);
            finish();
        }else {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean isCheckPermission(){
        for(String permission : PERMISSION_LIST) {
            if(PermissionUtils.hasPermission(PermissionActivity.this, permission) == false){
                return false;
            }
        }
        return true;
    }

    public void requestPermissions(String[] permissions) {
        PermissionUtils.requestPermissions(this, permissions, 0);
    }
}
