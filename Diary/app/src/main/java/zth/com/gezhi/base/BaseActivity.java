package zth.com.gezhi.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import zth.com.gezhi.R;
import zth.com.gezhi.AppClass;
import zth.com.gezhi.dialog.ProgressNetDialog;
import zth.com.gezhi.dialog.ToastResultDialog;
import zth.com.gezhi.okhttp.NetWorkEvent;
import zth.com.gezhi.okhttp.util.ResponseHandlerUtil;
import zth.com.gezhi.prop.ConfigProperties;
import zth.com.gezhi.prop.StaticInfo;
import zth.com.gezhi.util.LogUtils;
import zth.com.gezhi.util.SystemSaveUtils;

/**
 * jiLei
 * Created by Lenovo on 2017/2/7.
 */

public abstract class BaseActivity extends Activity implements ConfigProperties, StaticInfo {

    /**
     * 导航栏高度
     */
    public static int TILE_HEIGHT = 200;
    /**
     * 像素密度
     */
    public static float screenDensity = 1;
    /**
     * 屏幕高度
     */
    public static int screenHeight = 1920;
    /**
     * 屏幕宽度
     */
    public static int screenWidth = 1080;


    public int volume, maxVolume;

    public int inputPower, allocationPower, backToWarehousePower, searchPower, bindPower, inventoryPower, bindTestPower;
    public SystemSaveUtils systemSaveUtils;
    private boolean showFloatView = false;


    private int powerType = -1;
    protected boolean isRegisterEvent = false;  //eventbus 配置

    private ProgressNetDialog netDialog;
    private ToastResultDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSysData();
        setNeedRegister();
        registerEventBus();
        setupScreen();
        setUpView();
    }


    protected void setNeedRegister() {
    }

    public abstract void setUpView();


    public void getSysData() {
        systemSaveUtils = new SystemSaveUtils(this);
        volume = systemSaveUtils.getVolume();
        maxVolume = systemSaveUtils.getMaxVolume();
        inputPower = systemSaveUtils.getInputPower();
        allocationPower = systemSaveUtils.getAllocationPower();
        backToWarehousePower = systemSaveUtils.getBackToWarehousePower();
        searchPower = systemSaveUtils.getSearchPower();
        bindPower = systemSaveUtils.getBindPower();
        inventoryPower = systemSaveUtils.getInventoryPower();
        bindTestPower = systemSaveUtils.getBindTestPower();
    }


    public int getPowerType() {
        return powerType;
    }

    public void setFloatPowerType(int type) {
        this.powerType = type;
    }


    public boolean isShowFloatView() {
        return showFloatView;
    }

    public void setShowFloatView(boolean showFloat) {
        this.showFloatView = showFloat;
    }


    public void setPowerValue(int value) {

    }

    public void setupScreen() {
        if (screenDensity != 1) {
            return;
        }
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        if (frame.top != 0) {
            TILE_HEIGHT = frame.top;// 获取导航栏的高度,这里必须在界面绘制出来才能正确获取
        } else {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                TILE_HEIGHT = getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenDensity = metrics.density;
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;

        AppClass.getInstance().screenDensity = screenDensity;
        AppClass.getInstance().screenHeight = screenHeight;
        AppClass.getInstance().screenWidth = screenWidth;
        AppClass.getInstance().TILE_HEIGHT = TILE_HEIGHT;

    }

    private void registerEventBus() {
        if (isRegisterEvent) {
            EventBus.getDefault().register(this);
        }
    }

    private void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetWorkEvent(NetWorkEvent event) {
        LogUtils.info(event.toString());
        LogUtils.info("tag匹配成功：" + this.equals(event.tag));
        if (!this.equals(event.tag)) {
            return;
        }
        ResponseHandlerUtil.invokeResponseHandle(this, event.mUrlName, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregisterEventBus();
        AppClass.getInstance().getHttpRequestHelper().cancel(this);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
        super.onDestroy();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public void showImageCenterToast(int drawableId, String msg) {
        Toast toast = Toast.makeText(getApplicationContext(),
                msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(getApplicationContext());
        imageCodeProject.setImageResource(drawableId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }


    public void showImageToast(int drawableId, String msg, final boolean activityFinish) {
        dialog = new ToastResultDialog(this, drawableId, msg);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (activityFinish) {
                    finish();
                }
            }
        }, 1200);
    }

    public void showImageToast(Context context, int drawableId, String msg, final boolean activityFinish) {
        final ToastResultDialog dialog = new ToastResultDialog(context, drawableId, msg);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (activityFinish) {
                    finish();
                }
            }
        }, 1200);
    }


    public String getVersionName() {
        String version = "";
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public String getLocalMacAddress() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }


    public void showProgressNetDialog() {
        if (netDialog == null) {
            netDialog = new ProgressNetDialog(this);
        } else {
            netDialog.show();
        }
    }

    public void dismissProgressNetDialog() {
        if (netDialog != null && netDialog.isShowing()) {
            netDialog.dismiss();
            netDialog = null;
        }

    }

    public void startImgAnim(ImageView imageView) {
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.img_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        imageView.startAnimation(operatingAnim);
    }

    public void stopImgAnim(ImageView imageView) {
        imageView.clearAnimation();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
