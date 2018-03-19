package zth.com.gezhi.okhttp;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import zth.com.gezhi.okhttp.repsonsehandler.IResponseHandler;
import zth.com.gezhi.util.LogUtils;

/**
 *
 */
public class MyCallback implements Callback {

    private IResponseHandler mResponseHandler;

    public MyCallback(IResponseHandler responseHandler) {
        mResponseHandler = responseHandler;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mResponseHandler.onFailure(0, e.toString());
    }

    @Override
    public void onResponse(Call call, final Response response) {
            try {
                if(response.isSuccessful()) {
                    String resulteStr = response.body().string();
                    LogUtils.info("resulteStr :"+ resulteStr);
                    String substring = resulteStr.substring(1, resulteStr.length() - 1);
                    substring = substring.replace("\\\"", "\"");
                    mResponseHandler.onSuccess(substring);
                    LogUtils.info("SubresulteStr :"+ substring);
                } else {
                    mResponseHandler.onFailure(response.code(), "fail status=" + response.code());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null){
                    response.close();
                }
            }

    }
}
