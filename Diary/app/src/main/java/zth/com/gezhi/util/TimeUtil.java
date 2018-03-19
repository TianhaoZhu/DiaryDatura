package zth.com.gezhi.util;

import android.text.Html;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * Created by Kotle on 2017/9/28.
 */

public class TimeUtil {
    private TimeUtil() {
    }

    public static String formatTime(long time, String format) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
        return sDateFormat.format(time);
    }

    public static String onlyYear(long time) {
        return formatTime(time, "yyyy-MM-dd");
    }

    public static String noYear(long time) {
        return formatTime(time, "MM-dd hh:mm");
    }

    public static CharSequence inputOrderItemFormat(int planQty, int qty) {

        StringBuilder sb = new StringBuilder();
        sb.append("已收货<font color=\"#bfa052\">");
        sb.append(qty);
        sb.append("</font>件，剩余<font color=\"#f97979\">");
        sb.append(planQty - qty);
        sb.append("</font>件");
        return Html.fromHtml(sb.toString());
    }

    //对返回的金额保留两位小数，并添加￥
    public static String moneyFormat(Object originalData) {
        String o = null;
        if (originalData == null) {
            return "￥0.00";
        }
        if (originalData instanceof String) {
            o = (String) originalData;
        } else {
            o = String.valueOf(originalData);
        }
        if (TextUtils.isEmpty(o)) {
            return "￥0.00";
        }
        BigDecimal b = new BigDecimal(o);
        String result = b.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        return "￥" + result;
    }
}
