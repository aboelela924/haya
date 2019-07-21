package com.bignerdranch.android.haya.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bignerdranch.android.haya.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BurnerCodeFragment extends Fragment {

    @BindView(R.id.single_chat_bottom_cheat) LinearLayout singleBottomCheatContainer;
    @BindView(R.id.group_chat_bottom_cheat) LinearLayout groupBottomCheatContainer;

    private BottomSheetBehavior mSingleBottomSheetBehavior;
    private BottomSheetBehavior mGroupBottomSheetBehavior;
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
        return v;
    }

    @OnClick(R.id.single_chat_button_burner_code_fragment)
    public void generateSingleChatCode(View v){
        mSingleBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @OnClick(R.id.close_single_chat_bottom_cheat_button)
    public void closeSingleChat(View view){
        mSingleBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
    @OnClick(R.id.group_chat_button_burner_code_fragment)
    public void generateGroupChatCode(View v){
        mGroupBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    @OnClick(R.id.close_group_chat_bottom_cheat_button)
    public void closeGroupChatBottomCheat(View v){
        mGroupBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
}
