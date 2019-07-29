package com.bignerdranch.android.haya.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationSuccessActivity extends AppCompatActivity {

    @BindView(R.id.registeration_success_id_char_one) EditText charOneEditText;
    @BindView(R.id.registeration_success_id_char_two) EditText charTwoEditText;
    @BindView(R.id.registeration_success_id_char_three) EditText charThreeEditText;
    @BindView(R.id.registeration_success_id_char_four) EditText charFourEditText;
    @BindView(R.id.registeration_success_id_char_five) EditText charFiveEditText;

    @BindView(R.id.password_edit_text_registeration_success) EditText passwordEditText;

    private static final String USER = "USER";
    private static final String PASSWORD = "PASSWORD";

    private User mUser;

    public static Intent newIntent(Context context, User user, String password){
        Intent i = new Intent(context, RegistrationSuccessActivity.class);
        i.putExtra(USER, user);
        i.putExtra(PASSWORD, password);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_success);
        ButterKnife.bind(this);
        Intent i = getIntent();
        if(i != null){
            mUser = i.getParcelableExtra(USER);
            String userId = mUser.getUserId();
            String password = i.getStringExtra(PASSWORD);
            charOneEditText.setText(String.valueOf(userId.charAt(0)));
            charTwoEditText.setText(String.valueOf(userId.charAt(1)));
            charThreeEditText.setText(String.valueOf(userId.charAt(2)));
            charFourEditText.setText(String.valueOf(userId.charAt(3)));
            charFiveEditText.setText(String.valueOf(userId.charAt(4)));
            passwordEditText.setText(String.valueOf(password));
        }
    }

    @OnClick(R.id.close_button_registeration_success)
    public void goToBurner(View view){
        Intent i = ContainerActivity.newIntent(this, mUser);
        startActivity(i);

    }
}
