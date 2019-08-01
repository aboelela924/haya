package com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking;

import android.os.Parcel;
import android.os.Parcelable;

public class FAQs implements Parcelable {
    private String question;

    private String answer;

    private String updated_at;

    private String created_at;

    private String _id;

    private String id;

    private String activation;

    public String getQuestion ()
    {
        return question;
    }

    public void setQuestion (String question)
    {
        this.question = question;
    }

    public String getAnswer ()
    {
        return answer;
    }

    public void setAnswer (String answer)
    {
        this.answer = answer;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getActivation ()
    {
        return activation;
    }

    public void setActivation (String activation)
    {
        this.activation = activation;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [question = "+question+", answer = "+answer+", updated_at = "+updated_at+", created_at = "+created_at+", _id = "+_id+", id = "+id+", activation = "+activation+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeString(this.answer);
        dest.writeString(this.updated_at);
        dest.writeString(this.created_at);
        dest.writeString(this._id);
        dest.writeString(this.id);
        dest.writeString(this.activation);
    }

    public FAQs() {
    }

    protected FAQs(Parcel in) {
        this.question = in.readString();
        this.answer = in.readString();
        this.updated_at = in.readString();
        this.created_at = in.readString();
        this._id = in.readString();
        this.id = in.readString();
        this.activation = in.readString();
    }

    public static final Parcelable.Creator<FAQs> CREATOR = new Parcelable.Creator<FAQs>() {
        @Override
        public FAQs createFromParcel(Parcel source) {
            return new FAQs(source);
        }

        @Override
        public FAQs[] newArray(int size) {
            return new FAQs[size];
        }
    };
}
