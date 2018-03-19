package zth.com.gezhi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import zth.com.gezhi.R;


/**
 * dialog net work
 * Created by Lenovo on 2017/2/14.
 */

public class ProgressNetDialog extends Dialog {


    public ProgressNetDialog(Context context) {
        super(context, R.style.my_dialog_show);
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_net_progress_dialog_view);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }
}
