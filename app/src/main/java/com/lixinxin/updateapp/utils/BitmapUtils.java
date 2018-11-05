package com.lixinxin.updateapp.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class BitmapUtils {

    public static void saveBitmap(Context context, Bitmap Bmp) {
        try {
            File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "evente");
            File file = new File(path, System.currentTimeMillis() + ".png");
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            if (null != fos) {
                Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap;
        bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        v.draw(canvas);
        return bitmap;
    }


}
