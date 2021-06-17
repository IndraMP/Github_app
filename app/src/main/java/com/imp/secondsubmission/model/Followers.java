package com.imp.secondsubmission.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Followers implements Parcelable {
    private String name;
    private String username;
    private String company;
    private String location;
    private String photo;
    private int follower;
    private int following;
    private int repository;

    public Followers(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getRepository() {
        return repository;
    }

    public void setRepository(int repository) {
        this.repository = repository;
    }

    protected Followers(Parcel in) {
        name = in.readString();
        username = in.readString();
        company = in.readString();
        location = in.readString();
        photo = in.readString();
        follower = in.readInt();
        following = in.readInt();
        repository = in.readInt();
    }

    public static final Creator<Followers> CREATOR = new Creator<Followers>() {
        @Override
        public Followers createFromParcel(Parcel in) {
            return new Followers(in);
        }

        @Override
        public Followers[] newArray(int size) {
            return new Followers[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(company);
        dest.writeString(location);
        dest.writeString(photo);
        dest.writeInt(follower);
        dest.writeInt(following);
        dest.writeInt(repository);
    }
}
