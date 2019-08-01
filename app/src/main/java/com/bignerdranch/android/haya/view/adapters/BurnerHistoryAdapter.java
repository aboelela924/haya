package com.bignerdranch.android.haya.view.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.haya.R;

import org.w3c.dom.Text;

public class BurnerHistoryAdapter {


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

        public void bind(){

        }
    }

}
