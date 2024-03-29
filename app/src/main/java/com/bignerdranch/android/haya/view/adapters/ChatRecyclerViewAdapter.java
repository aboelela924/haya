package com.bignerdranch.android.haya.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.utils.TimeFormat;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.view.activities.ChatActivity;

import java.util.List;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {

    private List<Room> chats;
    private List<String> chatLastMessage;
    private Context mContext;
    private User mUser;

    public ChatRecyclerViewAdapter(Context ctx,List<Room> chats, List<String> chatLastMessage, User user)
    {
        mContext = ctx;
        mUser = user;
        this.chats = chats;
        this.chatLastMessage = chatLastMessage;
    }

    //Create new views.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_chat_outview, parent, false);

        return new ViewHolder(v);
    }

    //Bind data to views according to item index.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeFormat timeFormat = new TimeFormat();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = ChatActivity.newIntent(mContext,mUser,chats.get(position));
                mContext.startActivity(i);
            }
        });
        holder.textViewChatNickname.setText(chats.get(position).getName());
        holder.textViewChatLastMessageDate.setText(timeFormat.getTimeFormat(chats.get(position).getUpdated_at()));
        holder.textViewChatLastMessage.setText("");
        holder.imageViewGoToChat.setImageResource(R.drawable.right_arrow_icon);
        holder.imageViewChatBottomLine.setImageResource(R.drawable.costume_single_chat_outview_border);

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

        //Make reference to the views
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewChatNickname = (TextView) itemView.findViewById(R.id.chat_outview_nickname);
            textViewChatLastMessageDate = (TextView) itemView.findViewById(R.id.chat_outview_last_message_date);
            textViewChatLastMessage = (TextView) itemView.findViewById(R.id.chat_outview_last_message);
            imageViewGoToChat = (ImageView) itemView.findViewById(R.id.chat_outview_go_to);
            imageViewChatBottomLine = (ImageView) itemView.findViewById(R.id.chat_outview_bottom_line);
        }
    }
}
