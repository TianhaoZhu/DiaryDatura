package zth.com.gezhi.okhttp;

import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import zth.com.gezhi.AppClass;
import zth.com.gezhi.okhttp.buidler.GetBuilder;
import zth.com.gezhi.okhttp.buidler.PostBuilder;
import zth.com.gezhi.okhttp.repsonsehandler.GsonResponseHandler;

/**
 * Created by CYM on 2017/4/24.
 */

public class HttpRequestHelper {
    private static OkHttpClient mOkHttpClient;

    private static String mBaseUrl = "";

    private HttpRequestHelper() {}  //不创建实例

    /** 创建OkHttpClient实例
     * @param okHttpClient 为null时，使用默认配置
     *
     */
    public HttpRequestHelper(OkHttpClient okHttpClient){
        if(mOkHttpClient == null) {
            synchronized (HttpRequestHelper.class) {
                if (mOkHttpClient == null) {
                    if (okHttpClient == null) {
                        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        builder.connectTimeout(30, TimeUnit.SECONDS);  //设置连接超时时间
                        builder.writeTimeout(30, TimeUnit.SECONDS);//设置写入超时时间
                        builder.readTimeout(30, TimeUnit.SECONDS);//设置读取数据超时时间
                        builder.retryOnConnectionFailure(false);//设置不进行连接失败重试
//                         builder.cache(cache);//配置get缓存
                        mOkHttpClient = builder.build();
                    } else {
                        mOkHttpClient = okHttpClient;
                    }
                }
            }
        }
    }

    /** 配置域名
     * @param baseUrl
     */
    public void setBaseUrl(String baseUrl){
        mBaseUrl = baseUrl;
    }

    /** 获取域名
     * @return mBaseUrl
     */
    public String getBaseUrl() {
        return mBaseUrl;
    }

    /** get请求，通过eventbus的订阅，可接收到String结果
     * @param urlName
     * @param headers
     * @param params
     * @param tag 标识，一般为发起请求的对象
     */
    public void doGet(String urlName, Map<String, String> headers, Map<String, String> params, Object tag){
        GetBuilder getBuilder = new GetBuilder(mOkHttpClient);
        getBuilder.tag(tag)
                .url(getBaseUrl()+"/"+urlName)
                .headers(headers)
                .params(params)
                .enqueue();
    }

    /** get请求，通过eventbus的订阅，可接收到String结果
     * @param urlName
     * @param params
     * @param tag 标识，一般为发起请求的对象
     * @param clazz 返回结果为该类实例
     */
    public void doGet(String urlName, Map<String, String> headers, Map<String, String> params, Object tag, Class<?> clazz){
        GetBuilder getBuilder = new GetBuilder(mOkHttpClient);
        getBuilder.tag(tag)
                .url(getBaseUrl()+"/"+urlName)
                .headers(headers)
                .params(params)
                .enqueue(new GsonResponseHandler(urlName, tag, clazz));  //返回结果为clazz实例
    }

    /** Post请求，通过eventbus的订阅，可接收到String结果
     * @param urlName
     * @param headers
     * @param params
     * @param tag 标识，一般为发起请求的对象
     */
    public void doPostJson(String urlName, Map<String, String> headers, Map<String, Object> params, Object tag){
        String url =getBaseUrl() + "/" + urlName
                + "?userId=" + AppClass.getInstance().getUser().getId()
                + "&clientId=" + AppClass.getInstance().getUser().getClientId();

        params.put("userId", AppClass.getInstance().getUser().getId());
        params.put("clientId", AppClass.getInstance().getUser().getClientId());

        PostBuilder postBuilder = new PostBuilder(mOkHttpClient);
        postBuilder.tag(tag)
                .url(url)
                .addHeader("user-agent","android")
                .jsonParams(params)
                .enqueue();
        //.enqueue(new IResponseHandlerTest(urlName, tag));  //使用 已有的handler方式验证
    }
    /** Post请求，通过eventbus的订阅，可接收到String结果
     * @param urlName
     * @param headers
     * @param params
     * @param tag 标识，一般为发起请求的对象
     */
    public void doLoginPostJson(String urlName, Map<String, String> headers, Map<String, Object> params, Object tag){
        String url =getBaseUrl() + "/" + urlName;
//                + "?userId=" + AppClass.getInstance().getUser().getId()
//                + "&clientId=" + AppClass.getInstance().getUser().getClientId();

//        params.put("userId", AppClass.getInstance().getUser().getId());
//        params.put("clientId", AppClass.getInstance().getUser().getClientId());

        PostBuilder postBuilder = new PostBuilder(mOkHttpClient);
        postBuilder.tag(tag)
                .url(url)
                .addHeader("user-agent","android")
                .jsonParams(params)
                .enqueue();
        //.enqueue(new IResponseHandlerTest(urlName, tag));  //使用 已有的handler方式验证
    }
    /** Post请求，通过eventbus的订阅，可接收到String结果
     * @param urlName
     * @param headers
     * @param json
     * @param tag 标识，一般为发起请求的对象
     */
    public void doPostJson(String urlName, Map<String, String> headers, String json, Object tag){
        if (!json.endsWith("}")){
            throw new RuntimeException("doPostJson不能直接传数组:" + json);
        }

        json = json.substring(0, json.length()-1)
//                + ",\"userId\":" + AppClass.getInstance().getUser().getId()
//                + ",\"clientId\":" + AppClass.getInstance().getUser().getClientId()
                + "}";

        String url =getBaseUrl() + "/" + urlName;
//                + "?userId=" + AppClass.getInstance().getUser().getId()
//                + "&clientId=" + AppClass.getInstance().getUser().getClientId();

        PostBuilder postBuilder = new PostBuilder(mOkHttpClient);
        postBuilder.tag(tag)
                .url(url)
                .addHeader("user-agent","android")
                .jsonParams(json)
                .enqueue();
        //.enqueue(new IResponseHandlerTest(urlName, tag));  //使用 已有的handler方式验证
    }

    /** Post请求，通过eventbus的订阅，可接收到String结果
     * @param urlName
     * @param headers
     * @param params
     * @param tag 标识，一般为发起请求的对象
     */
    public void doPost(String urlName, Map<String, String> headers, Map<String, String> params, Object tag){
        PostBuilder postBuilder = new PostBuilder(mOkHttpClient);
        postBuilder.tag(tag)
                .url(getBaseUrl()+"/"+urlName)
                .addHeader("user-agent","android")
                .params(params)
                .addParam("userId", AppClass.getInstance().getUser().getId()+"")
                .addParam("clientId", AppClass.getInstance().getUser().getClientId()+"")
                .enqueue();
        //.enqueue(new IResponseHandlerTest(urlName, tag));  //使用 已有的handler方式验证
    }

    /** Post请求，通过eventbus的订阅，可接收到String结果
     * @param urlName
     * @param headers
     * @param params
     * @param tag 标识，一般为发起请求的对象
     * @param clazz 返回请求结果的bean
     */
    public void doPost(String urlName, Map<String, String> headers, Map<String, String> params, Object tag, Class<?> clazz){
        PostBuilder postBuilder = new PostBuilder(mOkHttpClient);
        postBuilder.tag(tag)
                .url(getBaseUrl()+"/"+urlName)
                .addHeader("user-agent","android")
                .params(params)
                .addParam("userId", AppClass.getInstance().getUser().getId()+"")
                .addParam("clientId", AppClass.getInstance().getUser().getClientId()+"")
                .enqueue(new GsonResponseHandler(urlName, tag, clazz));  //返回结果为clazz实例
    }

    /** Post请求，通过eventbus的订阅，可接收到String结果
     * @param urlName
     * @param params
     * @param tag 标识，一般为发起请求的对象
     */
    public void doLoginPost(String urlName, Map<String, String> params, Object tag){
        PostBuilder postBuilder = new PostBuilder(mOkHttpClient);
        postBuilder.tag(tag)
                .url(getBaseUrl()+"/"+urlName)
                .addHeader("user-agent","android")
                .params(params)
                .enqueue();
                //.enqueue(new IResponseHandlerTest(urlName, tag));
    }

    /** Post请求，通过eventbus的订阅，可接收到String结果
     * @param urlName
     * @param params
     * @param tag 标识，一般为发起请求的对象
     * @param clazz 返回请求结果的bean
     */
    public void doLoginPost(String urlName, Map<String, String> params, Object tag, Class<?> clazz){
        PostBuilder postBuilder = new PostBuilder(mOkHttpClient);
        postBuilder.tag(tag)
                .url(getBaseUrl()+"/"+urlName)
                .addHeader("user-agent","android")
                .params(params)
                .enqueue(new GsonResponseHandler(urlName, tag, clazz));  //返回结果为clazz实例
    }

    /**
     * do cacel by tag
     * @param tag tag
     */
    public void cancel(Object tag) {
        if (tag == null){
            return;
        }

        Dispatcher dispatcher = mOkHttpClient.dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /**
     * 是否已在请求队列中
     * @param tag
     * @param urlName
     * @return
     */
    public boolean isRequestInQueue(Object tag, String urlName){
        if (tag == null && urlName!=null && TextUtils.isEmpty(urlName.trim())){
            Dispatcher dispatcher = mOkHttpClient.dispatcher();
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag()) && call.request().url().toString().endsWith(urlName)) {
                    return true;
                }
            }
        }

        return false;
    }
}

