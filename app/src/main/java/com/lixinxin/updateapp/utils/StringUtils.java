package com.lixinxin.updateapp.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Base64;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 是否为空字符串
     */
    public static boolean isNull(String str) {
        if (null == str || "".equals(str.trim()) || "null".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(String str) {
        if (null == str || "".equals(str.trim()) || "null".equals(str.trim())) {
            return false;
        }
        return true;
    }

    /**
     * @param str
     * @return
     */
    public static String commaEndTrim(String str) {
        if (isNull(str))
            return "";
        if (str.trim().lastIndexOf(",") != -1) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
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

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (isChinese(c[i])) {
                if (c[i] == 12288) {
                    c[i] = (char) 32;
                    continue;
                }
                if (c[i] > 65280 && c[i] < 65375)
                    c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static int floatToInt(float source) {
        return new BigDecimal(source).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    /**
     * @param format "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getDateFormat(String format) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    public static String getTokenMD5(String resouce) {
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(resouce.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    // public static String decimalFormat(double price) {
    // DecimalFormat df = new DecimalFormat("######0");
    // return df.format(price);
    // }

    // public static String priceFormat(String price) {
    // if (isNotNull(price) && price.indexOf(".") != -1) {
    // return price.substring(0, price.indexOf("."));
    // }
    // return price;
    // }

    public static String priceNewFormat(String price) {
        if (StringUtils.isNull(price)) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#########.##");
        double priceDouble = 0;
        try {
            priceDouble = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return df.format(priceDouble);
    }

    public static String priceNewFormat(double price) {

        DecimalFormat df = new DecimalFormat("#########.##");

        return df.format(price);
    }


    /**
     * 保留两位小数
     *
     * @param f
     * @return
     */
    public static String formatFloat(float f) {
        float temp = f;
        //构造方法的字符格式这里如果小数不足2位,会以0补足
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        //format 返回的是字符串
        String p = decimalFormat.format(temp);
        return p;
    }

    /**
     * 隐藏手机号中间4位
     *
     * @param phone
     * @return
     */
    public static String hidePhone(String phone) {
        if (StringUtils.isNotNull(phone)) {
            return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return "";
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String deviceType() {
        return android.os.Build.BRAND + " " + android.os.Build.MODEL.trim().replaceAll("\\s*", "");
    }

    public static String deviceName() {
        return android.os.Build.MANUFACTURER;
    }


    /**
     * 返回 Base64 解码 数据
     *
     * @param encodedString
     * @return
     */
    public static String decodedBase64(String encodedString) {
        String decodedString = new String(Base64.decode(encodedString, Base64.DEFAULT));
        return decodedString;
    }

    /**
     * 返回 Base64 编码数据
     *
     * @param string
     * @return
     */
    public static String encodeBase64(String string) {
        String encodeString = Base64.encodeToString(string.getBytes(), 0, string.getBytes().length, Base64.DEFAULT);
        return encodeString;
    }

    public static String string2Base64(String str) {
        return Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
    }

    public static int getResource(Context mContext, String imageName) {
        int resId = mContext.getResources().getIdentifier(imageName, "mipmap", mContext.getPackageName());
        return resId;
    }


    /**
     * 复制链接
     *
     * @param link
     */
    public static void copyLink(String link, Context mContext) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setPrimaryClip(ClipData.newPlainText(null, link));
        ToastUtils.showToast(mContext, "已复制到剪切板");
    }

    /**
     * 复制链接
     *
     * @param link
     */
    public static void copyUrl(String link, Context mContext) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setPrimaryClip(ClipData.newPlainText(null, link));

    }
}
