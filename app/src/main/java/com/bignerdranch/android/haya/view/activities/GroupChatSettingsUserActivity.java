package com.bignerdranch.android.haya.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.utils.dialouges.DialogUtils;
import com.bignerdranch.android.haya.viewModel.ChatSettingsViewModel;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupChatSettingsUserActivity extends AppCompatActivity {
    private static final String TAG = "GroupChatSettingsUserActivity";
    private static final String IS_PRIVATE = "IS_PRIVATE";
    private static final String USER = "USER";
    private static final String ROOM = "ROOM";

    @BindView(R.id.user_settings_title_chat_name_text_view) TextView mChatNameTitleTextView;
    @BindView(R.id.user_settings_chat_info_chat_name_text_view) TextView mChatNameChatInfoTextView;
    @BindView(R.id.user_settings_chat_info_nickname_text_view) TextView mNicknameChatInfoTextView;
    @BindView(R.id.user_settings_chat_info_is_private_switch_button) SwitchButton mIsPrivateSwitchButton;
    @BindView(R.id.user_settings_share_burner_code_linear_layout) LinearLayout mShareBurnerCodeLinearLayout;

    @BindView(R.id.user_settings_edit_text_1) EditText mEditText1;
    @BindView(R.id.user_settings_edit_text_2) EditText mEditText2;
    @BindView(R.id.user_settings_edit_text_3) EditText mEditText3;
    @BindView(R.id.user_settings_edit_text_4) EditText mEditText4;
    @BindView(R.id.user_settings_edit_text_5) EditText mEditText5;

    @BindView(R.id.user_settings_second_subscriber_linear_layout) LinearLayout mSecondSubscriberLinearLayout;
    @BindView(R.id.user_settings_third_subscriber_linear_layout) LinearLayout mThirdSubscriberLinearLayout;
    @BindView(R.id.user_settings_more_subscriber_linear_layout) LinearLayout mMoreSubscriberLinearLayout;

    @BindView(R.id.user_settings_first_subscriber_name_text_view) TextView mFirstSubscriberTextView;
    @BindView(R.id.user_settings_second_subscriber_name_text_view) TextView mSecondSubscriberTextView;
    @BindView(R.id.user_settings_third_subscriber_name_text_view) TextView mThirdSubscriberTextView;
    @BindView(R.id.user_settings_more_subscriber_count_text_view) TextView mMoreSubscriberTextView;

    @BindView(R.id.user_settings_second_subscriber_view) View mSecondSubscriberView;
    @BindView(R.id.user_settings_third_subscriber_view) View mThirdSubscriberView;

    public static Intent newIntent(Context ctx, boolean isPrivate, User user, Room room){
        Intent i = new Intent(ctx, GroupChatSettingsUserActivity.class);
        i.putExtra(USER, user);
        i.putExtra(ROOM, room);
        i.putExtra(IS_PRIVATE, isPrivate);
        return i;
    }

    private Room mRoom;
    private User mUser;
    private Subscriber mSubscriber;
    private ChatSettingsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_settings_user);
        ButterKnife.bind(this);

        mRoom = getIntent().getParcelableExtra(ROOM);
        mUser = getIntent().getParcelableExtra(USER);
        for (Subscriber sb : mRoom.getSubscribers()){
            if(sb.getUser_id().equals(mUser.getId())){
                mSubscriber = sb;
                break;
            }
        }

        boolean isPrivate = getIntent().getBooleanExtra(IS_PRIVATE, false);
        updateSubscribers();
        mViewModel = ViewModelProviders.of(this).get(ChatSettingsViewModel.class);

        mEditText1.setText(String.valueOf(mRoom.getToken().charAt(0)));
        mEditText2.setText(String.valueOf(mRoom.getToken().charAt(1)));
        mEditText3.setText(String.valueOf(mRoom.getToken().charAt(2)));
        mEditText4.setText(String.valueOf(mRoom.getToken().charAt(3)));
        mEditText5.setText(String.valueOf(mRoom.getToken().charAt(4)));

        mChatNameTitleTextView.setText(mRoom.getName());
        mChatNameChatInfoTextView.setText(mRoom.getName());
        mIsPrivateSwitchButton.setChecked(isPrivate);

        mViewModel.mSubscriptionData.observe(GroupChatSettingsUserActivity.this, subscriptionExample -> {
            mChatNameTitleTextView.setText(subscriptionExample.getSubscription().getCustom_room_name());
            mChatNameChatInfoTextView.setText(subscriptionExample.getSubscription().getCustom_room_name());
            mIsPrivateSwitchButton.setChecked(Boolean.valueOf(subscriptionExample.getSubscription().getIs_secret()));
        });

        mViewModel.mRoomData.observe(this, room -> {
            mRoom = room;
            updateSubscribers();
        });

        mViewModel.mSubscriberData.observe(this,subscriber -> {
            mSubscriber = subscriber;
            for (Subscriber sb: mRoom.getSubscribers()){
                if(sb.getId().equals(subscriber.getId())){
                    sb = subscriber;
                    break;
                }
            }
        });

        mViewModel.observeUserJoin();
        mViewModel.observeUserLeave();


        mIsPrivateSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                mViewModel.toggleChat(mUser.getAccessToken(), mRoom.getId());
            }
        });

    }

    public void updateSubscribers(){

        mFirstSubscriberTextView.setText(mRoom.getSubscribers()[0].getName());

        if (mRoom.getSubscribers().length >= 2){
            mSecondSubscriberLinearLayout.setVisibility(View.VISIBLE);
            mSecondSubscriberView.setVisibility(View.VISIBLE);
            mSecondSubscriberTextView.setText(mRoom.getSubscribers()[1].getName());
        }else{
            mSecondSubscriberLinearLayout.setVisibility(View.INVISIBLE);
            mSecondSubscriberView.setVisibility(View.INVISIBLE);
        }

        if (mRoom.getSubscribers().length >= 3){
            mThirdSubscriberLinearLayout.setVisibility(View.VISIBLE);
            mThirdSubscriberView.setVisibility(View.VISIBLE);
            mThirdSubscriberTextView.setText(mRoom.getSubscribers()[2].getName());
        }else{
            mThirdSubscriberLinearLayout.setVisibility(View.INVISIBLE);
            mThirdSubscriberView.setVisibility(View.INVISIBLE);
        }

        if (mRoom.getSubscribers().length >= 4){
            mMoreSubscriberLinearLayout.setVisibility(View.VISIBLE);
            mMoreSubscriberTextView.setText("More "+String.valueOf(mRoom.getSubscribers().length - 3));
        }else{
            mMoreSubscriberLinearLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.user_settings_share_burner_code_linear_layout)
    public void shareBurnerCode(View v){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("plain/text");
        i.putExtra(Intent.EXTRA_TEXT, mRoom.getToken());
        startActivity(i);
    }

    @OnClick(R.id.user_settings_clear_your_content_text_view)
    public void clearYourContent(View v){
        mViewModel.deleteAll(mRoom.getId());
    }

    @OnClick(R.id.user_settings_clear_all_content_text_view)
    public void clearAllContent(View v){
    }

    @OnClick(R.id.user_settings_leave_group_text_view)
    public void leaveGroup(View v){
        mViewModel.deleteAddAndLeave(mRoom.getId());
    }

    @OnClick(R.id.user_settings_block_report_text_view)
    public void blockAndReport(View v){
        mViewModel.deleteAddAndLeave(mRoom.getId());
    }

    @OnClick(R.id.user_settings_chat_info_nickname_linear_layout)
    public void updateName(){
        DialogUtils.changeAttributeDialog(this, "Change your nickname", mSubscriber.getName(), new DialogUtils.AttrChange() {
            @Override
            public void onChange(String attr) {
                mViewModel.updateNickName(mRoom.getId(), attr);
            }
        });
    }

    @OnClick(R.id.user_settings_chat_info_chat_name_linear_layout)
    public void changeCustomName(View v){
        DialogUtils.changeAttributeDialog(this,
                "Change chat name",
                mSubscriber.getCustom_room_name(),
                new DialogUtils.AttrChange() {
                    @Override
                    public void onChange(String attr) {
                        mViewModel.updateRoomCustomName(mUser.getAccessToken(), mRoom.getId(), attr);

                    }
                });
    }
}
