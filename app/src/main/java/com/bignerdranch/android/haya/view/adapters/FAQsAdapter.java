package com.bignerdranch.android.haya.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking.FAQs;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class FAQsAdapter extends RecyclerView.Adapter<FAQsAdapter.FAQsViewHolder> {

    private Context mContext;
    private List<FAQs> mFAQsList;

    public FAQsAdapter(Context ctx, List<FAQs> faQs){
        mContext = ctx;
        mFAQsList = faQs;
    }

    @NonNull
    @Override
    public FAQsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.faqs_cell,parent,false);
        return new FAQsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQsViewHolder holder, int position) {
        holder.bind(mFAQsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFAQsList.size();
    }

    class FAQsViewHolder extends RecyclerView.ViewHolder {
        private ExpandableLayout mExpandableLayout;
        private TextView mQuestionTextView;
        private TextView mAnswerTextView;
        private ImageView mArrowImageView;

        public FAQsViewHolder(@NonNull View itemView) {
            super(itemView);

            mQuestionTextView = itemView.findViewById(R.id.question_text_view);
            mExpandableLayout = itemView.findViewById(R.id.expandable_layout);
            mAnswerTextView = itemView.findViewById(R.id.answer_text_view);
            itemView.findViewById(R.id.question_linear_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mExpandableLayout.toggle();
                    rotateArror(mExpandableLayout.isExpanded());
                }
            });
            mArrowImageView = itemView.findViewById(R.id.arrow_image_view);
            rotateArror(mExpandableLayout.isExpanded());
        }

        public void bind(FAQs faQs){
            mQuestionTextView.setText(faQs.getQuestion());
            mAnswerTextView.setText(faQs.getAnswer());
        }

        private void rotateArror(boolean isExpanded){
            if(isExpanded){
                mArrowImageView.setRotation(90f);
            }else{
                mArrowImageView.setRotation(0f);
            }
        }
    }
}
