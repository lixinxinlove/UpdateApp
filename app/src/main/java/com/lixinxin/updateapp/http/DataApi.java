package com.lixinxin.updateapp.http;

import com.lixinxin.updateapp.Config;
import com.lixinxin.updateapp.utils.StringUtils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class DataApi {

    public DataApiForOkHttp okHttp;

    public DataApi() {
        okHttp = new DataApiForOkHttp();
    }


    public void postFile(EventRequestCallback callback, String url, File file) {
        okHttp.postFile(url, file, callback);
    }

    /**
     * @param eventCallback
     */
    public void bindPhoneNumber(EventRequestCallback eventCallback
            , String token
            , String openid
            , String phone
            , String sms_code
            , String code
            , int type) {


        Map<String, String> map = new HashMap<>();

        TreeMap<String, String> tree = createGetRequestParams2();
        String url = Config.BASE_URL2 + "account/app/b/newWechatBind?";

        createBuilderBodyParams(map, tree);

        map.put("token", token);
        map.put("openid", openid);
        map.put("phone", phone);
        map.put("sms_code", sms_code);
        map.put("code", code);
        map.put("moudle_name", "b-login");
        map.put("type", type + "");

        // Log.e("ticketDetail", url);
        okHttp.post(map, url, eventCallback);


    }


    //===================================基础配置=======================================================

    /**
     * post 请求添加基础参数
     *
     * @param map
     * @param tree
     */
    private void createBuilderBodyParams( Map<String, String> map, TreeMap<String, String> tree) {
        Iterator<String> it = tree.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = tree.get(key);
            map.put(key, value);
        }
        String sign = getNewRequestTokenMD5(tree);
        map.put("sign", sign);
    }

    /**
     * 获取get url
     *
     * @param tree
     * @return
     * @throws UnsupportedEncodingException
     */
    public String createGetURIByInterface(TreeMap<String, String> tree, String _interface) {
        StringBuffer urlBuffer = new StringBuffer(Config.BASE_URL);
        urlBuffer.append(_interface);
        Iterator<String> it = tree.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = tree.get(key);
            if (Config.REQUEST_TIMESTAMP_LAB.equals(key)) {
                try {
                    value = URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            urlBuffer.append(key + "=" + value);
            if (it.hasNext()) {
                urlBuffer.append("&");
            }
        }
        urlBuffer.append("&" + Config.REQUEST_SIGN_LAB + "=" + getRequestTokenMD5(tree));
        return urlBuffer.toString();
    }

    /**
     * 获取get url   2
     *
     * @param tree
     * @return
     * @throws UnsupportedEncodingException
     */
    public String createGetURIByInterface2(TreeMap<String, String> tree, String _interface) {

        StringBuffer urlBuffer = new StringBuffer(Config.BASE_URL2);
        urlBuffer.append(_interface);
        Iterator<String> it = tree.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = tree.get(key);
            if (Config.REQUEST_TIMESTAMP_LAB.equals(key)) {
                try {
                    value = URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if ("last_query_date".equals(key)) {
                try {
                    value = URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            urlBuffer.append(key + "=" + value);
            if (it.hasNext()) {
                urlBuffer.append("&");
            }
        }
        urlBuffer.append("&" + Config.REQUEST_SIGN_LAB + "=" + getNewRequestTokenMD5(tree));
        return urlBuffer.toString();
    }

    /**
     * 获取加密的sign
     *
     * @param tree
     * @return
     */
    private String getRequestTokenMD5(TreeMap<String, String> tree) {
        StringBuffer secretBuffer = new StringBuffer();
        Iterator<String> it = tree.keySet().iterator();
        secretBuffer.append(Config.MD5_SECRET);
        while (it.hasNext()) {
            String key = it.next();
            String value = tree.get(key);
            secretBuffer.append(key + value);
        }
        secretBuffer.append(Config.MD5_SECRET);
        String sign = StringUtils.getTokenMD5(secretBuffer.toString());
        return sign;
    }


    /**
     * 获取加密的sign
     *
     * @param tree
     * @return
     */
    private String getNewRequestTokenMD5(TreeMap<String, String> tree) {
        StringBuffer secretBuffer = new StringBuffer();
        Iterator<String> it = tree.keySet().iterator();
        secretBuffer.append(Config.MD5_SECRET2);
        while (it.hasNext()) {
            String key = it.next();
            String value = tree.get(key);
            secretBuffer.append(key + value);
        }
        secretBuffer.append(Config.MD5_SECRET2);
        String sign = StringUtils.getTokenMD5(secretBuffer.toString());
        return sign;
    }


    /**
     * 构建基础参数treeMap
     *
     * @return
     */
    public static TreeMap<String, String> createBaseParams() {
        TreeMap<String, String> tree = new TreeMap<>();
        tree.put(Config.REQUEST_FORMAT_LAB, Config.REQUEST_FORMAT_VALUE);
        tree.put(Config.REQUEST_SIGN_METHOD_LAB, Config.REQUEST_SIGN_METHOD_VALUE);
        tree.put(Config.REQUEST_APPID_LAB, Config.REQUEST_APPID_VALUE);
        tree.put(Config.REQUEST_SERVICE_VERSION, Config.REQUEST_SERVICE_VERSION_VALUE);
        tree.put(Config.REQUEST_TIMESTAMP_LAB, StringUtils.getDateFormat("yyyy-MM-dd HH:mm:ss"));
//        if (EventApp.user != null && StringUtils.isNotNull(EventApp.user.getAccess_token_2())) {
//            tree.put(Config.ACCESS_TOKEN, EventApp.user.getAccess_token_2());
//        }
        return tree;
    }

    /**
     * 构建基础参数treeMap 2
     *
     * @return
     */
    public static TreeMap<String, String> createGetRequestParams2() {
        TreeMap<String, String> tree = new TreeMap<>();
        tree.put(Config.REQUEST_APPID_LAB, Config.REQUEST_APPID_VALUE_2);
        tree.put(Config.REQUEST_TIMESTAMP_LAB, StringUtils.getDateFormat("yyyy-MM-dd HH:mm:ss"));
        tree.put(Config.REQUEST_SIGN_METHOD_LAB, Config.REQUEST_SIGN_METHOD_VALUE);
        tree.put(Config.REQUEST_SERVICE_VERSION, Config.REQUEST_SERVICE_VERSION_VALUE);
        return tree;
    }

}
