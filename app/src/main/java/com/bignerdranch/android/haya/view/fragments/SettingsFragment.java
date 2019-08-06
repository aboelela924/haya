package com.bignerdranch.android.haya.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking.ToggleSecretResponse;
import com.bignerdranch.android.haya.utils.SharedPreferncesConstants;
import com.bignerdranch.android.haya.utils.dialouges.DialogUtils;
import com.bignerdranch.android.haya.utils.dialouges.ToastUtils;
import com.bignerdranch.android.haya.view.activities.BurnerCodeHistoryActivity;
import com.bignerdranch.android.haya.view.activities.ContactUsActivity;
import com.bignerdranch.android.haya.view.activities.FAQsActivity;
import com.bignerdranch.android.haya.viewModel.MainSettingsViewModel;
import com.suke.widget.SwitchButton;

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
    @BindView(R.id.is_secret_chat_enabled_switch_button) SwitchButton mIsSecretChatEnabledSwitchButton;
    @BindView(R.id.is_muted_switch_button) SwitchButton mIsMutedSwitchButton;

    private User mUser;
    private MainSettingsViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_settings, container, false);
        ButterKnife.bind(this,v);
        mIsMutedSwitchButton.setChecked(PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getBoolean(SharedPreferncesConstants.IS_MUTED,false));
        SwitchButton.OnCheckedChangeListener changeListener = new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SwitchButton.OnCheckedChangeListener self = this;
                DialogUtils.changeAttributeDialog(getActivity(), "Enable Secret Chats",
                        "Password",
                        new DialogUtils.AttrChange() {
                            @Override
                            public void onChange(String attr) {
                                mViewModel.toggleSecret(mUser.getAccessToken(), attr);
                            }
                        }, new DialogUtils.OnNetwrokBack() {
                            @Override
                            public void onConnectionBack() {
                               changeIsSecret(self, !isChecked);
                            }
                        });
            }
        };

        mIsMutedSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sp.edit()
                        .putBoolean(SharedPreferncesConstants.IS_MUTED, isChecked)
                        .apply();
            }
        });

        mUser = getArguments().getParcelable(USER);

        mUserKeyTextView.setText("Your User key: "+mUser.getUserId());


        mViewModel.mLiveEvent.observe(this, toggleSecretResponse -> {
            //changeIsSecret(changeListener, Boolean.valueOf(toggleSecretResponse.getUser().getOptions().getEnable_secret_messages()));
        });
        mIsSecretChatEnabledSwitchButton.setOnCheckedChangeListener(changeListener);

        mViewModel.mFAQsData.observe(this,faqsMaster -> {
            Intent i = FAQsActivity.newIntent(getActivity(),faqsMaster);
            startActivity(i);
        });

        mViewModel.mError.observe(this, s -> {
            if(s.equals("Wrong Password.") || s.equals("Network error couldn't Enable Secret Chat.")){
                changeIsSecret(changeListener, !mIsSecretChatEnabledSwitchButton.isChecked());
            }
            ToastUtils.showErrorToast(getActivity(), s);
        });

        return v;
    }

    private void changeIsSecret(SwitchButton.OnCheckedChangeListener changeListener, boolean isChecked) {

        mIsSecretChatEnabledSwitchButton.setOnCheckedChangeListener(null);
        mIsSecretChatEnabledSwitchButton.toggle(false);
        mIsSecretChatEnabledSwitchButton.setOnCheckedChangeListener(changeListener);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainSettingsViewModel.class);

    }

    @OnClick(R.id.go_to_burner_code_history_linear_layout)
    public void goToBurnerCodeHistory(View v){
        Intent i = BurnerCodeHistoryActivity.newIntent(getActivity(), mUser);
        startActivity(i);
    }

    @OnClick(R.id.go_to_faqs_linear_layout)
    public void goToFAQs(View v){
        mViewModel.getFAQs();
    }

    @OnClick(R.id.contact_us_linear_layout)
    public void onClick(View v){
        Intent i = new Intent(getActivity(), ContactUsActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.log_out_button)
    public void logOut(View v){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.edit()
                .remove(SharedPreferncesConstants.USER_ID)
                .remove(SharedPreferncesConstants.ID)
                .remove(SharedPreferncesConstants.ACCESS_TOKEN)
                .remove(SharedPreferncesConstants.ACCEPT_TERMS)
                .apply();
        getActivity().finish();
    }

}
