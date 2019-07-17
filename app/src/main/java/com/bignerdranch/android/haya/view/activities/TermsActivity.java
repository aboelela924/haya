package com.bignerdranch.android.haya.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.viewModel.TermsViewModel;
import com.github.ybq.android.spinkit.SpinKitView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsActivity extends AppCompatActivity {

    @BindView(R.id.terms_text_view_terms_activity) TextView mTermsTextView;
    @BindView(R.id.loading_indecator_terms_activity) SpinKitView mLoadingIndecator;

    private TermsViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_activity);
        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(TermsViewModel.class);
        mViewModel.mData.observe(this, termsExample -> {
            String terms = termsExample.getTerms().getContent();
            mTermsTextView.setText(terms);
            hideLoading();
        });
        mViewModel.loadTerms();
        showLoading();
    }

    @OnClick(R.id.close_button_terms_activity)
    public void close(View view){
        Intent i = MainActivity.newIntent(this, 2);
        startActivity(i);
        finish();
    }

    private void showLoading(){
        mLoadingIndecator.setVisibility(View.VISIBLE);
        mTermsTextView.setVisibility(View.INVISIBLE);
    }

    private void hideLoading(){
        mLoadingIndecator.setVisibility(View.INVISIBLE);
        mTermsTextView.setVisibility(View.VISIBLE);
    }
}
