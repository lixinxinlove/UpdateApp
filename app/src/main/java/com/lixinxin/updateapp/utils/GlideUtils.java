package com.lixinxin.updateapp.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.eventmosh.evente.EventApp;
import com.eventmosh.evente.R;

import java.io.File;
import java.text.DecimalFormat;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * 图片加载
 */
public class GlideUtils {


    public static void load(Context context,
                            String url,
                            ImageView imageView,
                            RequestOptions options) {

        Glide.with(context)
                .load(url)
                .apply(options.placeholder(R.mipmap.ic_launcher))  //添加默认站位图
                .into(imageView);
    }

    public static void load(Context context,
                            String url,
                            ImageView imageView) {

        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static void load(Context context,
                            String url,
                            ImageView imageView, int id) {

        RequestOptions options = new RequestOptions().placeholder(id).error(id).dontAnimate();

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadAsGif(Context context,
                                 String url,
                                 ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static void loadAsGif(Context context,
                                 int id,
                                 ImageView imageView) {
        Glide.with(context)
                .load(id)
                .into(imageView);
    }


    public static void loadCrop(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.loading_default);  //设置图片大小
        Glide.with(context)
                .load(url)
                .apply(options)
                .apply(bitmapTransform(new RoundedCorners(ScreenUtil.dip2px(context, 5f))))
                .into(imageView);
    }

    public static void loadCrop(Context context, String url, ImageView imageView, float crop, int defaultImageId) {
        RequestOptions options = new RequestOptions().placeholder(defaultImageId);  //设置图片大小
        Glide.with(context)
                .load(url)
                .apply(options)
                .apply(bitmapTransform(new RoundedCorners(ScreenUtil.dip2px(context, crop))))
                .into(imageView);
    }

    public static void loadHead(Context context, String url, ImageView imageView) {

        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.icon_def_head_image)
                .error(R.mipmap.icon_def_head_image);

        Glide.with(context)
                .load(url)
                .apply(options)
                .apply(bitmapTransform(new RoundedCorners(ScreenUtil.dip2px(context, 5f))))
                .into(imageView);
    }

    //高斯模糊
    public static void loadBlur(Context context, int id, ImageView imageView) {

        Glide.with(context)
                .load(id)
                .apply(bitmapTransform(new BlurTransformation(25)))
                .into(imageView);
    }

    public static String getCocheSize() {
        return "";
    }


    private static final double GB = 1024L * 1024L * 1024L;// 定义GB的计算常量
    private static final double MB = 1024L * 1024L;// 定义MB的计算常量
    private static final double KB = 1024L;// 定义KB的计算常量

    public static String byteConversionGBMBKB(double kSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        double temp = 0;
        if (kSize / GB >= 1) {
            temp = kSize / GB;
            return df.format(temp) + "GB";
        } else if (kSize / MB >= 1) {
            temp = kSize / MB;
            return df.format(temp) + "MB";
        } else if (kSize / KB >= 1) {
            temp = kSize / KB;
            return df.format(temp) + "KB";
        }
        return kSize + "B";
    }

    public static String getDir() {
        File file = Glide.getPhotoCacheDir(EventApp.mContext);
        return file.getAbsolutePath();
    }


    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 清楚缓存
     */
    public static void clearDiskCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(EventApp.mContext).clearDiskCache();
            }
        }).start();
    }

    public static void load(Context mContext, View view) {
    }
}
