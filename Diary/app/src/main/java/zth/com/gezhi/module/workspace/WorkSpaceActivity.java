package zth.com.gezhi.module.workspace;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zth.com.gezhi.R;
import zth.com.gezhi.base.BaseActivity;


public class WorkSpaceActivity extends BaseActivity {

    public static WorkSpaceActivity self;
    @BindView(R.id.btn_input)
    Button btnInput;
    @BindView(R.id.btn_allocation)
    Button btnAllocation;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.btn_bind)
    Button btnBind;
    @BindView(R.id.btn_inventory)
    Button btnInventory;
    @BindView(R.id.btn_setting)
    ImageView btnSetting;
    @BindView(R.id.shop_name)
    TextView tvShopName;
    @BindView(R.id.shop_num)
    TextView tvShopNum;
    @BindView(R.id.tv_bind_tag)
    TextView tvBindTag;
    @BindView(R.id.tv_bind_tag_sale_off)
    TextView tvBindTagSaleOff;

    private final String SHOW_TEST_BIND = "1";


    @Override
    public void setUpView() {
        setContentView(R.layout.activity_work_space);
        ButterKnife.bind(this);
        self = this;
//        if (SHOW_TEST_BIND.equals(AppClass.getInstance().getUser().getTagBindFlag())) {
//            tvBindTag.setVisibility(View.VISIBLE);
//            tvBindTagSaleOff.setVisibility(View.VISIBLE);
//        } else {
//            tvBindTag.setVisibility(View.GONE);
//            tvBindTagSaleOff.setVisibility(View.GONE);
//        }
        tvBindTag.setVisibility(View.VISIBLE);
        tvBindTagSaleOff.setVisibility(View.VISIBLE);
    }


    public static void selfFinish() {
        if (self != null) {
            self.finish();
            self = null;
        }
    }

    @Override
    protected void onDestroy() {
        if (self != null) {
            self = null;
        }
        super.onDestroy();
    }


}
