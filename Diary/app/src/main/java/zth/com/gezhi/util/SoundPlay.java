package zth.com.gezhi.util;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import zth.com.gezhi.R;


public class SoundPlay {

	private SoundPool mSound;
	private int mSuccess;
	private int mFail;
	private float volume;
	
	public SoundPlay(Context context, int volume, int maxVolume) {
		mSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mSuccess = mSound.load(context, R.raw.success, 1);
		mFail = mSound.load(context, R.raw.fail, 1);
		this.volume=( (float) volume)/maxVolume;
	}
	
	public void playSuccess() {
		mSound.play(mSuccess, 1, 1, 0, 0, 1);
	}
	
	public void playFail() {
		mSound.play(mFail, 1, 1, 0, 0, 1);
	}

	public void playSuccess(int volume,int maxVolume) {
		float playVolume=((float)volume)/maxVolume;
		if(maxVolume<1){
			playVolume=((float)volume*0.1f)/maxVolume;
		}
		mSound.play(mSuccess,playVolume,playVolume, 0, 0, 1);
	}

	public void playFail(int volume,int maxVolume) {
		mSound.play(mFail, volume, volume, 0, 0, 1);
	}


}
