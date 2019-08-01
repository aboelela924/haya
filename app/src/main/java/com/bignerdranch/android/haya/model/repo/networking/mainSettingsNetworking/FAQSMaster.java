package com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking;

import android.os.Parcel;
import android.os.Parcelable;

public class FAQSMaster implements Parcelable {
    private FAQs[] faqs;

    public FAQs[] getFaqs ()
    {
        return faqs;
    }

    public void setFaqs (FAQs[] faqs)
    {
        this.faqs = faqs;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [faqs = "+faqs+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.faqs, flags);
    }

    public FAQSMaster() {
    }

    protected FAQSMaster(Parcel in) {
        this.faqs = in.createTypedArray(FAQs.CREATOR);
    }

    public static final Parcelable.Creator<FAQSMaster> CREATOR = new Parcelable.Creator<FAQSMaster>() {
        @Override
        public FAQSMaster createFromParcel(Parcel source) {
            return new FAQSMaster(source);
        }

        @Override
        public FAQSMaster[] newArray(int size) {
            return new FAQSMaster[size];
        }
    };
}



