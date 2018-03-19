package zth.com.gezhi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zth.com.gezhi.AppClass;
import zth.com.gezhi.R;


/**
 * dialog net work
 * Created by Lenovo on 2017/2/14.
 */

public class ToastResultDialog extends Dialog {

    private int resourceId;
    private String textString;

    public ToastResultDialog(Context context, int resourceId, String textString) {
        super(context, R.style.my_dialog_show);
        this.resourceId=resourceId;
        this.textString=textString;
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_toast_result_view);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        ImageView iv=(ImageView)findViewById(R.id.iv_tips);
        TextView tv=(TextView)findViewById(R.id.text_tips);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(AppClass.getInstance().screenWidth*4/5, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv.setImageResource(resourceId);
        tv.setText(textString);

    }
}
