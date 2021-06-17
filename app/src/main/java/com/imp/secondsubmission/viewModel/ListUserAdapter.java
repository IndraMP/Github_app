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
import com.imp.secondsubmission.model.User;

import java.util.ArrayList;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.listViewHolder> {
    private ArrayList<User> listuser = new ArrayList<>();

    public void setData(ArrayList<User> items){
        listuser.clear();
        listuser.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_user, parent, false);
        return new listViewHolder(view);
    }

    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull final listViewHolder holder, int position) {
        holder.bind(listuser.get(position));
//        final User user =listuser.get(position);
//        holder.tvName.setText(user.getName());
//        holder.tvCompany.setText(user.getCompany());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listuser.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listuser.size();
    }

    public class listViewHolder extends RecyclerView.ViewHolder {

        ImageView impphoto;
        TextView tvName, tvUserName, tvLocation, tvCompany, tvFollowing, tvFollower, tvRepository;
        public listViewHolder(@NonNull View itemView) {
            super(itemView);

            impphoto = itemView.findViewById(R.id.imp_item_photo);
            tvCompany = itemView.findViewById(R.id.tv_item_company);
            tvFollower = itemView.findViewById(R.id.tv_item_follower);
            tvFollowing = itemView.findViewById(R.id.tv_item_following);
            tvLocation = itemView.findViewById(R.id.tv_item_location);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvUserName = itemView.findViewById(R.id.tv_item_username);
            tvRepository = itemView.findViewById(R.id.tv_item_repository);
        }

        void bind(User user){
            Glide.with(itemView.getContext())
                    .load(user.getPhoto())
                    .apply(new RequestOptions().override(55, 55))
                    .into(impphoto);
            tvName.setText(user.getName());
            tvCompany.setText(user.getCompany());
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(User data);
    }
}
