package com.qixiu.intelligentcommunity.utlis;

import android.content.Context;
import android.content.DialogInterface;

import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class ArshowDialogUtils {
    public interface ArshowDialogListener {
        void onClickPositive(DialogInterface dialogInterface, int which);

        void onClickNegative(DialogInterface dialogInterface, int which);
    }

    public static ArshowDialog.Builder showDialog(Context activity, String message, final ArshowDialogListener onClickListener) {


        ArshowDialog.Builder builder = new ArshowDialog.Builder(activity);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onClickListener != null) {
                    onClickListener.onClickPositive(dialog, which);
                }

                dialog.dismiss();
            }
        });

        builder.setMessage(message);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onClickListener != null) {
                    onClickListener.onClickNegative(dialog, which);
                }
                dialog.dismiss();
            }
        });
        builder.show();

        return builder;
    }
}
