package com.lixinxin.updateapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by android on 2016/9/19.
 */
public class AppUtils {

    /**
     * 获取app 版本号
     *
     * @param context
     * @return
     */
    public static String getVersionCode(Context context) {
        String versionCode = "1.0.0";
        PackageManager pm = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            versionCode = info.versionName;
        }
        return versionCode;
    }



    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

}
