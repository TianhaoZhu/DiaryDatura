package zth.com.gezhi.okhttp.buidler;


import com.alibaba.fastjson.JSON;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import zth.com.gezhi.okhttp.MyCallback;
import zth.com.gezhi.okhttp.repsonsehandler.IResponseHandler;
import zth.com.gezhi.okhttp.repsonsehandler.StringResponseHandler;
import zth.com.gezhi.util.LogUtils;

/**
 *
 */
public class PostBuilder extends BaseBuilder<PostBuilder> {

    private String mJsonParams;

    public PostBuilder(OkHttpClient okHttpClient){
        super(okHttpClient);
    }

    //append params to form builder
    private void appendParams(FormBody.Builder builder, Map<String, String> params) {

        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
    }

    /**
     * json格式参数
     * @param json
     * @return
     */
    public PostBuilder jsonParams(String json) {
        this.mJsonParams = json;
        return this;
    }

    /**
     * json格式参数
     * @param params
     * @return
     */
    public PostBuilder jsonParams(Map<String, Object> params) {
        this.mJsonParams = JSON.toJSONString(params);
        return this;
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
            Request.Builder builder = new Request.Builder().url(mUrl);
            appendHeaders(builder, mHeaders);

            if (mTag != null) {
                builder.tag(mTag);
            }

            if (mJsonParams != null && mJsonParams.length() > 0){ //上传json格式参数
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJsonParams);
                builder.post(body);
            } else { //普通kv参数
                FormBody.Builder encodingBuilder = new FormBody.Builder();
                appendParams(encodingBuilder, mParams);
                builder.post(encodingBuilder.build());
            }

            Request request = builder.build();

            try {
                LogUtils.logNetwodk("doPost:" + "url:" + mUrl);
                LogUtils.logNetwodk("headers:" + mHeaders.toString());
                StringBuilder sb=new StringBuilder();
                if (mJsonParams != null && mJsonParams.length() > 0){
                    LogUtils.logNetwodk("mJsonParams:" + mJsonParams.toString());
                }else {
                    LogUtils.logNetwodk("params:" + mParams.toString());
                    for (String key : mParams.keySet()) {
                        sb.append(key).append("=").append(mParams.get(key)).append("&");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
                LogUtils.logNetwodk("url:" + mUrl+"?"+sb);
            } catch (Exception e) {  //处理mParams.toString()过大时抛异常
                e.printStackTrace();
            }

            mOkHttpClient.newCall(request)
                    .enqueue(new MyCallback(responseHandler));
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
