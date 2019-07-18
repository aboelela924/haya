package com.bignerdranch.android.haya.view.activities;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.UserExample;
import com.bignerdranch.android.haya.model.repo.networking.registration.RegistrationBody;
import com.bignerdranch.android.haya.viewModel.RegistrationViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.user_id_edit_text_1) EditText firstNumberEditText;
    @BindView(R.id.user_id_edit_text_2) EditText secondNumberEditText;
    @BindView(R.id.user_id_edit_text_3) EditText thirdNumberEditText;
    @BindView(R.id.user_id_edit_text_4) EditText forthNumberEditText;
    @BindView(R.id.user_id_edit_text_5) EditText fifthNumberEditText;

    private RegistrationViewModel mViewModel;
    private UserExample mUserExample;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        mViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        mViewModel.mData.observe(this, userExample -> {
            mUserExample = userExample;
            String userId = mUserExample.getUser().getUserId();
            firstNumberEditText.setText(String.valueOf(userId.charAt(0)));
            secondNumberEditText.setText(String.valueOf(userId.charAt(1)));
            thirdNumberEditText.setText(String.valueOf(userId.charAt(2)));
            forthNumberEditText.setText(String.valueOf(userId.charAt(3)));
            fifthNumberEditText.setText(String.valueOf(userId.charAt(4)));
        });

        mViewModel.createUserId(new RegistrationBody(androidId,"android"));
    }

    @OnClick(R.id.sign_up_button)
    public void signup(View view){
        if(mUserExample != null){

        }
    }
}
