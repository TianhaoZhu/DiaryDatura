package zth.com.gezhi.util;

import android.util.Log;

import zth.com.gezhi.prop.ConfigProperties;


/**
 * log工具
 * Created by Lenovo on 2017/2/6.
 */

public class LogUtils {

    public static void life(String tag, String tagLifeInfo) {
        if (!ConfigProperties.showDebug) {
            return;
        }
        if (tagLifeInfo.length() > 4000){
            logLongString(tag,tagLifeInfo);
        }else {
            Log.i(tag,tagLifeInfo);
        }
    }

    public static void info(String info) {
        if (!ConfigProperties.showDebug) {
            return;
        }
        if (info.length() > 4000){
            logLongString("log_time",info);
        }else {
            Log.i("log_info",info);
        }
    }

    public static void time(String info) {
        if (!ConfigProperties.showDebug) {
            return;
        }
        if (info.length() > 4000){
            logLongString("log_time",info);
        }else {
            Log.i("log_time", info);
        }
    }

    public static void logFile(String info) {
        if (!ConfigProperties.showDebug) {
            return;
        }

        if (info.length() > 4000){
            logLongString("log_file",info);
        }else {
            Log.i("log_file", info);
        }
    }

    public static void logNetwodk(String info) {
        if (!ConfigProperties.showDebug) {
            return;
        }

        if (info.length() > 4000){
            logLongString("log_netWork",info);
        }else {
            Log.i("log_netWork",info);
        }
    }

    public static void logLongString(String tag, String info) {
        info = info.trim();
        int index = 0;
        int maxLength = 4000;
        String sub;

        if (!ConfigProperties.isShowLogDebug){
            Log.i(tag, info.substring(index, index + maxLength));
            return;
        }

        Log.i(tag, "log_length="+info.length());
        while (index < info.length()) {
            // java的字符不允许指定超过总的长度end
            if (info.length() <= index + maxLength) {
                sub = info.substring(index);
            } else {
                sub = info.substring(index, index + maxLength);
            }

            index += maxLength;
            Log.i(tag, sub.trim());
        }
    }
}
