package zth.com.gezhi.dialog;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by Kotle on 2017/10/19.
 */

public class KtAlertView extends DialogFragment {
    private final int DEFAULT_INT_VALUE = -2017;
    private View mDialogRootView;
    private int mLayoutRes = DEFAULT_INT_VALUE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if (mLayoutRes != DEFAULT_INT_VALUE) {
            mDialogRootView = inflater.inflate(mLayoutRes, container, false);
        }
        if (mDialogRootView == null) {
            throw new NullPointerException("content view is null");
        }
        return mDialogRootView;
    }

    public void setContentView(@LayoutRes int layoutRes) {
        this.mLayoutRes = layoutRes;
    }

    public void setContentView(View rootView) {
        mDialogRootView = rootView;
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }
}
