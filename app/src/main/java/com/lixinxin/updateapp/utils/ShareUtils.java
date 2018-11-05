package com.lixinxin.updateapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;

import com.eventmosh.evente.Config;
import com.eventmosh.evente.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.File;

/**
 * Created by android on 2016/11/8.
 * 分享
 */
public class ShareUtils {

    private static final int THUMB_SIZE = 100;

    private static IWXAPI api;

    private static IWeiboShareAPI mWeiboShareAPI;


    /**
     * 微博分享文本
     *
     * @param context
     * @param shareText
     */
    public static void shareToWeiboTextObj(Activity context, String shareText) {
        if (mWeiboShareAPI == null) {
            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Config.WEIBO_APP_ID);
            // 注册到新浪微博
            mWeiboShareAPI.registerApp();
        }

        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        textObject.text = shareText;
        weiboMessage.textObject = textObject;
        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        AuthInfo authInfo = new AuthInfo(context, Config.WEIBO_APP_ID, Config.WEIBO_REDIRECT_URL,
                Config.WEIBO_SCOPE);
        String token = "";

        mWeiboShareAPI.sendRequest(context, request, authInfo, token, new WeiboAuthListener() {

            @Override
            public void onWeiboException(WeiboException arg0) {
            }

            @Override
            public void onComplete(Bundle bundle) {
                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
            }

            @Override
            public void onCancel() {

            }
        });
    }


    /**
     * 分享活动链接到微博
     *
     * @param context
     * @param title
     * @param description
     * @param imgUrl
     * @param url
     */
    public static void shareToWeibo(final Activity context, String title, String description, String imgUrl,
                                    String url) {
        downloadImgToWeibo(context, title, description, imgUrl, url);

    }


    /**
     * 分享 链接 到微博
     */
    public static void shareToWeiboWebUrl(final Activity context, String title, String description, Bitmap btp,
                                          String url) {
        if (mWeiboShareAPI == null) {
            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Config.WEIBO_APP_ID);
            // 注册到新浪微博
            mWeiboShareAPI.registerApp();
        }

        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = description;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_def_share);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        // 设置 Bitmap 类型的图片到视频对象里
        // 设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(thumbBmp);
        mediaObject.actionUrl = url;
        mediaObject.defaultText = description;

        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.mediaObject = mediaObject;
//        TextObject textObject = new TextObject();
//        textObject.text ="更多huo产品尽在【长隆旅游】APP，下载请戳→" +url;
//        weiboMessage.textObject=


        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        AuthInfo authInfo = new AuthInfo(context, Config.WEIBO_APP_ID, Config.WEIBO_REDIRECT_URL, Config.WEIBO_SCOPE);
        String token = "";
        mWeiboShareAPI.sendRequest(context, request, authInfo, token, new WeiboAuthListener() {

            @Override
            public void onWeiboException(WeiboException arg0) {

            }

            @Override
            public void onComplete(Bundle bundle) {
                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                ToastUtils.showToast(context, "分享成功");
            }

            @Override
            public void onCancel() {
                ToastUtils.showToast(context, "分享取消");
            }
        });
    }

    /**
     * 微博分享文本
     *
     * @param context
     * @param
     * @param shareText
     */
    public static void shareToWeiboTextObj(Activity context, String shareText, String shareUrl) {
        executShareWeibo(context, shareText, shareUrl);
    }

    private static void executShareWeibo(Activity context, String shareText, String shareUrl) {
        if (mWeiboShareAPI == null) {
            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Config.WEIBO_APP_ID);
            // 注册到新浪微博
            mWeiboShareAPI.registerApp();
        }
        WeiboMessage weiboMessage = new WeiboMessage();
        TextObject textObject = new TextObject();
        String textString = shareText;
        if (StringUtils.isNotNull(shareUrl)) {
            textString = textString + shareUrl;
        }

        textObject.text = textString;
        weiboMessage.mediaObject = textObject;
        // }
        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(context, request);
    }


    /**
     * 分享 图片 到微博
     */
    public static void shareImgToWeibo(final Activity context, final Bitmap bmp) {

        if (mWeiboShareAPI == null) {
            mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Config.WEIBO_APP_ID);
            // 注册到新浪微博
            mWeiboShareAPI.registerApp();
        }

        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = bmp;
        imageObject.setImageObject(bitmap);


        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.mediaObject = imageObject;
        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        AuthInfo authInfo = new AuthInfo(context, Config.WEIBO_APP_ID, Config.WEIBO_REDIRECT_URL, Config.WEIBO_SCOPE);
        String token = "";
        mWeiboShareAPI.sendRequest(context, request, authInfo, token, new WeiboAuthListener() {

            @Override
            public void onWeiboException(WeiboException arg0) {
            }

            @Override
            public void onComplete(Bundle bundle) {
                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
            }

            @Override
            public void onCancel() {
                ToastUtils.showToast(context, "分享取消");
            }
        });
    }


    /**
     * 分享图片到微信
     *
     * @param context
     * @param bmp
     * @param isSession
     */
    public static void shareImageToWechat(Context context, Bitmap bmp, boolean isSession) {

        api = WXAPIFactory.createWXAPI(context, Config.WX_APP_ID, false);
        // Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        WXImageObject imageObject = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 100, 120, true);
        bmp.recycle();
        msg.thumbData = FileUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");

        req.message = msg;
        req.scene = isSession ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);

    }


    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    /**
     * 分享链接 到微信
     */
    public static void shareToWechat(Activity context, boolean isTimelineCb, String shareImg, String shareTitle,
                                     String shareContent, String shareUrl) {
        downloadImg(context, isTimelineCb, shareImg, shareTitle, shareContent, shareUrl);
    }

    public static void executShareWechat(Activity context, boolean isTimelineCb, Bitmap bitmap, String shareTitle,
                                         String shareContent, String shareUrl) {
        if (api == null)
            api = WXAPIFactory.createWXAPI(context, Config.WX_APP_ID, false);
        try {
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = shareUrl;
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = shareTitle;
            msg.description = shareContent;

            Bitmap thumbBmp;
            if (bitmap == null) {
                thumbBmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_def_share);
            } else {
                thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
            }

            msg.thumbData = FileUtil.bmpToByteArray(thumbBmp, true);
            bitmap.recycle();
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
            api.sendReq(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void downloadImg(final Activity context, final boolean isTimelineCb, String shareImg,
                                    final String shareTitle, final String shareContent, final String shareUrl) {
        if (StringUtils.isNull(shareImg))
            shareImg = "";
        HttpUtils http = new HttpUtils(2000);
        String filename = Environment.getExternalStorageDirectory() + "/ChimelongApp/ImageCache/share_"
                + System.currentTimeMillis() + ".jpg";

        http.download(shareImg, filename, true, true, new RequestCallBack<File>() {

            @Override
            public void onSuccess(ResponseInfo<File> arg0) {
                File file = arg0.result;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                executShareWechat(context, isTimelineCb, bitmap, shareTitle, shareContent, shareUrl);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_def_share);
                executShareWechat(context, isTimelineCb, bitmap, shareTitle, shareContent, shareUrl);
            }
        });
    }


    private static void downloadImgToWeibo(final Activity context, final String title, final String description, String imgUrl,
                                           final String url) {
        // if (StringUtils.isNull(imgUrl))
        //     imgUrl = "";
        HttpUtils http = new HttpUtils(2000);
        String filename = Environment.getExternalStorageDirectory() + "/ChimelongApp/ImageCache/share_"
                + System.currentTimeMillis() + ".jpg";
        http.download(imgUrl, filename, true, true, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                File file = responseInfo.result;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                shareToWeiboWebUrl(context, title, description, bitmap, url);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_def_share);
                shareToWeiboWebUrl(context, title, description, bitmap, url);
            }
        });

    }




}


