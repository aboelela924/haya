package com.bignerdranch.android.haya.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.ui.CircleCheckBox;
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

        }
    }
}
