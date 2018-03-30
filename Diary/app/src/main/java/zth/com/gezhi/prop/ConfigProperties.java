package zth.com.gezhi.prop;

/**
 * 配置文件
 * Created by Lenovo on 2017/2/6.
 */

public interface ConfigProperties {
    boolean showDebug = true;     //是否打印 LOG
    boolean isTestVersion = false; //是否为测试版本
    boolean isWriteCrashLog = false;  //是否记录闪退LOG，测试版本一般需要打开
    boolean isShowLogDebug = false; // 是否打印长LOG，注意测试版本一般不能打印
    boolean isAnalogReader = false; // 模拟读取RFid 添加test包 全局搜索 “模拟测试打开”
    boolean infilteHead = false;   //是否对标签进行过滤

    boolean disableCrashLogUpload = !isWriteCrashLog;   //是否停止CrashLog上传

    /**
     * URL
     */
    String testUrl = "http://www.i-gps.cn:8088/wcfservice/APPService.svc";//

    String useUrl = "http://193.112.58.27:8080/simbo";
    String baseUrl = isTestVersion ? testUrl : useUrl;
    String baseImgUrl = "";


    String NETWORK_ERROR = "网络异常";
    String SUCCESS = "SUCCESS";
    String FAIL = "FAIL";
    String ERROR = "ERROR";
    //注册
    String register = "user/registerUser";
    //登陆
    //String login = "login/appLogin";
    String login = "user/login";
    //更换密码
    String changePwd = "user/appModifyPassword";


//    {
//        "tel": "15929561310",
//            "password": "123"
//    }

}
