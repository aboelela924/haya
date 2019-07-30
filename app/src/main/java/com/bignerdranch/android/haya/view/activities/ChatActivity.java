package com.bignerdranch.android.haya.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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
import com.bignerdranch.android.haya.model.repo.networking.GetSocket;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.ChatNetworkingRepo;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncBody;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncMessage;
import com.bignerdranch.android.haya.utils.networkUtils.ConnectionHelper;
import com.bignerdranch.android.haya.utils.networkUtils.NetworkUtils;
import com.bignerdranch.android.haya.view.adapters.MessagesAdapter;
import com.bignerdranch.android.haya.viewModel.ChatViewModel;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Date;
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
    private List<Message> mUnsendMessageList = new ArrayList<>();
    private BottomSheetBehavior mBottomSheetBehavior;
    private Map<String,Subscriber> subscribers = new HashMap<>();
    private BroadcastReceiver mReceiver;

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
    @BindView(R.id.chat_titile_text_view) TextView mChatTitleTextView;

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

        mChatTitleTextView.setText(mRoom.getName());

        for (Subscriber subscriber: mRoom.getSubscribers()){
            subscribers.put(subscriber.getId(), subscriber);
        }

        Log.d(TAG, "onCreate: "+ mUser.getAccessToken());
        Log.d(TAG, "onCreate: "+ mRoom.getId());

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                    //check internet connection
                    if (!ConnectionHelper.isConnectedOrConnecting(context)) {
                        if (context != null) {
                            boolean show = false;
                            if (ConnectionHelper.lastNoConnectionTs == -1) {//first time
                                show = true;
                                ConnectionHelper.lastNoConnectionTs = System.currentTimeMillis();
                            } else {
                                if (System.currentTimeMillis() - ConnectionHelper.lastNoConnectionTs > 1000) {
                                    show = true;
                                    ConnectionHelper.lastNoConnectionTs = System.currentTimeMillis();
                                }
                            }

                            if (show && ConnectionHelper.isOnline) {
                                ConnectionHelper.isOnline = false;
                            }
                        }
                    } else {
                        // Perform your actions here
                        ConnectionHelper.isOnline = true;
                    }
                }
            }
        };
        //register the broadcast receiver
        //registerReceiver(mReceiver, filter);

        GetSocket.getSocket().on(Socket.EVENT_RECONNECT, sendUnsendMessages);


        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        mViewModel.mData.observe(this, message -> {
            if(message.getUser().getUser_id().equals(mUser.getId())){
                message.setUser(null);
            }
            mMessageList.add(message);
            updateRecyclerView();
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
            updateRecyclerView();
        });
        mViewModel.mDeleteResponse.observe(this, deleteMessageResponse -> {
            Message message = new Message();
            message.setId(deleteMessageResponse.getMessage_id());
            message.setRoom_id(deleteMessageResponse.getRoom_id());
            int index = mMessageList.indexOf(message);
            mMessageList.remove(index);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateRecyclerView();
                }
            });

        });
        mViewModel.observeMessages();
        mViewModel.observeMessageDelete();
        List<SyncBody> bodies = new ArrayList<>();
        SyncBody body = new SyncBody(mRoom.getId(), 0);
        bodies.add(body);
        mViewModel.syncMessages(mUser.getAccessToken(),bodies);




        mAdapter = new MessagesAdapter(this,mMessageList, this);
        final LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        mMessagesRecyclerView.setLayoutManager(linearLayout);
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
        if(ConnectionHelper.isOnline){
            if(message.isEmpty()){
                return;
            }
            mViewModel.sendUserMessage(message,mRoom.getId());
        }else{
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();
            Message messageObj = new Message();
            messageObj.setRoom_id(mRoom.getId());
            messageObj.setMessage(message);
            messageObj.setCreated_at(ts);
            mUnsendMessageList.add(messageObj);
            mMessageList.add(messageObj);
            updateRecyclerView();

        }

    }

    private void updateRecyclerView() {
        mAdapter.notifyDataSetChanged();
        mMessagesRecyclerView.scrollToPosition(mMessageList.size() - 1);
    }

    @OnClick(R.id.message_cancel_button)
    public void onCancel(View v){
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.getState();
        mBottomSheetBehavior.setHideable(true);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @OnClick(R.id.settings_image_view)
    public void goToSettings(View v){
        boolean isPrivate = false;
        boolean isAdmin = false;
        for (Subscriber sb : mRoom.getSubscribers()){
            if (sb.getUser_id().equals(mUser.getId())){
                isPrivate = Boolean.valueOf(sb.getIs_secret());
                isAdmin = Boolean.valueOf(sb.getIs_admin());
            }
        }
        if(Integer.parseInt(mRoom.getType()) == 1){
           Intent i = OneToOneSettingsActivity.newIntent(this, mRoom.getName(), isPrivate,mUser, mRoom);
            startActivity(i);
        }else{
            if(isAdmin){
                Intent i = GroupChatSettingsAdminActivity.newIntent(this, isPrivate,mUser,mRoom);
                startActivity(i);
            }else{
                Intent i = GroupChatSettingsUserActivity.newIntent(this, isPrivate,mUser,mRoom);
                startActivity(i);
            }
        }
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



    Emitter.Listener sendUnsendMessages = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            for (Message message: mUnsendMessageList){
                mViewModel.sendUserMessage(message.getMessage(),message.getRoom_id());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int index = mMessageList.indexOf(message);
                        mMessageList.remove(index);
                        updateRecyclerView();
                    }
                });

            }
            mUnsendMessageList.clear();

        }
    };
}
