package com.lixinxin.updateapp.http;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.lixinxin.updateapp.Config;
import com.lixinxin.updateapp.utils.GsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataApiForOkHttp {

    public OkHttpClient http;

    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public DataApiForOkHttp() {

        http = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
    }


    private FormBody getFormBodyByMap(Map<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = map.get(key);
            builder.add(key, value);
        }
        return builder.build();

    }


    public void get(String url, EventRequestCallback callback) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        requestBuilder.method(GET_METHOD, null);
        Request request = requestBuilder.build();
        Call mcall = http.newCall(request);
        callback.setmCall(mcall);
        mcall.enqueue(new EventRequestCallbackImpl(callback));
    }

    public void post(Map<String, String> map, String url, EventRequestCallback callback) {
        FormBody body = getFormBodyByMap(map);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        requestBuilder.post(body).build();
        Request request = requestBuilder.post(body).build();
        Call mCall = http.newCall(request);
        mCall.enqueue(new EventRequestCallbackImpl(callback));
    }


    public void getData(String url, EventRequestCallback callback) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        requestBuilder.method(GET_METHOD, null);
        Request request = requestBuilder.build();
        Call mCall = http.newCall(request);
        callback.setmCall(mCall);
        mCall.enqueue(new MyEventRequestCallbackImpl(callback));
    }


    /**
     * 返回结果在io线程
     *
     * @param body
     * @param url
     * @param callback
     */
    public void postAsync(FormBody body, String url, EventRequestCallback callback) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        requestBuilder.removeHeader("User-Agent").addHeader("User-Agent", getUserAgent());
        requestBuilder.post(body).build();
        Request request = requestBuilder.post(body).build();
        Call mcall = http.newCall(request);
        mcall.enqueue(new EventRequestCallbackAsyncImpl(callback));
    }


    public void get2(String url, EventRequestCallback callback, String header) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        requestBuilder.method(GET_METHOD, null);
        requestBuilder.addHeader("Authorization", header);
        requestBuilder.removeHeader("User-Agent");
        requestBuilder.addHeader("User-Agent", getUserAgent());
        Request request = requestBuilder.build();
        Call mcall = http.newCall(request);
        callback.setmCall(mcall);
        mcall.enqueue(new EventRequestCallbackImpl2(callback));

        // Log.e("User-Agent", getUserAgent());
    }


    /**
     * 在子线程返回
     *
     * @param url
     * @param callback
     * @param header
     */
    public void getAsync2(String url, EventRequestCallback callback, String header) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        requestBuilder.method(GET_METHOD, null);
        requestBuilder.addHeader("Authorization", header);
        Request request = requestBuilder.build();
        Call mcall = http.newCall(request);
        callback.setmCall(mcall);
        mcall.enqueue(new EventRequestCallbackAsyncImpl2(callback));
    }

    /**
     * 上传文件
     *
     * @param url
     * @param callback
     */
    public void postFile(String url, File file, EventRequestCallback callback) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody mBody = new MultipartBody.Builder("----")
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "filename1", body)
                .build();

        Request request = requestBuilder
                .post(mBody)
                .build();
        Call mCall = http.newCall(request);
        mCall.enqueue(new EventRequestCallbackImpl(callback));
    }


    /**
     * 在UI线程返回
     */
    public class EventRequestCallbackImpl implements Callback {

        private EventRequestCallback mCallback;

        private EventResponseEntity resEntity = new EventResponseEntity();

        public EventRequestCallbackImpl(EventRequestCallback callback) {
            this.mCallback = callback;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback._onStart();
                }
            });
        }

        @Override
        public void onFailure(Call arg0, IOException arg1) {
            resEntity.code = Config.HTTP_REQUEST_FAILURE;
            resEntity.message = "网络异常";
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback._RequestCallback(resEntity);
                }
            });
        }

        @Override
        public void onResponse(Call arg0, Response response) throws IOException {
            if (response.isSuccessful()) {
                String res = response.body().string();
                try {
                    resEntity = GsonUtils.asJSONToResponseEntity(res);
                    // 重新登录
                    if (resEntity.code.equals(Config.HTTP_REQUEST_TOKEN_OVERDUE)) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mCallback.reLogin();
                            }
                        });
                    } else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mCallback._RequestCallback(resEntity);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    resEntity.code = Config.HTTP_REQUEST_JSON_ERROR;
                    resEntity.message = "数据格式错误";
                    resEntity.data = res;
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            mCallback._RequestCallback(resEntity);
                        }
                    });
                }
            } else {

                resEntity.code = Config.HTTP_REQUEST_JSON_ERROR;
                resEntity.message = "请求失败";
                resEntity.data = "";
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback._RequestCallback(resEntity);
                    }
                });
            }
        }
    }


    /**
     * 在UI线程返回
     */
    public class EventRequestCallbackImpl2 implements Callback {

        private EventRequestCallback mCallback;

        private EventResponseEntity resEntity = new EventResponseEntity();

        public EventRequestCallbackImpl2(EventRequestCallback callback) {
            this.mCallback = callback;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback._onStart();
                }
            });
        }

        @Override
        public void onFailure(Call arg0, IOException arg1) {

            resEntity.code = Config.HTTP_REQUEST_FAILURE;
            resEntity.message = "网络异常";
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback._RequestCallback(resEntity);
                }
            });
        }

        @Override
        public void onResponse(Call arg0, Response response) throws IOException {

            int httpCode = response.code();

            if (httpCode == 401) {  //重新登录
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.reLogin();
                    }
                });
                return;
            }


            if (response.isSuccessful()) {
                String res = response.body().string();
                try {
                    resEntity = GsonUtils.asJSONToResponseEntity2(res);
                    // 重新登录
                    if (resEntity.code.equals(Config.HTTP_REQUEST_TOKEN_OVERDUE)) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mCallback.reLogin();
                            }
                        });
                    } else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mCallback._RequestCallback(resEntity);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    resEntity.code = Config.HTTP_REQUEST_JSON_ERROR;
                    resEntity.message = "数据格式错误";
                    resEntity.data = res;
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            mCallback._RequestCallback(resEntity);
                        }
                    });
                }
            } else {
                resEntity.code = Config.HTTP_REQUEST_JSON_ERROR;
                resEntity.message = "请求失败";
                resEntity.data = "";
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        mCallback._RequestCallback(resEntity);
                    }
                });
            }
        }
    }


    /**
     * 返回数据不解析
     */
    public class MyEventRequestCallbackImpl implements Callback {

        private EventRequestCallback mCallback;

        private EventResponseEntity resEntity = new EventResponseEntity();

        public MyEventRequestCallbackImpl(EventRequestCallback callback) {
            this.mCallback = callback;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback._onStart();
                }
            });
        }


        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String res = response.body().string();
            resEntity.code = Config.HTTP_REQUEST_SUCCESS;
            resEntity.data = res;
            resEntity.message = "执行成功";

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback._RequestCallback(resEntity);
                }
            });
        }

        @Override
        public void onFailure(Call call, IOException e) {
            resEntity.code = Config.HTTP_REQUEST_FAILURE;
            resEntity.data = "";
            resEntity.message = "网络异常";
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback._RequestCallback(resEntity);
                }
            });
        }
    }


    /**
     * 异步返回数据
     */
    public class EventRequestCallbackAsyncImpl implements Callback {

        private EventRequestCallback mCallback;

        private EventResponseEntity resEntity = new EventResponseEntity();

        public EventRequestCallbackAsyncImpl(EventRequestCallback callback) {
            this.mCallback = callback;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback._onStart();
                }
            });
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                String res = response.body().string();
                try {
                    resEntity = GsonUtils.asJSONToResponseEntity(res);
                    mCallback._RequestCallback(resEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    resEntity.code = Config.HTTP_REQUEST_JSON_ERROR;
                    resEntity.message = "数据格式错误";
                    resEntity.data = res;
                    mCallback._RequestCallback(resEntity);
                }
            }
        }

        @Override
        public void onFailure(Call call, IOException e) {
            resEntity.code = Config.HTTP_REQUEST_FAILURE;
            resEntity.data = "";
            resEntity.message = "网络异常";
            mCallback._RequestCallback(resEntity);
        }
    }


    /**
     * 异步返回数据 2
     */
    public class EventRequestCallbackAsyncImpl2 implements Callback {

        private EventRequestCallback mCallback;

        private EventResponseEntity resEntity = new EventResponseEntity();

        public EventRequestCallbackAsyncImpl2(EventRequestCallback callback) {
            this.mCallback = callback;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback._onStart();
                }
            });
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                String res = response.body().string();
                try {
                    resEntity = GsonUtils.asJSONToResponseEntity2(res);
                    mCallback._RequestCallback(resEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    resEntity.code = Config.HTTP_REQUEST_JSON_ERROR;
                    resEntity.message = "数据格式错误";
                    resEntity.data = res;
                    mCallback._RequestCallback(resEntity);
                }
            }
        }

        @Override
        public void onFailure(Call call, IOException e) {
            resEntity.code = Config.HTTP_REQUEST_FAILURE;
            resEntity.data = "";
            resEntity.message = "网络异常";
            mCallback._RequestCallback(resEntity);
        }
    }


    private static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                //userAgent = WebSettings.getDefaultUserAgent(EventApp.mContext);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}
