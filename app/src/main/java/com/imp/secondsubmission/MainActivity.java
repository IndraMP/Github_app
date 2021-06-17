package com.imp.secondsubmission;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.imp.secondsubmission.model.User;
import com.imp.secondsubmission.viewModel.ListUserAdapter;
import com.imp.secondsubmission.viewModel.MainViewModel;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListUserAdapter adapter;
    ProgressBar progressBar;
    private RecyclerView rvUser;
    private MainViewModel mainViewModel;
    Button btnSearch;
    EditText edtSearch;

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("List User"); // set the top title

        rvUser = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.loadingProgress);
        edtSearch = findViewById(R.id.edt_search);
        btnSearch = findViewById(R.id.btn_search);

        rvUser.setLayoutManager(new LinearLayoutManager(this));
        rvUser.setHasFixedSize(true);
        adapter = new ListUserAdapter();
        adapter.notifyDataSetChanged();
        rvUser.setAdapter(adapter);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = edtSearch.getText().toString();

                if (TextUtils.isEmpty(search))return;

                showLoading(true);
                mainViewModel.setUsers(search, getApplicationContext());
            }
        });

        mainViewModel.getUsers().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> listUser) {
                if (listUser != null){
                    adapter.setData(listUser);
                    showLoading(false);
                }
            }
        });

            adapter.setOnItemClickCallback(new ListUserAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(User data) {
                showSelectedUser(data);
                User user = new User();
                user.setPhoto(data.getPhoto());
                user.setName(data.getName());
                user.setUsername(data.getUsername());
                user.setFollower(data.getFollower());
                user.setFollowing(data.getFollowing());
                user.setRepository(data.getRepository());
                user.setLocation(data.getLocation());
                user.setCompany(data.getCompany());

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_DETAIL, user);
                startActivity(intent);
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showSelectedUser(User user) {
        Toast.makeText(this, String.format(getResources().getString(R.string.choose))+ " " + user.getName(), Toast.LENGTH_SHORT).show();
    }
}
