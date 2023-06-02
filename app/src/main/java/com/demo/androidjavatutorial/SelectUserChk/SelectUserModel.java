package com.demo.androidjavatutorial.SelectUserChk;

import android.os.Parcel;
import android.os.Parcelable;

public class SelectUserModel implements Parcelable {

    String UserName;
    public boolean isSelected = false;

    public SelectUserModel(String userName) {
        UserName = userName;
    }

    protected SelectUserModel(Parcel in) {
        UserName = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<SelectUserModel> CREATOR = new Creator<SelectUserModel>() {
        @Override
        public SelectUserModel createFromParcel(Parcel in) {
            return new SelectUserModel(in);
        }

        @Override
        public SelectUserModel[] newArray(int size) {
            return new SelectUserModel[size];
        }
    };

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UserName);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}
