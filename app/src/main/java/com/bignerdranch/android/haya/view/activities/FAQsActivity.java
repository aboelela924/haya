package com.bignerdranch.android.haya.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking.FAQSMaster;
import com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking.FAQs;
import com.bignerdranch.android.haya.view.adapters.FAQsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FAQsActivity extends AppCompatActivity {
    private static final String FAQS = "FAQS";

    private List<FAQs> mFAQsList = new ArrayList<>();
    private FAQsAdapter mAdapter;
    public static Intent newIntent(Context ctx, FAQSMaster faqs){
        Intent i = new Intent(ctx, FAQsActivity.class);
        i.putExtra(FAQS, faqs);
        return  i;
    }

    @BindView(R.id.faqs_recycler_view) RecyclerView mFAQsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        ButterKnife.bind(this);

        FAQSMaster master = getIntent().getParcelableExtra(FAQS);
        List<FAQs> list = Arrays.asList(master.getFaqs());
        mFAQsList.addAll(list);
        mFAQsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FAQsAdapter(this,mFAQsList);
        mFAQsRecyclerView.setAdapter(mAdapter);

    }
}
