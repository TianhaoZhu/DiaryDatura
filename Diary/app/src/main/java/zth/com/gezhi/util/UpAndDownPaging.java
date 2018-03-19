package zth.com.gezhi.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kotle on 2017/10/24.
 */

public class UpAndDownPaging<T> {
   public interface PagingUpListener<T> {
        void startUp(boolean lastUp, List<T> list);
    }

    public interface PagingDownListener<T> {
        void startDown(boolean lastDown, int start, int limit);
    }

    private int UP_LIMIT = 1000;
    private int DOWN_LIMIT = 1;
    private List<T> mAllUpList = new ArrayList<>();
    private List<T> mUpList = new ArrayList<>();
    private List<T> mDownList;
    private PagingUpListener upListener;
    private PagingDownListener downListener;
    private int downBeforeCount = 0;

    public UpAndDownPaging(List<T> mUpList, PagingUpListener upListener) {
        this.upListener = upListener;
        this.mAllUpList.addAll(mUpList);
    }

    public UpAndDownPaging(List<T> mDownList, PagingDownListener downListener) {
        this.mDownList = mDownList;
        this.downListener = downListener;
        if (mDownList == null) {
            throw new NullPointerException("mDownList is null");
        }
    }

    //开始上传，调用一次即可
    public void onStartUp() {
        if (mAllUpList.size() == 0) {
            return;
        }
        if (mAllUpList.size() > UP_LIMIT) {
            mUpList = mAllUpList.subList(0, UP_LIMIT);
            upListener.startUp(false, mUpList);
        } else {
            mUpList.addAll(mAllUpList);
            upListener.startUp(true, mUpList);
        }
    }

    //每次上传成功后调用
    public void onUpSuccess() {
        if (mAllUpList.removeAll(mUpList)) {
            mUpList.clear();
            onStartUp();
        }
    }

    //开始加载 ，每次加载手动调用
    public void onDownStart() {
        int size = mDownList.size();
        if (downBeforeCount != 0 && downBeforeCount == size) {
            downListener.startDown(true, downBeforeCount, 0);
        } else {
            downBeforeCount = size;
            downListener.startDown(false, downBeforeCount, DOWN_LIMIT);
        }
    }

    //加载成功，每次加载成功后需要调用
    public void onDownSuccess() {
        int size = mDownList.size();
        if (size == 0 || downBeforeCount == size || (size - downBeforeCount) < DOWN_LIMIT) {
            downListener.startDown(true, downBeforeCount, 0);
        }
    }

}
