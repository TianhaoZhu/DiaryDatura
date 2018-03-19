package zth.com.gezhi.okhttp.buidler;


import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import zth.com.gezhi.okhttp.repsonsehandler.IResponseHandler;

/**
 *
 */
public abstract class BaseBuilder<T extends BaseBuilder> {
    protected Object mTag;
    protected String mUrl;
    protected Map<String, String> mHeaders;
    protected Map<String, String> mParams;
    protected OkHttpClient mOkHttpClient;

    public BaseBuilder(OkHttpClient okHttpClient){
        this.mOkHttpClient = okHttpClient;
    }

    /**
     * set tag
     * @param tag tag
     * @return
     */
    public T tag(Object tag)
    {
        this.mTag = tag;
        return (T) this;
    }

    /****  设置url *************/
    /**
     * set url
     * @param url url
     * @return
     */
    public T url(String url)
    {
        this.mUrl = url;
        return (T) this;
    }

    /****  设置headers *************/
    /**
     * set headers
     * @param headers headers
     * @return
     */
    public T headers(Map<String, String> headers)
    {
        if (this.mHeaders == null)
        {
            mHeaders = new LinkedHashMap<>();
        }
        this.mHeaders.putAll(headers);
        return (T) this;
    }

    /**
     * set one header
     * @param key header key
     * @param val header val
     * @return
     */
    public T addHeader(String key, String val)
    {
        if (this.mHeaders == null)
        {
            mHeaders = new LinkedHashMap<>();
        }
        mHeaders.put(key, val);
        return (T) this;
    }

    //append headers into builder
    protected void appendHeaders(Request.Builder builder, Map<String, String> headers) {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    /****  设置params *************/
    /**
     * set Map
     * @param params
     * @return
     */
    public T params(Map<String, String> params) {
        if (params==null){
            return (T) this;
        }
        if (this.mParams == null)
        {
            mParams = new LinkedHashMap<>();
        }
        this.mParams.putAll(params);
        return (T) this;
    }

    /**
     * add param
     * @param key param key
     * @param val param val
     * @return
     */
    public T addParam(String key, String val) {
        if (this.mParams == null)
        {
            mParams = new LinkedHashMap<>();
        }
        mParams.put(key, val);
        return (T) this;
    }

    /**
     * 异步执行
     * @param responseHandler 自定义回调
     */
    public abstract void enqueue(IResponseHandler responseHandler);
}
