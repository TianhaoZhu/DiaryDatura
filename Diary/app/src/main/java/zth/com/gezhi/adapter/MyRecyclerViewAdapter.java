package zth.com.gezhi.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import zth.com.gezhi.R;
import zth.com.gezhi.bean.DiaryListInfoBean;

/**
 * Created by Administrator on 2018/3/29.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements ItemTouchHelperAdapter{
    private ArrayList<DiaryListInfoBean> mData;

    //private ArrayList<Integer> mHeights = new ArrayList<Integer>();

    public MyRecyclerViewAdapter(ArrayList<DiaryListInfoBean> mData) {
        this.mData = mData;
//        for (int i = 0; i < mData.size(); ++i) {
//            mHeights.add((int) (Math.random() * 1000));
//        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.ivTag.setImageDrawable();
        holder.tvSimpleInfo.setText(mData.get(position).getSimpleInfo());
        holder.tvTime.setText(mData.get(position).getTime());
//        ViewGroup.LayoutParams layoutParams = holder.cvDiaryCard.getLayoutParams();
//        layoutParams.height = mHeights.get(position);
//        holder.cvDiaryCard.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onItemDelete(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(mData,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_tag)
        ImageView ivTag;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_simple_info)
        TextView tvSimpleInfo;
        @BindView(R.id.cv_diary_card)
        CardView cvDiaryCard;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cvDiaryCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }
}
