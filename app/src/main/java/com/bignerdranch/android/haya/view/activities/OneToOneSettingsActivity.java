package com.bignerdranch.android.haya.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.utils.dialouges.DialogUtils;
import com.bignerdranch.android.haya.viewModel.ChatSettingsViewModel;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneToOneSettingsActivity extends AppCompatActivity {

    private static final String CHAT_NAME = "CHAT_NAME";
    private static final String IS_PRIVATE = "IS_PRIVATE";
    private static final String USER = "USER";
    private static final String ROOM = "ROOM";
    private static final String TAG = "OneToOneSettingsActivit";

    private ChatSettingsViewModel mViewModel;
    private User mUser;
    private Room mRoom;

    public static Intent newIntent(Context ctx, String chatName, boolean isPrivate, User user, Room room){
        Intent i = new Intent(ctx, OneToOneSettingsActivity.class);
        i.putExtra(CHAT_NAME, chatName);
        i.putExtra(IS_PRIVATE, isPrivate);
        i.putExtra(USER, user);
        i.putExtra(ROOM, room);
        return i;
    }

    @BindView(R.id.settings_chat_title_text_view) TextView mMainChatTitleTextView;
    @BindView(R.id.chat_info_chat_title_text_view) TextView mChatInfoTitleTextView;
    @BindView(R.id.is_private) SwitchButton mIsPrivateSwitchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_to_one_settings);
        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(ChatSettingsViewModel.class);

        Intent i = getIntent();
        if(i != null){
            mMainChatTitleTextView.setText(i.getStringExtra(CHAT_NAME));
            mChatInfoTitleTextView.setText(i.getStringExtra(CHAT_NAME));
            mIsPrivateSwitchButton.setChecked(i.getBooleanExtra(IS_PRIVATE, false));
            mUser = i.getParcelableExtra(USER);
            mRoom = i.getParcelableExtra(ROOM);
        }

        mIsPrivateSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                mViewModel.mSubscriptionData.observe(OneToOneSettingsActivity.this, subscriptionExample -> {
                    Log.d(TAG, "onCheckedChanged: "+ subscriptionExample.getSubscription().getIs_secret());
                });
                mViewModel.toggleChat(mUser.getAccessToken(), mRoom.getId());
            }
        });
    }

    @OnClick(R.id.change_custom_name)
    public void changeCustomName(View v){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.update_chat_custom_name);
        EditText customChatNameEditText = (EditText) dialog.findViewById(R.id.custom_chat_name_edit_text);
        customChatNameEditText.setText(getIntent().getStringExtra(CHAT_NAME));

        Button cancelButton = (Button) dialog.findViewById(R.id.change_custom_name_cancel_button);
        Button editButton = (Button) dialog.findViewById(R.id.change_custom_name_edit_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.mSubscriptionData.observe(OneToOneSettingsActivity.this, subscriptionExample -> {
                    Log.d(TAG, "onClick: "+ subscriptionExample.getSubscription().getCustom_room_name());
                });
                String newName = customChatNameEditText.getText().toString();
                mViewModel.updateRoomCustomName(mUser.getAccessToken(), mRoom.getId(), newName);
            }
        });
        dialog.show();
    }

    @OnClick(R.id.clear_chat_content_text_view)
    public void clearChatContent(View v){
        String title = "Clear your chat content";
        String message = "You are about to delete all content from this ChatDB from all users." +
                " You will still be connected to carry on chatting. Are you sure?";
        DialogUtils.confirmationDialouge(this, title, message, new DialogUtils.OnNetwrokBack() {
            @Override
            public void onConnectionBack() {
                mViewModel.deleteAll(mRoom.getId());
            }
        });

    }

    @OnClick(R.id.delete_and_disconnect_text_view)
    public void deleteAndDisconnect(View v){
        String title = "Delete chat and disconnect";
        String message = "You are about to delete all content from this ChatDB from all users and disconnect."+
                " You will not be able to reconnect to the same user unless you share a new Burner Code. Are you sure?";
        DialogUtils.confirmationDialouge(this, title, message, new DialogUtils.OnNetwrokBack() {
            @Override
            public void onConnectionBack() {
                mViewModel.deleteAll(mRoom.getId());
                mViewModel.deleteAddAndLeave(mRoom.getId());
            }
        });

    }

    @OnClick(R.id.block_and_report_text_view)
    public void blockAndReport(View v){
        String title = "Delete chat and disconnect";
        String message = "You are about to delete all content from this ChatDB from all users."+
                " As well as block and report the user to HaYa admin. Are you sure?";
        DialogUtils.confirmationDialouge(this, title, message, new DialogUtils.OnNetwrokBack() {
            @Override
            public void onConnectionBack() {
                mViewModel.deleteAll(mRoom.getId());
                mViewModel.deleteAddAndLeave(mRoom.getId());
            }
        });
    }
}
