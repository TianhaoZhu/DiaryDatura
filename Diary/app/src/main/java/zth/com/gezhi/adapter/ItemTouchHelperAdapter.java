package zth.com.gezhi.adapter;

/**
 * Created by Administrator on 2018/3/30.
 */

public interface ItemTouchHelperAdapter {
    void onItemDelete(int position);
    void onItemMove(int fromPosition,int toPosition);

}
