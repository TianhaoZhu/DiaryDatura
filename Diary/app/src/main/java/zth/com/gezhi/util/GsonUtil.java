package zth.com.gezhi.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import zth.com.gezhi.bean.BaseBean;

/**
 * Created by Kotle on 2017/9/25.
 * json格式字符串转javaBean
 * javaBean转字符串
 */

public class GsonUtil {
    private GsonUtil() {
    }

    /**
     * Json格式字符串转javaBean
     *
     * @param str 原字符串
     * @param <T> 类型
     * @return javabean
     */
    public static <T extends BaseBean> T jsonToBean(String str, Class<T> cls) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            Gson gson = new Gson();
            return gson.fromJson(str, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * javaBean转为json格式字符串
     *
     * @param bean javaBean
     * @return 字符串
     */
    public static String objectToJsonStr(Object bean) {
        try {
            if (bean == null) {
                return null;
            }
            Gson gson = new Gson();
            return gson.toJson(bean);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
