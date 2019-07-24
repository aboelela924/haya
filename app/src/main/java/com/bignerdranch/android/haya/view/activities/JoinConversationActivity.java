package com.bignerdranch.android.haya.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.networking.joinRoomNetworking.JoinRoomRepo;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.Chat;
import com.bignerdranch.android.haya.viewModel.JoinRoomViewModel;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinConversationActivity extends AppCompatActivity implements TextWatcher {

    private static final String USER = "USER";

    private JoinRoomViewModel mViewModel;
    private User mUser;
    private Room mRoom;

    @BindView(R.id.chat_id_edit_text_1_activity_join) EditText charOne;
    @BindView(R.id.chat_id_edit_text_2_activity_join) EditText charTwo;
    @BindView(R.id.chat_id_edit_text_3_activity_join) EditText charThree;
    @BindView(R.id.chat_id_edit_text_4_activity_join) EditText charFour;
    @BindView(R.id.chat_id_edit_text_5_activity_join) EditText charFive;
    @BindView(R.id.nickname_edit_text) EditText nicknameEditText;
    @BindView(R.id.update_nickname) ImageView updateImageView;

    private String[] mNames ={"Jesusa Linares", "Neomi Ensor", "Kenda Grave", "Mandy Summy", "Cordie Nazario", "Isidra Brush",
            "Maryam Preslar", "Paulita Wilner", "Nedra Ellery", "Marceline Morneau", "Ola Lombardi", "Christie Cocco",
            "Cathern Montez", "Gennie Kelsch", "Fernando Peets", "Petronila Rudasill", "Wally Mckay",
            "Delsie Ostroff", "Deanne Parten", "Macie Waldon", "Marie Reels", "Melody Rozell",
            "Hong Valiente", "Syble Mackey", "Jacalyn Rowley", "Lola Montijo", "Towanda Sanon",
            "Loan Neville", "Delbert Guidotti", "Saundra Palomo", "Berta Julia", "Tammera Smolka",
            "Hugo Almodovar", "Loree Sauage", "Alesha Caudillo", "Maynard Akin", "Yoko Olmos", "" +
            "Berna Lapierre", "Valrie Michalek", "Shonta Rowton", "Suanne Jeanlouis", "Hye Rhoads",
            "Cristobal Santillanes", " Marti Edmundson", "Julie Almanzar", "Tiny Gula",
            "Eleanor Schaff", "Sacha Bowe", "Alden Bixler", "Gwyn Kolesar"};

    public static Intent newIntent(Context ctx, User user){
        Intent i = new Intent(ctx, JoinConversationActivity.class);
        i.putExtra(USER, user);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_conversation);
        ButterKnife.bind(this);

        Intent i = getIntent();
        if(i != null){
            mUser = i.getParcelableExtra(USER);
        }

        int max = 50;
        int random = new Random().nextInt(max);
        nicknameEditText.setText(mNames[random]);

        nicknameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateImageView.setImageDrawable(getResources().getDrawable(R.drawable.success_alert_icon));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        charOne.addTextChangedListener(this);
        charTwo.addTextChangedListener(this);
        charThree.addTextChangedListener(this);
        charFour.addTextChangedListener(this);
        charFive.addTextChangedListener(this);

        mViewModel = ViewModelProviders.of(this).get(JoinRoomViewModel.class);
        mViewModel.mRoomData.observe(this,room -> {
            mRoom = room;

            View v = getLayoutInflater().inflate(R.layout.success_toast,null);
            TextView editText = v.findViewById(R.id.success_message_text_view);
            editText.setText("Joined successfully");
            Toast toast = new Toast(this);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(v);
            toast.show();
            Intent intent = ChatActivity.newIntent(this,mUser,mRoom);
            startActivity(intent);
        });
        mViewModel.mSubscriberData.observe(this, subscriber -> {
            nicknameEditText.setText(subscriber.getName());
        });


    }

    @OnClick(R.id.join_chat_button)
    public void joinChat(View v){
        String roomToken = charOne.getText().toString() + charTwo.getText().toString()
                            + charThree.getText().toString() + charFour.getText().toString()
                            + charFive.getText().toString();
        if(roomToken.length() != 5){
            return;
        }
        mViewModel.joinRoom(nicknameEditText.getText().toString(),roomToken);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charOne.getText().hashCode() == charSequence.hashCode()){
            charTwo.requestFocus();
        }else if (charTwo.getText().hashCode() == charSequence.hashCode()){
            charThree.requestFocus();
        }else if (charThree.getText().hashCode() == charSequence.hashCode()){
            charFour.requestFocus();
        }else if (charFour.getText().hashCode() == charSequence.hashCode()){
            charFive.requestFocus();
        }
    }

    @OnClick(R.id.update_nickname)
    public void updateNickname(View v){
        if(mRoom != null){
            mViewModel.updateNickname(nicknameEditText.getText().toString(), mRoom.getId());
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
