package org.whatsAppLike.recyclerview.adapter;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.whatsAppLike.recyclerview.R;
import org.whatsAppLike.recyclerview.common.SearchCallback;
import org.whatsAppLike.recyclerview.model.UserList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rana lucky on 9/1/2016.
 */
public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {
    Context context;
    private final LayoutInflater mInflater;
    private  List<UserList> mModels ;
    SearchCallback callback;
     View view ;
    boolean itemAdded = false;
    ViewHolder itemHolder;
    /*public SearchUserAdapter(Context context, List<UserList> models) {
        mInflater = LayoutInflater.from(context);
        mModels = new ArrayList<>(models);
    }*/

    public SearchUserAdapter(Context context, List<UserList> models, SearchCallback callback) {
        mInflater = LayoutInflater.from(context);
       this.mModels = models;
        this.context = context;
        this.callback = callback;
    }

    public View getView() {
        return view;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

       private final TextView tvText,idTv;
        final ImageView crossImg,profilePic;

        public ViewHolder(View itemView) {
            super(itemView);
          //  item = itemView;
            tvText = (TextView) itemView.findViewById(R.id.nametv);
            crossImg =  (ImageView) itemView.findViewById(R.id.crossImg);
            idTv =  (TextView) itemView.findViewById(R.id.idTv);
            profilePic=  (ImageView) itemView.findViewById(R.id.profilePic);
           }


    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = mInflater.inflate(R.layout.selected_user_list, parent, false);
        return new ViewHolder(view);
    }
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        itemHolder = holder;
        final UserList model = mModels.get(position);

        holder.tvText.setText(model.getName());
        holder.crossImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // interface method called here to intercept cross image click
                callback.changeCheckedStatus(holder.idTv.getText().toString());
            }
        });
      holder.idTv.setText(model.getId());


       if(model.isCheckStatus())
        {

            PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f);
            PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f);

            ObjectAnimator animateProfilePic = ObjectAnimator.ofPropertyValuesHolder(holder.profilePic, scaleXholder, scaleYholder);
            animateProfilePic.setDuration(750);
            animateProfilePic.start();


        }



    }



    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public Object getItem(int position) {
        return mModels.get(position);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public int  insert(UserList data) {

        mModels.add(data);
        notifyItemInserted(mModels.size()-1);
        return mModels.size()-1;
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(UserList data) {
        int position = mModels.indexOf(data);
        mModels.remove(position);
        notifyItemRemoved(position);

    }
    public int removeItemPos(UserList data)
    {
        return mModels.indexOf(data);
    }

    public void itemAddAnimation() {
        if(view!=null) {
           // ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);
            PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f);
            PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f);

            ObjectAnimator animateProfilePic = ObjectAnimator.ofPropertyValuesHolder(itemHolder.profilePic, scaleXholder, scaleYholder);
            animateProfilePic.setDuration(1000);
            animateProfilePic.start();
        }
    }


    public void itemRemoveAnimation() {
       // ImageView profilePic = (ImageView) view.findViewById(R.id.profilePic);

        PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X,0f);
        PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y,0f);

        ObjectAnimator animateProfilePic = ObjectAnimator.ofPropertyValuesHolder(itemHolder.profilePic,scaleYholder, scaleXholder);
        animateProfilePic.setDuration(1000);
        animateProfilePic.start();
    }

}
