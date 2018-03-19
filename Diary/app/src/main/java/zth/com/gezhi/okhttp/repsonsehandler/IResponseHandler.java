package zth.com.gezhi.okhttp.repsonsehandler;

/**
 * Created by tsy on 16/8/15.
 */
public interface IResponseHandler {

    void onSuccess(String result);

    void onFailure(int statusCode, String error_msg);

}
