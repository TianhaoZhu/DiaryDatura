package zth.com.gezhi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import org.xutils.x;

import zth.com.gezhi.beanjson.LoginJsonBean;
import zth.com.gezhi.module.login.LoginMainActivity;
import zth.com.gezhi.okhttp.HttpRequestHelper;
import zth.com.gezhi.prop.ConfigProperties;
import zth.com.gezhi.util.LogUtils;
import zth.com.gezhi.util.UserInfoUtil;

import static org.xutils.x.isDebug;

/**
 * appClass
 * Created by Lenovo on 2017/2/9.
 */

public class AppClass extends Application {

    public int TILE_HEIGHT = 200;

    public float screenDensity = 1;

    public int screenHeight = 1920;

    public int screenWidth = 1080;

    private static AppClass instance;

    private HttpRequestHelper mHttpRequestHelper;

    private String mDeviceId = "";


    private String baseURL;

    private boolean shareData = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(AppClass.getInstance()); // 尽可能早，推荐在Application中初始化

        mHttpRequestHelper = new HttpRequestHelper(null);

        mHttpRequestHelper.setBaseUrl(getBaseURL());

        initDeviceId();

        initXutils();
//        registerActivity();


    }

    private void initXutils() {
        x.Ext.init(this); // 这一步之后, 我们就可以在任何地方使用x.app()来获取Application的实例了
    }

    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getWindowParams() {
        return windowParams;
    }


    /**
     * 手机串号
     */
    @SuppressLint("MissingPermission")
    private void initDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //mDeviceId = telephonyManager.getDeviceId();
    }

    /**
     * 获取手机串号
     *
     * @return
     */
    public String getmDeviceId() {
        return mDeviceId;
    }

    public static AppClass getInstance() {
        return instance;
    }


    public HttpRequestHelper getHttpRequestHelper() {
        return mHttpRequestHelper;
    }


    private LoginJsonBean.LoginData user;

    public void setUser(LoginJsonBean.LoginData user) {
        this.user = user;
        UserInfoUtil.saveUserInfo(this, user);
    }

    public LoginJsonBean.LoginData getUser() {
        if (user == null) {
            user = UserInfoUtil.getUserInfo(this);
        }
        return user;
    }


    public boolean isShareData() {
        return shareData;
    }

    public void setShareData(boolean shareData) {
        this.shareData = shareData;
    }

    public String getBaseURL() {
        baseURL = UserInfoUtil.getUrl(this);
        if (TextUtils.isEmpty(baseURL)) {
            baseURL = ConfigProperties.baseUrl;
        }
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
        UserInfoUtil.saveUrl(this, baseURL);
        getHttpRequestHelper().setBaseUrl(baseURL);
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        LogUtils.info("onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        LogUtils.info("onTerminate");
        super.onTerminate();
    }


    private static int activityCount = 0;

    public void registerActivity() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                if (activityCount == 0) {
                    LogUtils.info("-====结束了执行了");
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }


}
