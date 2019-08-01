package com.bignerdranch.android.haya.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.view.activities.BurnerCodeHistoryActivity;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsFragment extends Fragment {
    private static final String USER = "USER";

    public static SettingsFragment newInstance(User user){
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.user_key_text_view) TextView mUserKeyTextView;

    private User mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_settings, container, false);
        ButterKnife.bind(this,v);

        mUser = getArguments().getParcelable(USER);

        mUserKeyTextView.setText("Your User key: "+mUser.getUserId());

        return v;
    }

    @OnClick(R.id.go_to_burner_code_history_linear_layout)
    public void goToBurnerCodeHistory(View v){
        Intent i = BurnerCodeHistoryActivity.newIntent(getActivity(), mUser);
        startActivity(i);
    }
}
