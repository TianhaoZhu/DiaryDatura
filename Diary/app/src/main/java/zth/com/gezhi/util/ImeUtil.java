package zth.com.gezhi.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2017/4/11.
 */

public class ImeUtil {
    //显示键盘
    public static void showIme(Activity activity, View focusView) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!imm.isActive()) {
            imm.showSoftInput(focusView, InputMethodManager.RESULT_SHOWN);
        }
    }

    //关闭键盘
    public static void hideIme(Activity activity) {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(activity.getWindow().peekDecorView().getWindowToken(), InputMethodManager.RESULT_HIDDEN);
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().peekDecorView().getWindowToken(), 0);
    }

}
