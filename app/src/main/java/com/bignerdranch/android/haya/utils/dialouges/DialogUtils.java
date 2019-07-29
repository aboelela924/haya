package com.bignerdranch.android.haya.utils.dialouges;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.utils.networkUtils.NetworkUtils;
import com.bignerdranch.android.haya.view.activities.SigninActivity;

import org.w3c.dom.Text;

public class DialogUtils {

    public interface OnNetwrokBack{
        void onConnectionBack();
    }

    public interface AttrChange{
        void onChange(String attr);
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

    public static void confirmationDialouge(Context ctx, String title, String message, OnNetwrokBack onNetwrokBack){
        Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.confirmation_dialog);

        TextView titleTextView = dialog.findViewById(R.id.confirmation_dialog_title_text_view);
        TextView messageTextView = dialog.findViewById(R.id.confirmation_dialog_message_text_view);
        Button cancelButton = dialog.findViewById(R.id.confirmation_dialog_cancel_button);
        Button confirmButton = dialog.findViewById(R.id.confirmation_dialog_confirm_button);

        titleTextView.setText(title);
        messageTextView.setText(message);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNetwrokBack.onConnectionBack();
            }
        });

        dialog.show();
    }

    public static void changeAttributeDialog(Context ctx, String title, String attr,AttrChange attrChange){
        Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.update_chat_custom_name);

        TextView titleTextView = dialog.findViewById(R.id.update_custom_dialog_title_text_view);
        EditText attrEditText = dialog.findViewById(R.id.custom_chat_name_edit_text);
        Button cancelButton = dialog.findViewById(R.id.change_custom_name_cancel_button);
        Button confirmButton = dialog.findViewById(R.id.change_custom_name_edit_button);

        titleTextView.setText(title);
        attrEditText.setText(attr);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attrChange.onChange(attrEditText.getText().toString());
            }
        });

        dialog.show();
    }


}
