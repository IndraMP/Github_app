package com.imp.secondsubmission.viewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.imp.secondsubmission.MainActivity;
import com.imp.secondsubmission.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<User>> listUser =new MutableLiveData<>();
    final ArrayList<User> listItems = new ArrayList<>();

    public void setUsers (final String query, final Context context){
        listItems.clear();
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "token b81ee98710f3dcd0cc2757c84add4c46b96efdf9");
        client.addHeader("User-Agent", "request");
        String URL = "https://api.github.com/search/users?q=" + query;
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String hasil = new String(responseBody);
                Log.d(MainActivity.TAG, hasil);
                try {
                    JSONObject responseObject = new JSONObject(hasil);
                    JSONArray list = responseObject.getJSONArray("items");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject data = list.getJSONObject(i);
                        String username = data.getString("login");
                        setDataDetail(username, context);
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

    public void setData (final Context context){
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "token b81ee98710f3dcd0cc2757c84add4c46b96efdf9");
        client.addHeader("User-Agent", "request");
        String URL = "https://api.github.com/users";
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String hasil = new String(responseBody);
                Log.d(MainActivity.TAG, hasil);
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
                Log.d(MainActivity.TAG, hasil);
                try {
                    JSONObject responseObject = new JSONObject(hasil);
                    User usersData = new User();
                    usersData.setUsername(responseObject.getString("login"));
                    usersData.setName(responseObject.getString("name"));
                    usersData.setPhoto(responseObject.getString("avatar_url"));
                    usersData.setCompany(responseObject.getString("company"));
                    usersData.setLocation(responseObject.getString("location"));
                    usersData.setRepository(Integer.parseInt(responseObject.getString("public_repos")));
                    usersData.setFollower(Integer.parseInt(responseObject.getString("followers")));
                    usersData.setFollowing(Integer.parseInt(responseObject.getString("following")));
                    listItems.add(usersData);
                    listUser.postValue(listItems);
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

    public LiveData<ArrayList<User>> getUsers() {
        return listUser;
    }

}
