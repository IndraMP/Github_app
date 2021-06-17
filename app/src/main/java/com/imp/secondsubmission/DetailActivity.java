package com.imp.secondsubmission;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.imp.secondsubmission.model.User;
import com.imp.secondsubmission.viewModel.SectionsPagerAdapter;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_DETAIL = "extra_detail";
    ImageView impphoto;
    TextView tvName, tvUserName, tvLocation, tvCompany, tvFollowing, tvFollower, tvRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Detail User"); // set the top title

        impphoto = findViewById(R.id.imp_item_photo);
        tvCompany = findViewById(R.id.tv_item_company);
        tvFollower = findViewById(R.id.tv_item_follower);
        tvFollowing = findViewById(R.id.tv_item_following);
        tvLocation = findViewById(R.id.tv_item_location);
        tvName = findViewById(R.id.tv_item_name);
        tvRepository = findViewById(R.id.tv_item_repository);
        tvUserName = findViewById(R.id.tv_item_username);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(sectionsPagerAdapter);
        int orientation = (getResources().getConfiguration().orientation);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            viewPager.getLayoutParams().height = (int) getResources().getDimension(R.dimen.height);
        }else {
            viewPager.getLayoutParams().height = (int) getResources().getDimension(R.dimen.height1);
        }
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);

        final User user =getIntent().getParcelableExtra(EXTRA_DETAIL);

        Glide.with(DetailActivity.this)
                .load(user.getPhoto())
                .apply(new RequestOptions().override(200, 255))
                .into(impphoto);

        String getName = user.getName();
        tvName.setText(getName);

        String getUsername = user.getUsername();
        tvUserName.setText(getUsername);

        String getFollower = String.valueOf(user.getFollower());
        tvFollower.setText(getFollower);

        String getFollowing = String.valueOf(user.getFollowing());
        tvFollowing.setText(getFollowing);

        String getLocation = user.getLocation();
        tvLocation.setText(getLocation);

        String getComapny = user.getCompany();
        tvCompany.setText(getComapny);

        String getRepository = String.valueOf(user.getRepository());
        tvRepository.setText(getRepository);
    }
}
