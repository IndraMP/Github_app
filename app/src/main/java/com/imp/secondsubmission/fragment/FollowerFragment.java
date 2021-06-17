package com.imp.secondsubmission.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.imp.secondsubmission.R;
import com.imp.secondsubmission.model.Followers;
import com.imp.secondsubmission.model.User;
import com.imp.secondsubmission.viewModel.FollowersViewModel;
import com.imp.secondsubmission.viewModel.ListFollowersAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowerFragment extends Fragment {

    public static final String TAG = FollowerFragment.class.getSimpleName();
    public static final String EXTRA_DETAIL = "extra_detail";
    ProgressBar progressBar;
    RecyclerView rvFollowers;
    ListFollowersAdapter adapter;
    private FollowersViewModel followersViewModel;


    public FollowerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressbarFollowers);
        rvFollowers = view.findViewById(R.id.rv_followers);

        rvFollowers.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFollowers.setHasFixedSize(true);
        adapter = new ListFollowersAdapter();
        adapter.notifyDataSetChanged();
        rvFollowers.setAdapter(adapter);

        followersViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel.class);

        final User user = getActivity().getIntent().getParcelableExtra(EXTRA_DETAIL);

        followersViewModel.setData(getActivity().getApplicationContext(), user.getUsername());
        showLoading(true);

        followersViewModel.getFollowers().observe(getActivity(), new Observer<ArrayList<Followers>>() {
            @Override
            public void onChanged(ArrayList<Followers> followers) {
                if (followers != null){
                    adapter.setData(followers);
                    showLoading(false);
                }
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
