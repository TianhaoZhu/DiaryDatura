package zth.com.gezhi.okhttp.repsonsehandler;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import zth.com.gezhi.AppClass;
import zth.com.gezhi.okhttp.NetWorkEvent;
import zth.com.gezhi.util.LogUtils;

/**
 * Created by CYM on 2017/4/25.
 */

public class GsonResponseHandler implements IResponseHandler {
    private final Class<?> clazz;
    private NetWorkEvent mNetWorkEvent;
    public GsonResponseHandler(String urlName, Object tag, Class<?> clazz){
        this.clazz = clazz;
        mNetWorkEvent = new NetWorkEvent();
        mNetWorkEvent.mUrlName = urlName.replace(AppClass.getInstance().getHttpRequestHelper().getBaseUrl()+"/", "");
        mNetWorkEvent.tag = tag;
        mNetWorkEvent.flag = 1;
        mNetWorkEvent.result = null;
    }

    @Override
    public void onSuccess(String result) {
        try{
            Gson gson = new Gson();
            Object object = gson.fromJson(result, clazz);
            mNetWorkEvent.result = object;
            EventBus.getDefault().post(mNetWorkEvent);
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.logNetwodk("fail parse gson, body=" + result);
            onFailure(0, "fail parse gson, body=" + result);
        }
    }

    @Override
    public void onFailure(int statusCode, String error_msg) {
        EventBus.getDefault().post(mNetWorkEvent);
    }

}
