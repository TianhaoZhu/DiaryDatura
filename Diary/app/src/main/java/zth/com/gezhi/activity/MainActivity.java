package zth.com.gezhi.activity;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import zth.com.gezhi.AppClass;
import zth.com.gezhi.R;
import zth.com.gezhi.base.BaseActivity;
import zth.com.gezhi.okhttp.NetWorkEvent;
import zth.com.gezhi.okhttp.util.NetWorkAnnotation;
import zth.com.gezhi.prop.ConfigProperties;
import zth.com.gezhi.util.LogUtils;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.btn_scan_rfid)
    Button btnRfid;
    @BindView(R.id.btn_scan_code)
    Button btnScanCode;
    @BindView(R.id.btn_network)
    Button btnNetwork;

    @Override
    protected void setNeedRegister() {
        isRegisterEvent = true;   //网络请求必须添加的
    }

    @Override
    public void setUpView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_network:
                getNetWork();
                break;
        }
    }

    public void getNetWork() {
        tvMsg.setText(String.format("%s", "网络请求中。。。"));
//        ArrayList<String> list = new ArrayList<>();
//        list.add("epc值1");
//        list.add("epc值2");
//        list.add("epc值3");
//        list.add("epc值4");
        HashMap<String, String> params = new HashMap<>();
//        params.put("username", "admin");
//        params.put("password", "123456");
//        params.put("epcs", list.toString().substring(1, list.toString().length() - 1));
//        params.put("app", 1 + "");
        AppClass.getInstance()
                .getHttpRequestHelper()
                .doLoginPost(ConfigProperties.login, params, this);
    }
    @NetWorkAnnotation(url = ConfigProperties.login)
    public void getNetworkDataEvent(NetWorkEvent event) {
        LogUtils.info("结果回调了");
        //网络错误
        if (event.result == null) {
            showImageToast(R.mipmap.ic_warning, NETWORK_ERROR, false);
            return;
        }
        tvMsg.setText(String.format("%s", "网络请求成功"));

    }
}
