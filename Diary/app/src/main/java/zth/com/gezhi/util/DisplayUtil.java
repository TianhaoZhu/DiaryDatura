package zth.com.gezhi.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Kotle 2017-09-28
 */
public class DisplayUtil {
    private DisplayUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 根据dip值转化成px值
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dipToPix(Context context, int dip) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return size;
    }


    /**
     * sp转px
     *
     * @param context
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

}
