package zth.com.gezhi.okhttp.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.util.Map;

import zth.com.gezhi.AppClass;
import zth.com.gezhi.R;
import zth.com.gezhi.base.BaseActivity;
import zth.com.gezhi.bean.BaseBean;
import zth.com.gezhi.okhttp.NetWorkEvent;
import zth.com.gezhi.util.GsonUtil;

import static zth.com.gezhi.prop.ConfigProperties.ERROR;
import static zth.com.gezhi.prop.ConfigProperties.FAIL;
import static zth.com.gezhi.prop.ConfigProperties.NETWORK_ERROR;

/**
 * Created by Kotle on 2017/9/26.
 */

public class NetUtil {
    private NetUtil() {
    }

    //显示加载框的网络请求
    public static void doPostNoHead(String url, Map<String, Object> params, Object tag) {
        doPostNoHead(url, params, tag, true);
    }

    //没有请求头的post提交
    //别问为什么过时，我高兴而已
    public static void doPostNoHead(String url, Map<String, Object> params, Object tag, boolean isShowNetAlert) {
        if (tag instanceof BaseActivity && isShowNetAlert) {
            ((BaseActivity) tag).showProgressNetDialog();
        }
        if (params == null || params.size() == 0) {
            //传入参数为空的时候，会导致系统崩溃，所以强行加一个无用的参数
            doPostJson(url, "{\"what`sYouName?\":\"YuFangFang\"}", tag);
            return;
        }
        String s = GsonUtil.objectToJsonStr(params);
        if (TextUtils.isEmpty(s)) {
            return;
        }
        doPostJson(url, s, tag);
    }

    //没有请求头的post提交
    public static void doPostJson(String url, String params, Object tag) {
        AppClass.getInstance()
                .getHttpRequestHelper()
                .doPostJson(url, null, params, tag);
    }
    /**
     * 判断网络请求是否成功,不成功返回null
     * @return
     */
    public static <T extends BaseBean> T isNetWorkSuccess(NetWorkEvent event, Class<T> cls) {
        return isNetWorkSuccess(event, cls, true);
    }

    /**
     * 判断网络请求是否成功,不成功返回null
     */
    public static <T extends BaseBean> T isNetWorkSuccess(NetWorkEvent event, Class<T> cls, boolean isDismissDialog) {
        //event为null 请求失败
        if (event == null) {
            return null;
        }
        //隐藏网络请求弹窗
        if (event.tag != null && event.tag instanceof BaseActivity && isDismissDialog) {
            ((BaseActivity) event.tag).dismissProgressNetDialog();
        }
        //没有请求到结果
        if (event.result == null) {
            if (event.tag != null && event.tag instanceof BaseActivity) {
                ((BaseActivity) event.tag).showImageToast(R.mipmap.ic_warning, NETWORK_ERROR, false);
            }
            return null;
        }
        //字符串转javaBean
        T t = GsonUtil.jsonToBean(event.result.toString(), cls);
        //转换失败
        if (t == null) {
            return null;
        }
        //返回的数据状态为失败或者错误
        if (FAIL.equals(t.getStatus()) || ERROR.equals(t.getStatus())) {
            if (event.tag != null && event.tag instanceof BaseActivity) {
                ((BaseActivity) event.tag).showImageToast(R.mipmap.ic_warning, t.getMsg(), false);
            }
            return null;
        }
        //返回的数据状态不为成功（针对返回的数据格式为其他做判断）
//        if (!SUCCESS.equals(t.getStatus())&&t!=null&&t.) {
//            if (event.tag != null && event.tag instanceof BaseActivity) {
//                ((BaseActivity) event.tag).showImageToast(R.mipmap.ic_warning, "操作失败", false);
//            }
//            return null;
//        }
        return t;
    }

    public static String getMac(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            // 获取MAC地址
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String mac = wifiInfo.getMacAddress();
            if (null == mac) {
                // 未获取到
                mac = "";
            }
            return mac;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

