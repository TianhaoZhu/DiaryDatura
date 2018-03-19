package zth.com.gezhi.okhttp.buidler;


import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import zth.com.gezhi.okhttp.MyCallback;
import zth.com.gezhi.okhttp.repsonsehandler.IResponseHandler;
import zth.com.gezhi.okhttp.repsonsehandler.StringResponseHandler;
import zth.com.gezhi.util.LogUtils;


/**
 *
 */
public class GetBuilder extends BaseBuilder<GetBuilder> {

    public GetBuilder(OkHttpClient okHttpClient){
        super(okHttpClient);
    }

    /**
     * @param url append params to url
     * @param params
     * @return
     */
    private String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 异步执行
     * @param responseHandler 自定义回调
     */
    @Override
    public void enqueue(IResponseHandler responseHandler) {
        try {
            if(mUrl == null || mUrl.length() == 0) {
                throw new IllegalArgumentException("url can not be null !");
            }

            if (mParams != null && mParams.size() > 0) {
                mUrl = appendParams(mUrl, mParams);
            }

            Request.Builder builder = new Request.Builder().url(mUrl).get();
            appendHeaders(builder, mHeaders);

            if (mTag == null) {
                if (mTag == null){
                    throw new IllegalArgumentException("tag can not be null !");
                }
            } else{
                builder.tag(mTag);
            }

            Request request = builder.build();

            LogUtils.logNetwodk("doPost:" + "url:" + mUrl);
            LogUtils.logNetwodk("headers:" + mHeaders.toString());
            LogUtils.logNetwodk("params:" + mParams.toString());

            mOkHttpClient.
                    newCall(request).
                    enqueue(new MyCallback(responseHandler));
        } catch (Exception e) {
            LogUtils.logNetwodk("onFailure:" + "url:" + mUrl +  e.getMessage());
            responseHandler.onFailure(0, e.getMessage());
        }
    }

    /**
     * 异步执行 使用已有回调
     */
    public void enqueue() {
        StringResponseHandler responseHandler = new StringResponseHandler(mUrl, mTag);
        enqueue(responseHandler);
    }
}
