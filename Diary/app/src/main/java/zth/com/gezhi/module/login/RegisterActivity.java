package zth.com.gezhi.module.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.HashMap;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import zth.com.gezhi.AppClass;
import zth.com.gezhi.R;
import zth.com.gezhi.base.BaseActivity;
import zth.com.gezhi.okhttp.util.NetUtil;
import zth.com.gezhi.prop.ConfigProperties;
import zth.com.gezhi.util.LogUtils;

@Route(path = "/user/register")
public class RegisterActivity extends BaseActivity implements TextWatcher {
    @BindView(R.id.et_phonenum)
    EditText etPhonenum;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_register)
    Button btnRegister;

    final Long TOATALTIME = 120 * 1000L;
    final Long INTERVALTIME = 1000L;
    String phonenum = "";

    @Override
    protected void setNeedRegister() {
        isRegisterEvent = true;
    }

    @Override
    public void setUpView() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        etPhonenum.addTextChangedListener(this);
        init();
    }

    private void init() {
        etPhonenum.setText("");
        etPassword.setText("");
        etCode.setText("");
        tvSendCode.setEnabled(false);
        //btnRegister.setEnabled(false);
    }

    @OnClick(R.id.tv_send_code)
    public void onTvSendCodeClicked() {
        tvSendCode.setEnabled(false);
        countDownTimer.start();
        phonenum = etPhonenum.getText().toString().trim();
        sendCode("86", phonenum);
    }

    @OnClick(R.id.btn_register)
    public void onBtnRegisterClicked() {
        //submitCode("86", phonenum, etCode.getText().toString().trim());
        Register();
    }

    /**
     *  * 验证手机号码
     *  *
     *  * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     *  * 联通号码段:130、131、132、136、185、186、145
     *  * 电信号码段:133、153、180、189
     *  *
     *  * @param cellphone
     *  * @return
     *  
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位
        return Pattern.matches(regex, cellphone);
    }

    private CountDownTimer countDownTimer = new CountDownTimer(TOATALTIME, INTERVALTIME) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvSendCode.setText("发送验证码（" + millisUntilFinished / 1000 + "）");
        }

        @Override
        public void onFinish() {
            tvSendCode.setEnabled(true);
        }
    };

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                } else {
                    // TODO 处理错误的结果
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Register();
                    break;
                case 1:
                    showToast("验证码不正确");
                    break;
            }
        }
    };

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    LogUtils.info("验证码正确");
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                } else {
                    // TODO 处理错误的结果
                    LogUtils.info("验证码不正确");
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    private void Register() {
        HashMap<String, Object> params = new HashMap<>();
//        params.put("tel", phonenum);
        params.put("tel", etPhonenum.getText().toString().trim());
        params.put("password", etPassword.getText().toString().trim());
        NetUtil.doPostNoHead(ConfigProperties.register, params, this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (checkCellphone(s.toString())) {
            tvSendCode.setEnabled(true);
        }
    }
}
