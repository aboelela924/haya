package com.bignerdranch.android.haya.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.Message;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.ChatNetworkingRepo;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncBody;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncMessage;
import com.bignerdranch.android.haya.view.adapters.MessagesAdapter;
import com.bignerdranch.android.haya.viewModel.ChatViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChatActivity extends AppCompatActivity implements MessageClickCallbacks {
    private static final String USER = "USER";
    private static final String ROOM = "Room";
    private static final String TAG = "ChatActivity";

    private User mUser;
    private Room mRoom;
    private ChatViewModel mViewModel;
    private MessagesAdapter mAdapter;
    private List<Message> mMessageList = new ArrayList<>();
    private BottomSheetBehavior mBottomSheetBehavior;
    private Map<String,Subscriber> subscribers = new HashMap<>();

    public static Intent newIntent(Context ctx, User user,Room room){
        Intent i = new Intent(ctx, ChatActivity.class);
        i.putExtra(USER, user);
        i.putExtra(ROOM, room);
        return i;
    }

    @Override
    public void onMessageLongClick(Message message) {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        Button deleteButton = findViewById(R.id.message_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.deleteMessage(mRoom.getId(), message.getId());
            }
        });

        Button copyButton = findViewById(R.id.message_copy_button);
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipboard(message.getMessage());
            }
        });
    }



    @BindView(R.id.message_edit_text) EditText mMessageEditText;
    @BindView(R.id.messages_recycler_view) RecyclerView mMessagesRecyclerView;
    @BindView(R.id.message_options_bottom_sheet) LinearLayout mMessageBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        Intent i = getIntent();
        if(i != null){
            mUser = i.getParcelableExtra(USER);
            mRoom = i.getParcelableExtra(ROOM);
        }

        for (Subscriber subscriber: mRoom.getSubscribers()){
            subscribers.put(subscriber.getId(), subscriber);
        }

        Log.d(TAG, "onCreate: "+ mUser.getAccessToken());
        Log.d(TAG, "onCreate: "+ mRoom.getId());

        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        mViewModel.mData.observe(this, message -> {
            if(message.getUser().getUser_id().equals(mUser.getId())){
                message.setUser(null);
            }
            mMessageList.add(message);
            mAdapter.notifyDataSetChanged();
        });
        mViewModel.mMessages.observe(this, messages -> {
            for (SyncMessage message: messages.getMessages()){
                if(!Boolean.valueOf(message.getIsDeleted())){
                    Message sentMessage = new Message();
                    sentMessage.setUser(subscribers.get(message.getUser()));
                    sentMessage.setId(message.getId());
                    sentMessage.setCreated_at(message.getCreated_at());
                    sentMessage.setIsDeleted(message.getIsDeleted());
                    sentMessage.setMessage(message.getMessage());
                    sentMessage.setRoom_id(message.getRoom_id());
                    sentMessage.setType(message.getType());
                    sentMessage.setUpdated_at(message.getUpdated_at());
                    sentMessage.set__v(message.get__v());
                    sentMessage.set_id(message.get_id());
                    mMessageList.add(sentMessage);
                }
            }
            mAdapter.notifyDataSetChanged();
        });
        mViewModel.observeMessages();
        List<SyncBody> bodies = new ArrayList<>();
        SyncBody body = new SyncBody(mRoom.getId(), 0);
        bodies.add(body);
        mViewModel.syncMessages(mUser.getAccessToken(),bodies);




        mAdapter = new MessagesAdapter(this,mMessageList, this);
        mMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessagesRecyclerView.setAdapter(mAdapter);

        mBottomSheetBehavior = BottomSheetBehavior.from(mMessageBottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

    }

    @OnClick(R.id.send_image_view)
    public void sendMessage(View v){
        String message = mMessageEditText.getText().toString();
        if(message.isEmpty()){
            return;
        }
        mViewModel.sendUserMessage(message,mRoom.getId());
    }

    @OnClick(R.id.message_cancel_button)
    public void onCancel(View v){
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.getState();
        mBottomSheetBehavior.setHideable(true);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }


    public void copyToClipboard(String text){
        ClipboardManager manager = (ClipboardManager)
                this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("Copied Text", text);
        manager.setPrimaryClip(data);
        View v = getLayoutInflater().inflate(R.layout.success_toast,null);
        TextView editText = v.findViewById(R.id.success_message_text_view);
        editText.setText("Burner code copied");
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(v);
        toast.show();
    }
}
