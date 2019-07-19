package com.bignerdranch.android.haya.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.networking.signinNetworking.SigninBody;
import com.bignerdranch.android.haya.utils.dialouges.DialogUtils;
import com.bignerdranch.android.haya.utils.networkUtils.NetworkUtils;
import com.bignerdranch.android.haya.viewModel.SigninViewModel;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SigninActivity extends AppCompatActivity implements TextWatcher {
    private static final String TAG = "SigninActivity";

    @BindView(R.id.sign_in_edit_text_1) EditText userIdEditTextCharOne;
    @BindView(R.id.sign_in_edit_text_2) EditText userIdEditTextCharTwo;
    @BindView(R.id.sign_in_edit_text_3) EditText userIdEditTextCharThree;
    @BindView(R.id.sign_in_edit_text_4) EditText userIdEditTextCharFour;
    @BindView(R.id.sign_in_edit_text_5) EditText userIdEditTextCharFive;
    @BindView(R.id.password_edit_text_sign_in) EditText passwordEditText;

    private SigninViewModel mViewModel;
    private List<EditText> userId =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);
        ButterKnife.bind(this);

        if(!NetworkUtils.isNetworkConnected(this) && !NetworkUtils.isInternetAvailable()){
            DialogUtils.noInternetConnectionDialog(this,null);
        }

        userId.add(userIdEditTextCharOne);
        userId.add(userIdEditTextCharTwo);
        userId.add(userIdEditTextCharThree);
        userId.add(userIdEditTextCharFour);
        userId.add(userIdEditTextCharFive);

        userIdEditTextCharOne.addTextChangedListener(this);
        userIdEditTextCharTwo.addTextChangedListener(this);
        userIdEditTextCharThree.addTextChangedListener(this);
        userIdEditTextCharFour.addTextChangedListener(this);
        userIdEditTextCharFive.addTextChangedListener(this);

        mViewModel = ViewModelProviders.of(this).get(SigninViewModel.class);
        mViewModel.mData.observe(this, userExample -> {
            Log.d(TAG, "onCreate: sing in successfully");
            Log.d(TAG, "onCreate: "+ userExample.getUser().getId());
        });

    }

    @OnClick(R.id.sign_in_button_sign_in_activity)
    public void signin(){
        String id = "";
        for (EditText editText: userId){
            id = id+editText.getText().toString().toUpperCase();
        }
        String password = passwordEditText.getText().toString();
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if(id.length() == 5 && !password.isEmpty() ){
            mViewModel.singUser(new SigninBody(id, password, androidId));
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(userIdEditTextCharOne.getText().hashCode() == charSequence.hashCode()){
            userIdEditTextCharTwo.requestFocus();
        }else if (userIdEditTextCharTwo.getText().hashCode() == charSequence.hashCode()){
            userIdEditTextCharThree.requestFocus();
        }else if (userIdEditTextCharThree.getText().hashCode() == charSequence.hashCode()){
            userIdEditTextCharFour.requestFocus();
        }else if (userIdEditTextCharFour.getText().hashCode() == charSequence.hashCode()){
            userIdEditTextCharFive.requestFocus();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @OnClick(R.id.back_image_view_sign_in)
    public void backToRegisteration(View view){
        toRegisterationActivity();
    }

    @OnClick(R.id.go_to_sign_up_text_view_sign_in)
    public void toRegisteration(View view){
        toRegisterationActivity();
    }

    private void toRegisterationActivity() {
        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
        finish();
    }
}
