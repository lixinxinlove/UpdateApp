package com.lixinxin.updateapp.utils;

/**
 * Created by android on 2018/2/6.
 */

public class IntegerUtils {
    public static int getInteger(Object object) {
        if (object instanceof Boolean) {
            return 0;
        } else if (object instanceof Double) {
            return (int) (double) object;
        } else if (object instanceof Integer) {
            return (int) object;
        } else if (object instanceof String) {
            return Integer.getInteger(object.toString(), 0);
        } else {
            return 0;
        }
    }
}
