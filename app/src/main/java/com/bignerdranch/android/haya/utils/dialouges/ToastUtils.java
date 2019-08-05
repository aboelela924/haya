package com.bignerdranch.android.haya.utils.dialouges;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.haya.R;

public class ToastUtils {

    public static void showErrorToast(Context ctx, String message){
        View toastView = LayoutInflater.from(ctx).inflate(R.layout.error_toast, null);
        TextView textView = toastView.findViewById(R.id.toast_error_message_text_view);
        textView.setText(message);
        Toast toast = new Toast(ctx);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,30);
        toast.show();
    }

}
