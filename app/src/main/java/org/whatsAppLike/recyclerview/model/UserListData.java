package org.whatsAppLike.recyclerview.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Rana lucky on 8/31/2016.
 */
public class UserListData implements Parcelable {
    public ArrayList<UserList> userList;


    /**
     * Constructs a Question from values
     */
    public UserListData(ArrayList<UserList> userList) {
         this.userList = userList;
    }

    /**
     * Constructs a Question from a Parcel
     * @param parcel Source Parcel
     */
    public UserListData(Parcel parcel) {
        this.userList = parcel.readArrayList(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Required method to write to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeList(userList);
    }

    // Method to recreate a Question from a Parcel
    public static Creator<UserListData> CREATOR = new Creator<UserListData>() {

        @Override
        public UserListData createFromParcel(Parcel source) {
            return new UserListData(source);
        }

        @Override
        public UserListData[] newArray(int size) {
            return new UserListData[size];
        }

    };
}