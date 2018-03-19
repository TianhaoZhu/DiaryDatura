package zth.com.gezhi.okhttp.repsonsehandler;


import org.greenrobot.eventbus.EventBus;

import zth.com.gezhi.AppClass;
import zth.com.gezhi.okhttp.NetWorkEvent;

/**
 * Created by CYM on 2017/4/25.
 */

public class StringResponseHandler implements IResponseHandler {
    private NetWorkEvent mNetWorkEvent;
    public StringResponseHandler(String urlName, Object tag){
        mNetWorkEvent = new NetWorkEvent();
        mNetWorkEvent.mUrlName = urlName.replace(AppClass.getInstance().getHttpRequestHelper().getBaseUrl()+"/", "");
        mNetWorkEvent.tag = tag;
        mNetWorkEvent.flag = 1;
        mNetWorkEvent.result = null;
    }

    @Override
    public void onSuccess(String result) {
            mNetWorkEvent.result = result;
            EventBus.getDefault().post(mNetWorkEvent);
    }

    @Override
    public void onFailure(int statusCode, String error_msg) {
        EventBus.getDefault().post(mNetWorkEvent);
    }

}
