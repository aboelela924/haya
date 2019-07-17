package com.bignerdranch.android.haya.model.repo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermsExample {
    @SerializedName("terms")
    @Expose
    private Term terms;
    private String error = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Term getTerms() {
        return terms;
    }

    public void setTerms(Term terms) {
        this.terms = terms;
    }
}
