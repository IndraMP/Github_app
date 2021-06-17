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
import com.imp.secondsubmission.model.Following;
import com.imp.secondsubmission.model.User;
import com.imp.secondsubmission.viewModel.FollowingViewModel;
import com.imp.secondsubmission.viewModel.ListFollowersAdapter;
import com.imp.secondsubmission.viewModel.ListFollowingAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {

    public static final String EXTRA_DETAIL = "extra_detail";
    public static final String TAG = FollowingFragment.class.getSimpleName();
    ProgressBar progressBar;
    RecyclerView rvFollowing;
    ListFollowingAdapter adapter;
    private FollowingViewModel followingViewModel;


    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressbarFollowing);
        rvFollowing = view.findViewById(R.id.rv_following);

        rvFollowing.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFollowing.setHasFixedSize(true);
        adapter = new ListFollowingAdapter();
        adapter.notifyDataSetChanged();
        rvFollowing.setAdapter(adapter);

        followingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel.class);

        final User user = getActivity().getIntent().getParcelableExtra(EXTRA_DETAIL);

        followingViewModel.setData(getActivity().getApplicationContext(), user.getUsername());
        showLoading(true);

        followingViewModel.getFollowing().observe(getActivity(), new Observer<ArrayList<Following>>() {
            @Override
            public void onChanged(ArrayList<Following> following) {
                if (following != null){
                    adapter.setData(following);
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
