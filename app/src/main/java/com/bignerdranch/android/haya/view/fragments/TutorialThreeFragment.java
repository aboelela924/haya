package com.bignerdranch.android.haya.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.ui.CircleCheckBox;
import com.bignerdranch.android.haya.utils.SharedPreferncesConstants;
import com.bignerdranch.android.haya.view.activities.RegistrationActivity;
import com.bignerdranch.android.haya.view.activities.TermsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TutorialThreeFragment extends Fragment {
    private Unbinder mUnbinder;
    @BindView(R.id.are_terms_accepted_check_box) CircleCheckBox areTermsAccepted;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tutorial_three_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        areTermsAccepted.setOnCheckedChangeListener(new CircleCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CircleCheckBox view, boolean isChecked) {
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                        .putBoolean(SharedPreferncesConstants.ACCEPT_TERMS, isChecked)
                        .apply();
            }
        });
        return v;
    }

    @OnClick(R.id.show_terms_text_view)
    public void showTerms(){
        Intent i = new Intent(getActivity(), TermsActivity.class);
        startActivity(i);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.go_to_registeration_button)
    public void goToRegisteration(View view){
        if(areTermsAccepted.isChecked()){
            Intent i = new Intent(getActivity(), RegistrationActivity.class);
            startActivity(i);
        }else{
            View toastView = getLayoutInflater().inflate(R.layout.error_toast, null);
            TextView textView = toastView.findViewById(R.id.toast_error_message_text_view);
            textView.setText("You must accept terms.");
            Toast toast = new Toast(getActivity());
            toast.setView(toastView);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM,0,30);
            toast.show();
        }
    }
}
