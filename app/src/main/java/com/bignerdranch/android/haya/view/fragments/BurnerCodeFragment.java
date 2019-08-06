package com.bignerdranch.android.haya.view.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.haya.model.repo.CurrentUser;
import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.view.activities.JoinConversationActivity;
import com.bignerdranch.android.haya.viewModel.CreateRoomViewModel;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BurnerCodeFragment extends Fragment {
    
    private static final String TAG = "BurnerCodeFragment";
    private static final String USER = "USER";

    private boolean isGroup;
    private User mUser;
    private Room mRoom;
    private CreateRoomViewModel mViewModel;


    @BindView(R.id.single_chat_bottom_cheat) LinearLayout singleBottomCheatContainer;
    @BindView(R.id.group_chat_bottom_cheat) LinearLayout groupBottomCheatContainer;
    @BindView(R.id.show_code_bottom_sheet) LinearLayout showCodeBottomContainer;
    @BindView(R.id.loading_indecator_burnder_code_fragment) SpinKitView loadingIndicator;

    //single chat views
    @BindView(R.id.single_chat_bottom_cheat_edit_text_1) EditText charOneEditText;
    @BindView(R.id.single_chat_bottom_cheat_edit_text_2) EditText charTwoEditText;
    @BindView(R.id.single_chat_bottom_cheat_edit_text_3) EditText charThreeEditText;
    @BindView(R.id.single_chat_bottom_cheat_edit_text_4) EditText charFourEditText;
    @BindView(R.id.single_chat_bottom_cheat_edit_text_5) EditText charFiveEditText;
    @BindView(R.id.single_chat_bottom_cheat_chat_name_edit_text) EditText chatNameEditText;
    @BindView(R.id.chat_name_edit_single_bottom_sheet_image_view) ImageView editChatNameSingleImageView;

    //group chat views
    @BindView(R.id.group_chat_bottom_sheet_edit_text_1) EditText charOneEditTextGroup;
    @BindView(R.id.group_chat_bottom_sheet_edit_text_2) EditText charTwoEditTextGroup;
    @BindView(R.id.group_chat_bottom_sheet_edit_text_3) EditText charThreeEditTextGroup;
    @BindView(R.id.group_chat_bottom_sheet_edit_text_4) EditText charFourEditTextGroup;
    @BindView(R.id.group_chat_bottom_sheet_edit_text_5) EditText charFiveEditTextGroup;
    @BindView(R.id.group_bottom_sheet_conversation_name_edit_text) EditText chatNameEditTextGroup;
    @BindView(R.id.group_bottom_sheet_nickname_edit_text) EditText nickNameEditTextGroup;
    @BindView(R.id.chat_name_edit_group_bottom_sheet_image_view) ImageView editChatNameGroupImageView;
    @BindView(R.id.nickname_edit_group_bottom_sheet_image_view) ImageView nicknameGroupImageView;

    //show code views
    @BindView(R.id.show_code_bottom_sheet_edit_text_1) EditText charOneEditTextShow;
    @BindView(R.id.show_code_bottom_sheet_edit_text_2) EditText charTwoEditTextShow;
    @BindView(R.id.show_code_bottom_sheet_edit_text_3) EditText charThreeEditTextShow;
    @BindView(R.id.show_code_bottom_sheet_edit_text_4) EditText charFourEditTextShow;
    @BindView(R.id.show_code_bottom_sheet_edit_text_5) EditText charFiveEditTextShow;


    private BottomSheetBehavior mSingleBottomSheetBehavior;
    private BottomSheetBehavior mGroupBottomSheetBehavior;
    private BottomSheetBehavior mShowBottomSheetBehavior;

    public static BurnerCodeFragment newInstance(User user){
        BurnerCodeFragment fragment = new BurnerCodeFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mUser = getArguments().getParcelable(USER);
        mUser = CurrentUser.user;
        mViewModel = ViewModelProviders.of(this).get(CreateRoomViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.burner_code_fragment,container, false);
        ButterKnife.bind(this, v);
        mSingleBottomSheetBehavior = BottomSheetBehavior.from(singleBottomCheatContainer);
        mSingleBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mSingleBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    mSingleBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        mGroupBottomSheetBehavior = BottomSheetBehavior.from(groupBottomCheatContainer);
        mGroupBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mGroupBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    mGroupBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });


        mShowBottomSheetBehavior = BottomSheetBehavior.from(showCodeBottomContainer);
        mShowBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mShowBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    mShowBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        mViewModel.mRoomData.observe(this, roomExample -> {
            mRoom = roomExample.getRoom();
            hideLoading();
            if(isGroup){
                mGroupBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                populateGroupBottomSheet(roomExample.getRoom().getToken(), roomExample.getRoom().getName(),
                        roomExample.getRoom().getSubscribers()[0].getName());
            }else{
                mSingleBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                populateSingleBottomSheet(roomExample.getRoom().getToken(), roomExample.getRoom().getName());
            }
            chatNameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editChatNameSingleImageView.setClickable(true);
                    editChatNameSingleImageView.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.save_icon));
                }

                @Override
                public void afterTextChanged(Editable editable) {
//
                }
            });
            chatNameEditTextGroup.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editChatNameGroupImageView.setClickable(true);
                    editChatNameGroupImageView.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.save_icon));
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            nickNameEditTextGroup.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    nicknameGroupImageView.setClickable(true);
                    nicknameGroupImageView.setImageDrawable(
                            getActivity().getResources().getDrawable(R.drawable.save_icon));
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        });

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        mSingleBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mGroupBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @OnClick(R.id.single_chat_button_burner_code_fragment)
    public void generateSingleChatCode(View v){
        showLoading();
        isGroup = false;
        mViewModel.createSingleRoom(mUser.getAccessToken(),"new Room");
    }

    @OnClick(R.id.close_single_chat_bottom_cheat_button)
    public void closeSingleChat(View view){
        mSingleBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }
    @OnClick(R.id.group_chat_button_burner_code_fragment)
    public void generateGroupChatCode(View v){
        showLoading();
        isGroup = true;
        mViewModel.createGroupRoom(mUser.getAccessToken(),"new Room");
    }
    @OnClick(R.id.close_group_chat_bottom_cheat_button)
    public void closeGroupChatBottomCheat(View v){
        mGroupBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
    
    @OnClick(R.id.chat_name_edit_group_bottom_sheet_image_view)
    public void saveGroupChatNameChange(View v){
        isGroup = true;
        mGroupBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        showLoading();
        mViewModel.updateInfo(mUser.getAccessToken(),
                mRoom.getId(),
                chatNameEditTextGroup.getText().toString(),
                nickNameEditTextGroup.getText().toString());
    }
    
    @OnClick(R.id.nickname_edit_group_bottom_sheet_image_view)
    public void saveUserNicknameChange(View v){
        isGroup = true;
        mGroupBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        showLoading();
        mViewModel.updateInfo(mUser.getAccessToken(),
                mRoom.getId(),
                chatNameEditTextGroup.getText().toString(),
                nickNameEditTextGroup.getText().toString());
    }
    
    @OnClick(R.id.chat_name_edit_single_bottom_sheet_image_view)
    public void saveSingleChatNameChange(View v){
        isGroup = false;
        mSingleBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        showLoading();
        mViewModel.updateInfo(mUser.getAccessToken(),
                mRoom.getId(),
                chatNameEditText.getText().toString(),
                "");
    }

    @OnClick(R.id.group_share_card_view)
    public void groupShare(View v){
        shareText(mRoom.getToken());
    }

    @OnClick(R.id.group_show_card_view)
    public void groupShow(View v){
        showCode(mRoom.getToken());
    }

    @OnClick(R.id.group_copy_card_view)
    public void groupCopy(View v){
        copyToClipboard(mRoom.getToken());
    }

    @OnClick(R.id.single_share_card_view)
    public void singleShare(View v){
        shareText(mRoom.getToken());
    }

    @OnClick(R.id.single_show_card_view)
    public void singleShow(View v){
        showCode(mRoom.getToken());
    }

    @OnClick(R.id.single_copy_card_view)
    public void singleCopy(View v){
        copyToClipboard(mRoom.getToken());
    }

    @OnClick(R.id.close_show_code_bottom_cheat_button)
    public void closeShow(View v){
        mShowBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @OnClick(R.id.join_conversation)
    public void joinConversation(View v){
        Intent i = JoinConversationActivity.newIntent(getActivity(),mUser);
        startActivity(i);
    }

    public void shareText(String text){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("plain/text");
        i.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(i);
    }

    public void copyToClipboard(String text){
        ClipboardManager manager = (ClipboardManager)
                getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("Copied Text", text);
        manager.setPrimaryClip(data);
        View v = getActivity().getLayoutInflater().inflate(R.layout.success_toast,null);
        TextView editText = v.findViewById(R.id.success_message_text_view);
        editText.setText("Burner code copied");
        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(v);
        toast.show();
    }

    public void showCode(String text){
        mShowBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        charOneEditTextShow.setText(String.valueOf(text.charAt(0)));
        charTwoEditTextShow.setText(String.valueOf(text.charAt(1)));
        charThreeEditTextShow.setText(String.valueOf(text.charAt(2)));
        charFourEditTextShow.setText(String.valueOf(text.charAt(3)));
        charFiveEditTextShow.setText(String.valueOf(text.charAt(4)));
    }

    private void populateSingleBottomSheet(String code, String name){
        charOneEditText.setText(String.valueOf(code.charAt(0)));
        charTwoEditText.setText(String.valueOf(code.charAt(1)));
        charThreeEditText.setText(String.valueOf(code.charAt(2)));
        charFourEditText.setText(String.valueOf(code.charAt(3)));
        charFiveEditText.setText(String.valueOf(code.charAt(4)));
        chatNameEditText.setText(String.valueOf(name));
    }

    private void populateGroupBottomSheet(String code, String name, String nickName){
        charOneEditTextGroup.setText(String.valueOf(code.charAt(0)));
        charTwoEditTextGroup.setText(String.valueOf(code.charAt(1)));
        charThreeEditTextGroup.setText(String.valueOf(code.charAt(2)));
        charFourEditTextGroup.setText(String.valueOf(code.charAt(3)));
        charFiveEditTextGroup.setText(String.valueOf(code.charAt(4)));
        chatNameEditTextGroup.setText(String.valueOf(name));
        nickNameEditTextGroup.setText(String.valueOf(nickName));
    }

    private void showLoading(){
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        loadingIndicator.setVisibility(View.INVISIBLE);
    }
}

