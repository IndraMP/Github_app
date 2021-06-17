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

import java.util.ArrayList;

public class ListFollowersAdapter extends RecyclerView.Adapter<ListFollowersAdapter.listViewHolder> {
    private ArrayList<Followers> listFollowers = new ArrayList<>();

    public void setData(ArrayList<Followers> items){
        listFollowers.clear();
        listFollowers.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_user, parent, false);
        return new ListFollowersAdapter.listViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listViewHolder holder, int position) {
        holder.bind(listFollowers.get(position));
    }

    @Override
    public int getItemCount() {
        return listFollowers.size();
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

        void bind(Followers followers){
            Glide.with(itemView.getContext())
                    .load(followers.getPhoto())
                    .apply(new RequestOptions().override(55, 55))
                    .into(impphoto);
            tvName.setText(followers.getName());
            tvCompany.setText(followers.getCompany());
        }
    }
}
