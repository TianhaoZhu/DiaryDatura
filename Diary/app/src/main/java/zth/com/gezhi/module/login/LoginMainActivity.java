package zth.com.gezhi.module.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mob.MobSDK;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import zth.com.gezhi.AppClass;
import zth.com.gezhi.R;
import zth.com.gezhi.base.BaseActivity;
import zth.com.gezhi.okhttp.NetWorkEvent;
import zth.com.gezhi.okhttp.util.NetWorkAnnotation;
import zth.com.gezhi.prop.ConfigProperties;

public class LoginMainActivity extends BaseActivity {


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.btn_register)
    TextView register;

    @Override
    protected void setNeedRegister() {
        isRegisterEvent = true;
    }

    @Override
    public void setUpView() {
        setContentView(R.layout.activity_login_main);
        ButterKnife.bind(this);

        MobSDK.init(this);

       // sendCode(this);

        register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        register.getPaint().setAntiAlias(true);//抗锯齿

        SharedPreferences sharedPreferences = getSharedPreferences(userShareName, Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user", "");
        String pwd = sharedPreferences.getString("passwd", "");
        boolean check = sharedPreferences.getBoolean("check", false);

        if (check) {
            username.setText(user);
            password.setText(pwd);
            username.setSelection(user.length());
            password.setSelection(pwd.length());
        }
    }



    @OnClick({R.id.button, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                submitData();
                break;
            case R.id.btn_register:
                ARouter.getInstance().build("/user/register").navigation();
                break;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    };

    public void submitData() {
        String userNameString = username.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(userNameString)) {
            showImageToast(R.mipmap.ic_warning, getString(R.string.userNameEmpty), false);
            return;
        }
        if (TextUtils.isEmpty(passwordString)) {
            showImageToast(R.mipmap.ic_warning, getString(R.string.pwdEmpty), false);
            return;
        }
        showProgressNetDialog();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, String> headers = new HashMap<>();
        params.put("username", userNameString);
        params.put("password", passwordString);
        params.put("app", 1 + "");
        AppClass.getInstance().getHttpRequestHelper()
                .doLoginPostJson(ConfigProperties.login, headers, params, this);

    }

    @NetWorkAnnotation(url = ConfigProperties.login)
    public void getNetWorkEnvent(NetWorkEvent event) {
        dismissProgressNetDialog();
        if (event.result == null) {
            showImageToast(R.mipmap.ic_warning, NETWORK_ERROR, false);
            return;
        }
//        LoginJsonBean jsonBean = gson.fromJson(event.result.toString(), LoginJsonBean.class);
//        if (jsonBean.status.equals(FAIL) || jsonBean.status.equals(ERROR)) {
//            showImageToast(R.mipmap.ic_warning, jsonBean.msg, false);
//            return;
//        }
//        //登陆成功数据保存
//        LoginJsonBean.LoginData user = jsonBean.data;
//        user.setPassword(passwordString);
//        user.setWarehouseName(user.dname);
//        AppClass.getInstance().setUser(user);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("user", userNameString);
//        editor.putString("passwd", passwordString);
//        editor.putBoolean("check", checkBox.isChecked());
//        editor.apply();
//        startActivity(new Intent(LoginMainActivity.this, WorkSpaceActivity.class));
//        finish(false);
    }

    @Override
    public void finish() {

    }
}
