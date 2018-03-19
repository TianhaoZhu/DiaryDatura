package zth.com.gezhi.util;

import android.content.Context;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import zth.com.gezhi.beanjson.LoginJsonBean;
import zth.com.gezhi.prop.StaticInfo;

/**
 * 存取UserBean
 * Created by cym1497 on 2017/6/26.
 */

public class UserInfoUtil {
    public static void saveUserInfo(Context context, LoginJsonBean.LoginData userBean) {
        String userInfo = object2String(userBean);
        context.getSharedPreferences(StaticInfo.userShareName, Context.MODE_PRIVATE)
                .edit()
                .putString("userBean", userInfo)
                .apply();
    }

    public static LoginJsonBean.LoginData getUserInfo(Context context) {
        String userBeanStr = context.getSharedPreferences(StaticInfo.userShareName, Context.MODE_PRIVATE)
                .getString("userBean", "");
        LoginJsonBean.LoginData userBean = (LoginJsonBean.LoginData)string2Object(userBeanStr);
        LogUtils.info(userBean.toString());
        return userBean;
    }


    /**
     * code object to objectBase64String
     * @param object
     * @return
     */
    public static String object2String(Object object) {
        String objectBase64 = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            // 将字节流编码成base64的字符窜
            objectBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectBase64;
    }

    /**
     *  decode objectBase64String to Object
     * @param objectBase64
     * @return
     */
    public static Object string2Object(String objectBase64) {
        Object obj = null;
        byte[] base64 = Base64.decode(objectBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            ObjectInputStream bis = new ObjectInputStream(bais);
            try {
                //读取对象
                obj = bis.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }



    public static void saveUrl(Context context, String url) {
        context.getSharedPreferences(StaticInfo.urlName, Context.MODE_PRIVATE)
                .edit()
                .putString("url", url)
                .apply();
    }

    public static String getUrl(Context context) {
        String result = context.getSharedPreferences(StaticInfo.urlName, Context.MODE_PRIVATE)
                .getString("url", "");
        LogUtils.info(result);
        return result;
    }

}
