package zth.com.gezhi.module.workspace;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import zth.com.gezhi.R;
import zth.com.gezhi.adapter.MyRecyclerViewAdapter;
import zth.com.gezhi.adapter.SimpleItemTouchHelperCallback;
import zth.com.gezhi.base.BaseActivity;
import zth.com.gezhi.bean.DiaryListInfoBean;


public class WorkSpaceActivity extends BaseActivity {
    ArrayList<DiaryListInfoBean> diaryListInfoBeanArrayList = new ArrayList<>();
    @BindView(R.id.rv_diary_list)
    RecyclerView rvDiaryList;

    @Override
    public void setUpView() {
        setContentView(R.layout.activity_work_space);
        ButterKnife.bind(this);
        init();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDiaryList.setLayoutManager(linearLayoutManager);
        //rvDiaryList.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(diaryListInfoBeanArrayList);
        myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showToast(""+position);
            }
        });
        rvDiaryList.setAdapter(myRecyclerViewAdapter);

        //先实例化Callback
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(myRecyclerViewAdapter);
        //用Callback构造ItemtouchHelper
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        touchHelper.attachToRecyclerView(rvDiaryList);
    }

    public void init() {
        for (int i = 0; i < 30; i++) {
            DiaryListInfoBean diaryListInfoBean = new DiaryListInfoBean();
            diaryListInfoBean.setSimpleInfo("simple info" + i);
            diaryListInfoBean.setTime("time" + i);
            diaryListInfoBeanArrayList.add(diaryListInfoBean);
        }
    }

}
