package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.TermsExample;
import com.bignerdranch.android.haya.model.repo.networking.termsNetworking.TermsModel;

public class TermsViewModel extends ViewModel {
    public MutableLiveData<TermsExample> mData;
    private TermsModel mModel;
    public TermsViewModel() {
        mModel = TermsModel.getTermsModel();
        mData = mModel.mData;
    }
    public void loadTerms(){
        mModel.loadTerms();
    }
}
