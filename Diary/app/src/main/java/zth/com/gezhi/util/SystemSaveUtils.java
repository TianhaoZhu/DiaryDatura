package zth.com.gezhi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;

import zth.com.gezhi.prop.StaticInfo;


/**
 * Created by yff on 2017/5/5.
 * 功能描述：
 */
public class SystemSaveUtils {

    private Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public int maxVolume, currentVolume;

    public SystemSaveUtils(Context context) {
        this.context = context;
        setUpSysData();
    }


    public void setUpSysData() {
        sharedPreferences = context.getSharedPreferences(StaticInfo.shareName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);

        editor.putInt(StaticInfo.volumeMaxLabel, maxVolume);
        editor.apply();
    }


    public void resetAll() {
        editor.putInt(StaticInfo.volumeMaxLabel, maxVolume);
        editor.putInt(StaticInfo.volumeLabel, StaticInfo.volume_init);


        editor.putInt(StaticInfo.inputPowerLabel, StaticInfo.input_power);
        editor.putInt(StaticInfo.allocationPowerLabel, StaticInfo.allocation_power);
        editor.putInt(StaticInfo.backToWarehousePowerLabel,StaticInfo.back_power);
        editor.putInt(StaticInfo.searchPowerLabel, StaticInfo.search_Power);
        editor.putInt(StaticInfo.inventoryPowerLabel, StaticInfo.inventory_Power);
        editor.putInt(StaticInfo.bindPowerLabel,StaticInfo.bind_power);
        editor.apply();
    }

    public void setUpVolume(int volume) {
        editor.putInt(StaticInfo.volumeLabel, volume);
        editor.apply();
    }

    public int getVolume() {
        return sharedPreferences.getInt(StaticInfo.volumeLabel, currentVolume);
    }


    public int getMaxVolume() {
        return sharedPreferences.getInt(StaticInfo.volumeMaxLabel, currentVolume);
    }


    public void setSearchPower(int power) {
        editor.putInt(StaticInfo.searchPowerLabel, power);
        editor.apply();
    }

    public int getSearchPower() {
        return sharedPreferences.getInt(StaticInfo.searchPowerLabel, StaticInfo.search_Power);
    }

    public void setInventoryPower(int power) {
        editor.putInt(StaticInfo.inventoryPowerLabel, power);
        editor.apply();
    }

    public int getInventoryPower() {
        return sharedPreferences.getInt(StaticInfo.inventoryPowerLabel, StaticInfo.inventory_Power);
    }

    public void setInputPower(int power) {
        editor.putInt(StaticInfo.inputPowerLabel, power);
        editor.apply();
    }

    public int getInputPower() {
        return sharedPreferences.getInt(StaticInfo.inputPowerLabel, StaticInfo.input_power);
    }


    public void setAllocationPower(int power) {
        editor.putInt(StaticInfo.allocationPowerLabel, power);
        editor.apply();
    }

    public int getAllocationPower() {
        return sharedPreferences.getInt(StaticInfo.allocationPowerLabel, StaticInfo.allocation_power);

    }
    public void setBackToWarehousePower(int power) {
        editor.putInt(StaticInfo.backToWarehousePowerLabel, power);
        editor.apply();
    }

    public int getBackToWarehousePower() {
        return sharedPreferences.getInt(StaticInfo.backToWarehousePowerLabel, StaticInfo.back_power);
    }

    public void setBindPower(int power) {
        editor.putInt(StaticInfo.bindPowerLabel, power);
        editor.apply();
    }

    public int getBindPower() {
        return sharedPreferences.getInt(StaticInfo.bindPowerLabel, StaticInfo.bind_power);
    }


    public void setBindTestPower(int power) {
        editor.putInt(StaticInfo.bindPowerTestLabel, power);
        editor.apply();
    }

    public int getBindTestPower() {
        return sharedPreferences.getInt(StaticInfo.bindPowerTestLabel, StaticInfo.bind_test_power);
    }

}
