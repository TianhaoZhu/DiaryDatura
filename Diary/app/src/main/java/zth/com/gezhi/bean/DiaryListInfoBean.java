package zth.com.gezhi.bean;

import android.media.Image;

/**
 * Created by Administrator on 2018/3/29.
 */

public class DiaryListInfoBean extends BaseBean {
    public Image getImageTag() {
        return imageTag;
    }

    public void setImageTag(Image imageTag) {
        this.imageTag = imageTag;
    }

    public String getSimpleInfo() {
        return simpleInfo;
    }

    public void setSimpleInfo(String simpleInfo) {
        this.simpleInfo = simpleInfo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private Image imageTag;
    private String simpleInfo;
    private String time;
}
