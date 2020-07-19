package com.example.umbeo.Storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserPreference {
    private static final String PREF_NAME = "User";
    // Shared pref mode
    private static int PRIVATE_MODE = 0;
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    private Context _context;
    private String UserId = "id";
    private String Token = "token";
    private String UserName = "name";
    private String Email = "email";
    private String profilePic = "profilePic";
    private Integer LoyaltyPoints = 0;
    private Integer Theme = 0;
    private static final String Addresses = "addresses";
    private static final String ShopId = "5f131026a7cd970017e7b655";


    @SuppressLint("CommitPrefEdits")
    public UserPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public String getShopId() {
        return pref.getString(ShopId, "5f131026a7cd970017e7b655");
    }


    public String getUserId() {
        return pref.getString(UserId, null);
    }

    public void setUserId(String userId) {
        editor.putString(UserId, userId);
        editor.apply();
    }

    public String getToken() {
        return pref.getString(Token, null);
    }

    public void setToken(String token) {
        editor.putString(Token, token);
        editor.apply();
    }

    public String getProfilePic() {
        return pref.getString(profilePic, "null");
    }

    public void setProfilePic(String profile) {
        editor.putString(profilePic, profile);
        editor.apply();
    }

    public String getUserName() {
        return pref.getString(UserName, null);
    }

    public void setUserName(String userName) {
        editor.putString(UserName, userName);
        editor.apply();
    }

    public String getEmail() {
     return    pref.getString(Email, null);
    }

    public void setEmail(String email) {
        editor.putString(Email, email);
        editor.apply();
    }

    public Integer getLoyaltyPoints() {
        return pref.getInt(String.valueOf(LoyaltyPoints), 0);
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        editor.putInt(String.valueOf(LoyaltyPoints), loyaltyPoints);
        editor.apply();
    }
    public Integer getTheme() {
        return pref.getInt(String.valueOf(Theme), 0);
    }

    public void setTheme(Integer theme) {
        editor.putInt(String.valueOf(Theme), theme);
        editor.apply();
    }

    public void setAddresses(List<String> dataList) {
        if (dataList != null) {
            Set<String> set = new HashSet<String>(dataList);
            editor.putStringSet(Addresses, set);
            editor.commit();
        }
    }

    public List<String> getAddresses() {
        Set<String> list = pref.getStringSet(Addresses, null);
        if (list != null) {
            return new ArrayList<>(list);
        }
        return null;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
