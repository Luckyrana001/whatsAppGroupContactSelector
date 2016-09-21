package org.whatsAppLike.recyclerview.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Rana lucky on 8/13/2016.
 */
public class UserListResponseModel {

    public ArrayList<UserList> getList() {
        return list;
    }

    public void setList(ArrayList<UserList> list) {
        this.list = list;
    }
    @SerializedName("userList")
    ArrayList<UserList> list = new ArrayList<UserList>();
}
