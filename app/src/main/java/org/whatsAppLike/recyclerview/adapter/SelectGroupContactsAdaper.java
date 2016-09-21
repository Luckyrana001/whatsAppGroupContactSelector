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
import org.whatsAppLike.recyclerview.model.UserList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rana lucky on 9/1/2016.
 */
public class SelectGroupContactsAdaper extends RecyclerView.Adapter<SelectGroupContactsAdaper.ExampleViewHolder> {
    Context context;
    private final LayoutInflater mInflater;
    private final List<UserList> mModels;
    ImageView imageTv;

    /*public SearchUserAdapter(Context context, List<UserList> models) {
        mInflater = LayoutInflater.from(context);
        mModels = new ArrayList<>(models);
    }*/

    public SelectGroupContactsAdaper(Context context, List<UserList> models) {
        mInflater = LayoutInflater.from(context);
        mModels = new ArrayList<>(models);
        this.context = context;
    }


    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvText,idTv,roleTv;
        ImageView checkbox;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.nametv);
              checkbox = (ImageView) itemView.findViewById(R.id.checkboxTv);
            idTv =  (TextView) itemView.findViewById(R.id.idTv);
            roleTv =  (TextView) itemView.findViewById(R.id.roleTv);
            imageTv =  (ImageView) itemView.findViewById(R.id.imageTv);
        }


    }
    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.anticipateovershoot_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.add_group_contacts, parent, false);
        return new ExampleViewHolder(itemView);
    }

    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {
        final UserList model = mModels.get(position);

                holder.tvText.setText(checkNullString(model.getName()));

        holder.idTv.setText(checkNullString(model.getId()));
        holder.roleTv.setText(model.getRole());

        holder.tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(holder);
                notifyDataSetChanged();
            }
        });
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(holder);
            }
        });
        if(model.isCheckStatus())
        {
            holder.checkbox.setImageResource(android.R.drawable.checkbox_on_background);
        }
        else
        {
            holder. checkbox.setImageResource(android.R.drawable.checkbox_off_background);

        }

    }



    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public void animateTo(List<UserList> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<UserList> newModels) {
        for (int i = mModels.size() - 1; i >= 0; i--) {
            final UserList model = mModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<UserList> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final UserList model = newModels.get(i);
            if (!mModels.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<UserList> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final UserList model = newModels.get(toPosition);
            final int fromPosition = mModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public UserList removeItem(int position) {
        final UserList model = mModels.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, UserList model) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final UserList model = mModels.remove(fromPosition);
        mModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }


    public static String checkNullString(String value){
        String result = "";
        if(value!=null) {
            if (!value.equals("null")) {
                result = value;
            }
        }
        return result;
    }
}
