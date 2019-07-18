package com.bignerdranch.android.haya.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.UserExample;
import com.bignerdranch.android.haya.model.repo.networking.registration.PasswordBody;
import com.bignerdranch.android.haya.model.repo.networking.registration.RegistrationBody;
import com.bignerdranch.android.haya.utils.dialouges.DialogUtils;
import com.bignerdranch.android.haya.utils.networkUtils.NetworkUtils;
import com.bignerdranch.android.haya.viewModel.RegistrationViewModel;
import com.github.ybq.android.spinkit.SpinKitView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";

    @BindView(R.id.user_id_edit_text_1) EditText firstNumberEditText;
    @BindView(R.id.user_id_edit_text_2) EditText secondNumberEditText;
    @BindView(R.id.user_id_edit_text_3) EditText thirdNumberEditText;
    @BindView(R.id.user_id_edit_text_4) EditText forthNumberEditText;
    @BindView(R.id.user_id_edit_text_5) EditText fifthNumberEditText;
    @BindView(R.id.password_edit_text) EditText passwordEditText;
    @BindView(R.id.loading_indecator_registration_activity_for_user_id) SpinKitView userIdSpinner;
    @BindView(R.id.loading_indecator_registration_activity_for_sign_up_action) SpinKitView signupSPinner;
    @BindView(R.id.sign_up_button) Button signupButton;

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
            hideUserIdSpinner();
        });
        mViewModel.mMessageData.observe(this, messageResponseUpdatePassword -> {
            hideSignUpSpinner();
            Log.d(TAG, "onCreate: "+ messageResponseUpdatePassword.getMessage());
            Intent i = RegistrationSuccessActivity.newIntent(this,
                    mUserExample.getUser().getUserId(),
                    passwordEditText.getText().toString());
            startActivity(i);
        });

        if(!NetworkUtils.isNetworkConnected(this) && !NetworkUtils.isInternetAvailable()){
            DialogUtils.noInternetConnectionDialog(this, new DialogUtils.OnNetwrokBack() {
                @Override
                public void onConnectionBack() {
                    mViewModel.createUserId(new RegistrationBody(androidId,"android"));
                    showUserIdSpinner();
                }
            });
        }else{
            mViewModel.createUserId(new RegistrationBody(androidId,"android"));
            showUserIdSpinner();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.sign_up_button)
    public void signup(View view){
        String password = passwordEditText.getText().toString();
        if(password.isEmpty()){
            View toastView = getLayoutInflater().inflate(R.layout.error_toast, null);
            TextView textView = toastView.findViewById(R.id.toast_error_message_text_view);
            textView.setText("Password can't be empty.");
            Toast toast = new Toast(this);
            toast.setView(toastView);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,30);
            toast.show();
            return;
        }
        if(mUserExample != null){
            showSignUpSpinner();
            mViewModel.updateUserPassword(mUserExample.getUser().getAccessToken(),
                    new PasswordBody(password, password));
        }
    }

    @OnClick(R.id.go_to_sign_in_text_view_registration_activity)
    public void goToSignin(){
        Intent i = new Intent(this, SigninActivity.class);
        startActivity(i);
    }

    private void showSignUpSpinner(){
        signupSPinner.setVisibility(View.VISIBLE);
        signupButton.setEnabled(false);
    }

    private void hideSignUpSpinner(){
        signupSPinner.setVisibility(View.INVISIBLE);
        signupButton.setEnabled(true);
    }

    private void showUserIdSpinner(){
        userIdSpinner.setVisibility(View.VISIBLE);
    }

    private void hideUserIdSpinner(){
        userIdSpinner.setVisibility(View.INVISIBLE);
    }
}
