package com.bignerdranch.android.haya.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.utils.dialouges.DialogUtils;
import com.bignerdranch.android.haya.viewModel.MainSettingsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsActivity extends AppCompatActivity {

    private MainSettingsViewModel mViewModel;

    @BindView(R.id.name_edit_text) EditText mNameEditText;
    @BindView(R.id.email_edti_Text) EditText mEmailEditText;
    @BindView(R.id.phone_number_edit_text) EditText mPhoneNumberEditText;
    @BindView(R.id.message_edit_text) EditText mMessageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(MainSettingsViewModel.class);
        mViewModel.mContactData.observe(this,contactUsResponse -> {
            Toast.makeText(this,"We recieved you feedback. Thanks.", Toast.LENGTH_LONG).show();
        });
    }

    @OnClick(R.id.contact_us_button)
    public void contactUs(View v){

        String name = mNameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String phoneNumber = mPhoneNumberEditText.getText().toString();
        String message = mMessageEditText.getText().toString();

        if(name.isEmpty() || message.isEmpty()){
            Toast.makeText(this,"Name and message fields can't be empty", Toast.LENGTH_LONG).show();
            return;
        }

        mViewModel.contactUs(name,email,phoneNumber,message);
    }
}
