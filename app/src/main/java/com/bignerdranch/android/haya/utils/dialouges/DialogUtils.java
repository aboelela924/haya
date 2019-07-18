package com.bignerdranch.android.haya.utils.dialouges;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.utils.networkUtils.NetworkUtils;
import com.bignerdranch.android.haya.view.activities.SigninActivity;

public class DialogUtils {

    public interface OnNetwrokBack{
        void onConnectionBack();
    }

    public static void noInternetConnectionDialog(Context context, OnNetwrokBack netwrokBack){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.no_connection);
        dialog.setTitle("No Connection");
        dialog.setCanceledOnTouchOutside(false);
        Button button = (Button) dialog.findViewById(R.id.refresh_button_no_conncection_dialouge);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkUtils.isNetworkConnected(context) ){
                    dialog.dismiss();
                    if(netwrokBack != null){
                        netwrokBack.onConnectionBack();
                    }
                }
            }
        });


        dialog.show();
    }

}
