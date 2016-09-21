package org.whatsAppLike.recyclerview.adapter;

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
    ImageView imageTv;
    SearchCallback callback;
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


    public class ViewHolder extends RecyclerView.ViewHolder {

       private final TextView tvText,idTv;
        ImageView crossImg;

        public ViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.nametv);
            crossImg =  (ImageView) itemView.findViewById(R.id.crossImg);
            idTv =  (TextView) itemView.findViewById(R.id.idTv);
           }


    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.selected_user_list, parent, false);
        return new ViewHolder(itemView);
    }
    /*public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }*/
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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


    }



    @Override
    public int getItemCount() {
        return mModels.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(UserList data) {
        mModels.add(data);
        notifyItemInserted(mModels.size()-1);
       // notifyDataSetChanged();
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(UserList data) {
        int position = mModels.indexOf(data);
        mModels.remove(position);
        notifyItemRemoved(position);
    }



}
