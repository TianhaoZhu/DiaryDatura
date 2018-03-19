package zth.com.gezhi.util;

import android.content.Context;
import android.content.Intent;

import zth.com.gezhi.prop.Fun;

/**
 * Created by Kotle on 2017/9/25.
 */

public class ActivityUtil {
    public final static String KEY_INVENTORY_PLAN_ID = "KEY_INVENTORY_PLAN_ID";
    public final static String INTENT_KEY = "INTENT_KEY";

    private ActivityUtil() {
    }

    /**
     * activity跳转
     *
     * @param context 上下文
     * @param target  目标activity
     * @param fun     参数,可为null
     */
    public static void startActivity(Context context, Class<?> target, Fun<Intent> fun) {
        Intent intent = new Intent(context, target);
        if (fun != null) {
            fun.execute(intent);
        }
        context.startActivity(intent);
    }

    /**
     * activity跳转
     *
     * @param context 上下文
     * @param action  目标activity
     * @param fun     参数可为null
     */
    public static void startActivity(Context context, String action, Fun<Intent> fun) {
        Intent intent = new Intent(action);
        if (fun != null) {
            fun.execute(intent);
        }
        context.startActivity(intent);
    }
}
