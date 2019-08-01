package com.bignerdranch.android.haya.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.networking.burnerCodeHistoryNetworking.History;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class BurnerHistoryAdapter extends RecyclerView.Adapter<BurnerHistoryAdapter.BurnerHistoryViewHolder> {
    private List<History> mHistoryList;
    private Context mContext;

    public BurnerHistoryAdapter(Context ctx, List<History> histories){
        mContext = ctx;
        mHistoryList = histories;
    }

    @NonNull
    @Override
    public BurnerHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.burner_code_history_cell,
                parent,
                false);
        return new BurnerHistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BurnerHistoryViewHolder holder, int position) {
        holder.bind(mHistoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }


    class BurnerHistoryViewHolder extends RecyclerView.ViewHolder{

        private ImageView mChatTypeImageVeiw;
        private TextView mChatNameTextView;
        private TextView mBurnerCodeTextView;
        private TextView mDateTextView;

        public BurnerHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            mChatTypeImageVeiw = itemView.findViewById(R.id.chat_type_image_view);
            mChatNameTextView = itemView.findViewById(R.id.chat_name_text_view);
            mBurnerCodeTextView = itemView.findViewById(R.id.chat_burner_code_text_view);
            mDateTextView = itemView.findViewById(R.id.burner_code_creat_date_text_view);
        }

        public void bind(History history){
            Drawable singlBurnerCodeIcon = mContext.getResources().getDrawable(R.drawable.man_user);


            /*Drawable chatType = Integer.valueOf(history.getType()) == 2 ?
                    mContext.getResources().getDrawable(R.drawable.group_burner_icon):
                    singlBurnerCodeIcon;*/

            mChatTypeImageVeiw.setImageDrawable( mContext.getResources().getDrawable(R.drawable.group_burner_icon));
            mChatNameTextView.setText(history.getName());
            mBurnerCodeTextView.setText(history.getToken());
            Timestamp ts = new Timestamp(Long.valueOf(history.getCreated_at()));
            /*Date date = ts;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String createAt =  format.format(date);*/
            mDateTextView.setText("Ya rab");
        }
    }

}
