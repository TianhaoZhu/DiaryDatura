package zth.com.gezhi.module.setting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zth.com.gezhi.AppClass;
import zth.com.gezhi.R;
import zth.com.gezhi.base.BaseActivity;
import zth.com.gezhi.bean.BaseBean;
import zth.com.gezhi.beanjson.LoginJsonBean;
import zth.com.gezhi.okhttp.NetWorkEvent;
import zth.com.gezhi.okhttp.util.NetWorkAnnotation;
import zth.com.gezhi.prop.ConfigProperties;

public class ChangePwdActivity extends BaseActivity {


    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.et_origianal_pwd)
    EditText etOrigianalPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_confirm_pwd)
    EditText etConfirmPwd;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.iv_original)
    ImageView ivOriginal;
    @BindView(R.id.iv_new)
    ImageView ivNew;
    @BindView(R.id.iv_confirm)
    ImageView ivConfirm;


    private boolean showOriginalPwd = false, showNewPwd = false, showConfirmPwd = false;

    private Gson gson;
    private String password;

    @Override
    protected void setNeedRegister() {
        isRegisterEvent = true;
    }

    @Override
    public void setUpView() {
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);

        gson = new Gson();
        tvUserName.setText(AppClass.getInstance().getUser().getName());

    }


    @OnClick({R.id.iv_original, R.id.iv_new, R.id.iv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_original:
                showOriginalPwd=changePwdState(etOrigianalPwd, showOriginalPwd,ivOriginal);
                break;
            case R.id.iv_new:
                showNewPwd=changePwdState(etNewPwd, showNewPwd,ivNew);
                break;
            case R.id.iv_confirm:
                showConfirmPwd=changePwdState(etConfirmPwd, showConfirmPwd,ivConfirm);
                break;
        }
    }


    public boolean changePwdState(@NonNull EditText editText, boolean showValue, @NonNull ImageView ivShow) {
        if (showValue) {
            ivShow.setImageResource(R.mipmap.ic_hide_pwd);
            showValue = false;
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivShow.setImageResource(R.mipmap.ic_see_pwd);
            showValue = true;
        }
        editText.setSelection(editText.getText().toString().length());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        return showValue;
    }


    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        String origianlPwd = etOrigianalPwd.getText().toString().trim();
        String newPwd = etNewPwd.getText().toString().trim();
        String confirmPwd = etConfirmPwd.getText().toString().trim();
        if (TextUtils.isEmpty(origianlPwd)) {
            showImageToast(R.mipmap.ic_warning, getString(R.string.originalCanNotEmpty), false);
            return;
        }
        if (TextUtils.isEmpty(newPwd)) {
            showImageToast(R.mipmap.ic_warning, getString(R.string.newCanNotEmpty), false);
            return;
        }
        if (TextUtils.isEmpty(confirmPwd)) {
            showImageToast(R.mipmap.ic_warning, getString(R.string.ConfirmCanNotEmpty), false);
            return;
        }

        if (newPwd.length() < 6) {
            showImageToast(R.mipmap.ic_warning, getString(R.string.pwdLenghtTooShort), false);
            return;
        }

        if (confirmPwd.length() < 6) {
            showImageToast(R.mipmap.ic_warning, getString(R.string.pwdLenghtTooShort), false);
            return;
        }

        if (!TextUtils.equals(origianlPwd, AppClass.getInstance().getUser().getPassword())) {
            showImageToast(R.mipmap.ic_warning, getString(R.string.originalWrong), false);
            return;
        }

        if (!TextUtils.equals(newPwd, confirmPwd)) {
            showImageToast(R.mipmap.ic_warning, getString(R.string.pwdIsDifferent), false);
            return;
        }

        password = newPwd;
        showProgressNetDialog();
        HashMap<String, String> params = new HashMap<>();
        params.put("oldPassword", AppClass.getInstance().getUser().getPassword());
        params.put("newPassword", password);
        AppClass.getInstance()
                .getHttpRequestHelper()
                .doPost(ConfigProperties.changePwd, null, params, this);

    }

    @NetWorkAnnotation(url = ConfigProperties.changePwd)
    public void getNetWorkEvent(NetWorkEvent event) {
        dismissProgressNetDialog();
        if (event.result == null) {
            showImageToast(R.mipmap.ic_warning, NETWORK_ERROR, false);
            return;
        }
        BaseBean jsonBean = gson.fromJson(event.result.toString(), BaseBean.class);
        if (jsonBean.getStatus().equals(FAIL)||jsonBean.getStatus().equals(ERROR)) {
            showImageToast(R.mipmap.ic_warning, jsonBean.getMsg(), false);
            return;
        }
        showImageToast(R.mipmap.ic_okay, getString(R.string.changePwdSuccess), false);
        LoginJsonBean.LoginData userBean = AppClass.getInstance().getUser();
        userBean.setPassword(password);
        AppClass.getInstance().setUser(userBean);
    }


}
