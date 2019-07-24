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
import com.bignerdranch.android.haya.view.activities.ChatActivity;
import com.bignerdranch.android.haya.view.activities.MessageClickCallbacks;
import com.bignerdranch.android.haya.viewModel.ChatViewModel;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CITIZIEN_MESSAGE = 0;
    private static final int FOREIGNER_MESSAGE = 1;
    private static final int SYSTEM_MESSAGE = 2;

    private Context mContext;
    private List<Message> mMessages;
    private MessageClickCallbacks mCallbacks;

    public MessagesAdapter(Context context, List<Message> messages, MessageClickCallbacks callbacks) {
        mContext = context;
        mMessages = messages;
        mCallbacks = callbacks;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessages.get(position);
        if(message.getUser() == null){
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
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
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

        public SystemMessageViewHolder(@NonNull View itemView) {
            super(itemView);
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
