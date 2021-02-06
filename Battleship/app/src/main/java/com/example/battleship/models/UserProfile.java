package com.example.battleship.models;

public class UserProfile {
    private String imageUrl;
    private String nickname;

    public UserProfile() {
    }

    public UserProfile(String imageUrl, String nickname) {
        this.imageUrl = imageUrl;
        this.nickname = nickname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNickname() {
        return nickname;
    }
}
