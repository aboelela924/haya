package com.bignerdranch.android.haya.view.adapters;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.Message;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.view.activities.ChatActivity;
import com.bignerdranch.android.haya.view.activities.MessageClickCallbacks;
import com.bignerdranch.android.haya.viewModel.ChatViewModel;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CITIZIEN_MESSAGE = 0;
    private static final int FOREIGNER_MESSAGE = 1;
    private static final int SYSTEM_MESSAGE = 2;
    private static final int CITIZIEN_MESSAGE_NO_NETWORK = 3;

    private Context mContext;
    private List<Message> mMessages;
    private User mUser;
    private MessageClickCallbacks mCallbacks;



    public MessagesAdapter(Context context, List<Message> messages, User user, MessageClickCallbacks callbacks) {
        mContext = context;
        mMessages = messages;
        mCallbacks = callbacks;
        mUser = user;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessages.get(position);
        if(message.getType().equals("1")){
            return  SYSTEM_MESSAGE;
        }
        if (message.getId() == null){
            return CITIZIEN_MESSAGE_NO_NETWORK;
        }

        if(message.getUser().getUser_id().equals(mUser.getId())){
            return CITIZIEN_MESSAGE;
        }
        return FOREIGNER_MESSAGE;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case CITIZIEN_MESSAGE:
                View v1 = LayoutInflater.from(mContext).inflate(
                        R.layout.citizien_message,
                        parent,
                        false);
                return new CitizienMessageViewHolder(v1);
            case FOREIGNER_MESSAGE:
                View v2 = LayoutInflater.from(mContext).inflate(
                        R.layout.foreign_message,
                        parent,
                        false);
                return new ForeignMessageViewHolder(v2);
            case CITIZIEN_MESSAGE_NO_NETWORK:
                View v3 = LayoutInflater.from(mContext).inflate(
                        R.layout.citizien_message_no_network,
                        parent,
                        false);
                return new CitizienMessageNoNetworkViewHolder(v3);
            case SYSTEM_MESSAGE:
                View v4 = LayoutInflater.from(mContext).inflate(
                        R.layout.system_message,
                        parent,
                        false);
                return new SystemMessageViewHolder(v4);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = mMessages.get(position);
        if(holder instanceof CitizienMessageViewHolder){
            CitizienMessageViewHolder viewHolder = (CitizienMessageViewHolder) holder;
            viewHolder.bind(message);
        }else if(holder instanceof ForeignMessageViewHolder){
            ((ForeignMessageViewHolder) holder).bind(message);
        }else if(holder instanceof CitizienMessageNoNetworkViewHolder){
            ((CitizienMessageNoNetworkViewHolder) holder).bind(message);
        }else if(holder instanceof SystemMessageViewHolder){
            ((SystemMessageViewHolder) holder).bind(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    private class CitizienMessageNoNetworkViewHolder extends RecyclerView.ViewHolder{
        private TextView mMessageTextView;
        private TextView mTimeTextView;

        public CitizienMessageNoNetworkViewHolder(@NonNull View itemView) {
            super(itemView);

            mMessageTextView = itemView.findViewById(R.id.citizien_no_network_message_text_view);
            mTimeTextView = itemView.findViewById(R.id.citizien_no_network_message_time_text_View);
        }

        public void bind(Message message){
            mMessageTextView.setText(message.getMessage());
            mTimeTextView.setText(getTime(message.getCreated_at()));
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mCallbacks.onMessageLongClick(message);
                    return true;
                }
            });
        }
    }

    private class CitizienMessageViewHolder extends RecyclerView.ViewHolder{

        private TextView mMessageTextView;
        private TextView mTimeTextView;


        public CitizienMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            mMessageTextView = itemView.findViewById(R.id.citizien_message_text_view);
            mTimeTextView = itemView.findViewById(R.id.citizien_message_time_text_View);


        }

        public void bind(Message message){
            mMessageTextView.setText(message.getMessage());
            mTimeTextView.setText(getTime(message.getCreated_at()));
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mCallbacks.onMessageLongClick(message);
                    return true;
                }
            });
        }
    }


    private class ForeignMessageViewHolder extends RecyclerView.ViewHolder{

        private TextView mSenderTextView;;
        private TextView mMessageTextView;
        private TextView mTimeTextView;

        public ForeignMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            mSenderTextView = itemView.findViewById(R.id.foreinger_name_text_view);
            mMessageTextView = itemView.findViewById(R.id.foreinger_message_text_view);
            mTimeTextView = itemView.findViewById(R.id.foreinger_message_time_text_view);
        }

        public void bind(Message message){
            mSenderTextView.setText(message.getUser().getName());
            mMessageTextView.setText(message.getMessage());
            mTimeTextView.setText(getTime(message.getCreated_at()));
        }
    }

    private class SystemMessageViewHolder extends RecyclerView.ViewHolder{

        private TextView mSystemMessageTextView;

        public SystemMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            mSystemMessageTextView = itemView.findViewById(R.id.system_message_text_view);
        }

        public void bind(String systemMessage){
            mSystemMessageTextView.setText(systemMessage);
        }
    }

    private String getTime(String timeStamp){
        long durationInMillis = Long.valueOf(timeStamp);
        long millis = durationInMillis % 1000;
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

        String time = String.format("%02d:%02d", hour, minute);
        return time;
    }
}
