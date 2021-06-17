package com.imp.secondsubmission.viewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.imp.secondsubmission.fragment.FollowingFragment;
import com.imp.secondsubmission.model.Following;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FollowingViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Following>> listFollowing = new MutableLiveData<>();
    final ArrayList<Following> listItems = new ArrayList<>();

    public void setData (final Context context, String id){
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "token b81ee98710f3dcd0cc2757c84add4c46b96efdf9");
        client.addHeader("User-Agent", "request");
        String URL = "https://api.github.com/users/" + id + "/following";
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String hasil = new String(responseBody);
                Log.d(FollowingFragment.TAG, hasil);
                try {
                    JSONArray jsonArray = new JSONArray(hasil);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        String usernamelogin = data.getString("login");
                        setDataDetail(usernamelogin, context);
                    }
                }catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setDataDetail (final String usernamelogin, final Context context){
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "token b81ee98710f3dcd0cc2757c84add4c46b96efdf9");
        client.addHeader("User-Agent", "request");
        String URL = "https://api.github.com/users/" + usernamelogin;
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String hasil = new String(responseBody);
                Log.d(FollowingFragment.TAG, hasil);
                try {
                    JSONObject responseObject = new JSONObject(hasil);
                    Following followingData = new Following();
                    followingData.setUsername(responseObject.getString("login"));
                    followingData.setName(responseObject.getString("name"));
                    followingData.setPhoto(responseObject.getString("avatar_url"));
                    followingData.setCompany(responseObject.getString("company"));
                    followingData.setLocation(responseObject.getString("location"));
                    followingData.setRepository(Integer.parseInt(responseObject.getString("public_repos")));
                    followingData.setFollower(Integer.parseInt(responseObject.getString("followers")));
                    followingData.setFollowing(Integer.parseInt(responseObject.getString("following")));
                    listItems.add(followingData);
                    listFollowing.postValue(listItems);
                }catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Following>> getFollowing() {
        return listFollowing;
    }
}
