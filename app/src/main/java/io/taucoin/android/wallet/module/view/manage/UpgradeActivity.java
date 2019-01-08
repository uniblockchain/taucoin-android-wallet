package io.taucoin.android.wallet.module.view.manage;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.naturs.logger.Logger;
import com.mofei.tau.R;

import io.taucoin.android.wallet.MyApplication;
import io.taucoin.android.wallet.base.BaseActivity;
import io.taucoin.android.wallet.base.TransmitKey;
import io.taucoin.android.wallet.module.bean.VersionBean;
import io.taucoin.android.wallet.module.service.UpgradeService;
import io.taucoin.android.wallet.module.view.SplashActivity;
import io.taucoin.android.wallet.util.ActivityUtil;
import io.taucoin.android.wallet.util.PermissionUtils;
import io.taucoin.android.wallet.widget.download.DownloadManager;
import io.taucoin.foundation.util.ActivityManager;
import io.taucoin.foundation.util.AppUtil;

/**
 * Description: version upgrade activity
 * Author: yang
 * Date: 2019/1/5 09:08
 */

public class UpgradeActivity extends BaseActivity implements ServiceConnection {

    private DownloadManager mDownloadManager;
    private VersionBean mVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null != savedInstanceState){
            if(null != mDownloadManager ){
                mDownloadManager.closeDialog();
            }
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ActivityUtil.startActivity(intent, this, SplashActivity.class);

        }
        mVersion = getIntent().getParcelableExtra(TransmitKey.BEAN);
        Logger.d("Upgrade show dialog start");
        if(mVersion != null && ActivityManager.getInstance().getActivitySize() > 0 &&
                AppUtil.isOnForeground(MyApplication.getInstance())){
            mDownloadManager = new DownloadManager();
            showUpGradeDialog();
        }else{
            Logger.d("Upgrade stop on onCreate");
            UpgradeService.stopUpdateService();
            finish();
        }
    }

    private void showUpGradeDialog() {
        Logger.d("show upgrade dialog for user");
        mDownloadManager.showUpGradeDialog(this, mVersion);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.REQUEST_PERMISSIONS_RECORD_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mDownloadManager.isFileExists(mVersion)){
                        mDownloadManager.showUpGradeDialog(UpgradeActivity.this, mVersion);
                    }else{
                        mDownloadManager.startDownload(UpgradeActivity.this);
                    }
                }else{
                    PermissionUtils.checkUserBanPermission(UpgradeActivity.this, permissions[0], R.string.permission_tip_upgrade_never_ask_again);
                }
                break;

            default:
                break;
        }
    }

    /**
     * activity and service connected
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Logger.d("activity and service connected");
        UpgradeService.UpgradeServiceBinder mUpgradeServiceBinder = (UpgradeService.UpgradeServiceBinder) service;
        mUpgradeServiceBinder.setUpgradeProgressListener(mDownloadManager.getOnUpgradeProgressListener());
    }


    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}