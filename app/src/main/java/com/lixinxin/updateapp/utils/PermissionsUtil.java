package com.lixinxin.updateapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionsUtil {

	public static final int REQUEST_STATUS_CODE = 0x001;
	public static final int REQUEST_PERMISSION_SETTING = 0x002;

	public static String[] PERMISSIONS_GROUP_SORT = { Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA };

	private static PermissionCallbacks callbacks;

	public interface PermissionCallbacks {

		void onPermissionsGranted();

		void onPermissionsDenied(int requestCode, List<String> perms);

	}

	public static void checkAndRequestPermissions(final Activity activity, PermissionCallbacks callback) {
		if (Build.VERSION.SDK_INT >= 23) {
			callbacks = callback;

			ArrayList<String> denidArray = new ArrayList<String>();

			for (String permission : PERMISSIONS_GROUP_SORT) {
				int grantCode = ActivityCompat.checkSelfPermission(activity, permission);
				if (grantCode == PackageManager.PERMISSION_DENIED) {
					denidArray.add(permission);
				}
			}

			if (denidArray.size() > 0) {
				ArrayList<String> denidArrayNew = new ArrayList<String>();
				denidArrayNew.add(denidArray.get(0));
				String[] denidPermissions = denidArrayNew.toArray(new String[denidArrayNew.size()]);
				requestPermissions(activity, denidPermissions);
			} else {
				callbacks.onPermissionsGranted();
			}

		}
	}

	/**
	 * 关于shouldShowRequestPermissionRationale函数的一点儿注意事项：
	 * ***1).应用安装后第一次访问，则直接返回false；
	 * ***2).第一次请求权限时，用户Deny了，再次调用shouldShowRequestPermissionRationale()，
	 * 则返回true； ***3).第二次请求权限时，用户Deny了，并选择了“dont ask me
	 * again”的选项时，再次调用shouldShowRequestPermissionRationale()时，返回false；
	 * ***4).设备的系统设置中，禁止了应用获取这个权限的授权，则调用shouldShowRequestPermissionRationale()，
	 * 返回false。
	 */
	public static boolean showRationaleUI(Activity activity, String permission) {
		return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
	}

	/**
	 * 对权限字符串数组中的所有权限进行申请授权，如果用户选择了“dont ask me again”，则不会弹出系统的Permission申请授权对话框
	 */
	public static void requestPermissions(Activity activity, String[] permissions) {
		ActivityCompat.requestPermissions(activity, permissions, REQUEST_STATUS_CODE);
	}

	public static boolean checkSinglePermissions(Activity activity, String permission) {
		if (Build.VERSION.SDK_INT >= 23) {
			ArrayList<String> denidArray = new ArrayList<>();
			int grantCode = ActivityCompat.checkSelfPermission(activity, permission);
			if (grantCode == PackageManager.PERMISSION_DENIED) {
				denidArray.add(permission);
			}

			if (denidArray.size() > 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 用来判断，App是否是首次启动：
	 * ***由于每次调用shouldShowRequestPermissionRationale得到的结果因情况而变，因此必须判断一下App是否首次启动
	 * ，才能控制好出现Dialog和SnackBar的时机
	 */
	public static boolean isAppFirstRun(Activity activity) {
		SharedPreferences sp = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (sp.getBoolean("first_run", true)) {
			editor.putBoolean("first_run", false);
			editor.commit();
			return true;
		} else {
			editor.putBoolean("first_run", false);
			editor.commit();
			return false;
		}
	}
}
