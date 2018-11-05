package com.lixinxin.updateapp.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/1/4.
 */
public class ToastUtils {
	private ToastUtils() {
	}

	public static void showToast(Context context, String toast) {
		if (null == mToast) {
			mToast = Toast.makeText(context.getApplicationContext(), toast, Toast.LENGTH_SHORT);
			mToast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			mToast.setText(toast);
		}
		mToast.show();
	}

	public static void cancel() {
		if (null != mToast) {
			mToast.cancel();

		}
	}

	public static Toast mToast = null;

}
