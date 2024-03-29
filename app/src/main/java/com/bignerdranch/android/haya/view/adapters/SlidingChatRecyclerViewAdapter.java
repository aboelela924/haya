package com.bignerdranch.android.haya.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.haya.App;
import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.Message;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.networking.SubscribedRoomsNetworking.RoomOptionsCallBacks;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.MessageDB;
import com.bignerdranch.android.haya.utils.TimeFormat;
import com.bignerdranch.android.haya.view.activities.ChatActivity;
import com.bignerdranch.android.haya.viewModel.ChatRoomsViewModel;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SlidingChatRecyclerViewAdapter extends RecyclerView.Adapter<SlidingChatRecyclerViewAdapter.ViewHolder> {

    private List<Room> chats;
    private Context mContext;
    private User mUser;
    private ChatRoomsViewModel viewModel;
    private RoomOptionsCallBacks roomOptionsCallBacks;

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public SlidingChatRecyclerViewAdapter(Context ctx, User user, RoomOptionsCallBacks callBacks) {
        mContext = ctx;
        chats = new ArrayList<>();
        this.mUser = user;
        viewBinderHelper.setOpenOnlyOne(true);
        roomOptionsCallBacks = callBacks;
    }

    public void setRoomChats(List<Room>chats){
        this.chats.clear();
        this.chats.addAll(chats);
    }

    //Create new views.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_chat_outview_sliding, parent, false);

        return new ViewHolder(v);
    }

    //Bind data to views according to item index.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeFormat timeFormat = new TimeFormat();
        String lastMessage = chats.get(position).getLastMessage().getMessage();
        if(lastMessage.length()>35)
            lastMessage = lastMessage.substring(0,34)+"...";

        holder.sliding_frame_layout_chat_overview.setOnClickListener(view -> {
            Intent i = ChatActivity.newIntent(mContext,mUser, chats.get(position));
            mContext.startActivity(i);
        });
        holder.buttonDeleteRoom.setOnClickListener(view -> {
            roomOptionsCallBacks.deleteRoom(chats.get(position).get_id());
        });
        holder.buttonSetPrivateRoom.setOnClickListener(view->{

        });

        holder.textViewChatNickname.setText(chats.get(position).getName());
        holder.textViewChatLastMessageDate.setText(timeFormat.getTimeFormat(chats.get(position).getUpdated_at()));
        holder.textViewChatLastMessage.setText(lastMessage);
        holder.imageViewGoToChat.setImageResource(R.drawable.right_arrow_icon);
        holder.imageViewChatBottomLine.setImageResource(R.drawable.costume_single_chat_outview_border);
        viewBinderHelper.bind(holder.swipeRevealLayout, chats.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewChatNickname;
        private TextView textViewChatLastMessageDate;
        private TextView textViewChatLastMessage;
        private ImageView imageViewGoToChat;
        private ImageView imageViewChatBottomLine;
        private SwipeRevealLayout swipeRevealLayout;
        private FrameLayout sliding_frame_layout_chat_overview;

        private Button buttonDeleteRoom;
        private Button buttonSetPrivateRoom;

        //Make reference to the views
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewChatNickname = itemView.findViewById(R.id.sliding_chat_outview_nickname);
            textViewChatLastMessageDate = itemView.findViewById(R.id.sliding_chat_outview_last_message_date);
            textViewChatLastMessage = itemView.findViewById(R.id.sliding_chat_outview_last_message);
            imageViewGoToChat = itemView.findViewById(R.id.sliding_chat_outview_go_to);
            imageViewChatBottomLine = itemView.findViewById(R.id.sliding_chat_outview_bottom_line);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout);
            sliding_frame_layout_chat_overview = itemView.findViewById(R.id.sliding_framelayout_chat_overview);

            buttonDeleteRoom = itemView.findViewById(R.id.sliding_chat_outview_delete_button);
            buttonSetPrivateRoom = itemView.findViewById(R.id.sliding_chat_outview_private_button);
        }
    }
}
