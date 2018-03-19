package zth.com.gezhi.okhttp;

/**
 * Created by CYM on 2017/4/25.
 */
public class NetWorkEvent {
    public String mUrlName;
//    public Map<String, String> params;
    public Object tag;
    public Object result;
    public int statusCode;
    public int flag;

    @Override
    public String toString() {
        return "NetWorkEvent{" +
                "mUrlName='" + mUrlName + '\'' +
                ", tag=" + tag +
                ", result='" + result + '\'' +
//                "params='" + params + '\'' +
                ", statusCode=" + statusCode +
                ", flag=" + flag +
                '}';
    }
}
