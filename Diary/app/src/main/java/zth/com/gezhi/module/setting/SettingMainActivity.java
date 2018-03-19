package zth.com.gezhi.module.setting;

import android.content.Intent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zth.com.gezhi.R;
import zth.com.gezhi.base.BaseActivity;

public class SettingMainActivity extends BaseActivity {


    @BindView(R.id.seek_volume)
    SeekBar seekVolume;
    @BindView(R.id.linear_change)
    LinearLayout linearChange;
    @BindView(R.id.linear_help)
    LinearLayout linearHelp;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    public void setUpView() {
        setContentView(R.layout.activity_setting_main);
        ButterKnife.bind(this);

       tvVersion.setText(String.format(Locale.US,"%s","V "+getVersionName()));

        seekVolume.setMax(systemSaveUtils.getMaxVolume());
        seekVolume.setProgress(systemSaveUtils.getVolume());
        seekVolume.setOnSeekBarChangeListener(seekBarChangeListener);

    }



    public SeekBar.OnSeekBarChangeListener seekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            seekVolume.setProgress(progress);
            systemSaveUtils.setUpVolume(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


    @OnClick(R.id.linear_change)
    public void onLinearChangeClicked() {
        Intent intent = new Intent(SettingMainActivity.this, ChangePwdActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.linear_help)
    public void onLinearHelpClicked() {
    }


    @OnClick(R.id.btn_exit)
    public void onBtnExitClicked() {

    }

}
