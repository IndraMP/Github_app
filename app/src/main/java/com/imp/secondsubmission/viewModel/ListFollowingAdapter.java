package com.imp.secondsubmission.viewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.imp.secondsubmission.R;
import com.imp.secondsubmission.model.Followers;
import com.imp.secondsubmission.model.Following;

import java.util.ArrayList;

public class ListFollowingAdapter extends RecyclerView.Adapter<ListFollowingAdapter.listViewHolder> {

    private ArrayList<Following> listFollowing = new ArrayList<>();

    public void setData(ArrayList<Following> items){
        listFollowing.clear();
        listFollowing.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_user, parent, false);
        return new ListFollowingAdapter.listViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listViewHolder holder, int position) {
        holder.bind(listFollowing.get(position));
    }

    @Override
    public int getItemCount() {
        return listFollowing.size();
    }

    public class listViewHolder extends RecyclerView.ViewHolder {
        ImageView impphoto;
        TextView tvName, tvCompany;
        public listViewHolder(@NonNull View itemView) {
            super(itemView);
            impphoto = itemView.findViewById(R.id.imp_item_photo);
            tvCompany = itemView.findViewById(R.id.tv_item_company);
            tvName = itemView.findViewById(R.id.tv_item_name);
        }

        void bind(Following following){
            Glide.with(itemView.getContext())
                    .load(following.getPhoto())
                    .apply(new RequestOptions().override(55, 55))
                    .into(impphoto);
            tvName.setText(following.getName());
            tvCompany.setText(following.getCompany());
        }
    }
}
