package com.example.umbeo.Storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.umbeo.BuildConfig;
import com.facebook.login.LoginManager;

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
    private String shopPh = "1111111111";
    private String shopTimeSlot = "shopTimeSlot";
    private String shopDeliveryCharges = "shopDeliveryCharges";
    private String shopCategory = "shopCategory";
    private String shopCategoryName = "shopCategoryName";
    private String deliveryAddress = "deliveryAddress";
    private Integer LoyaltyPoints = 0;
    private Integer Theme = 1;
    private static final String Addresses = "addresses";
    private static final Boolean achievments = false;
    private static final String ShopId = "shopId";
    private static final String Currency = "₹";


    @SuppressLint("CommitPrefEdits")
    public UserPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getCurrency() {
         return pref.getString(Currency, "₹");
    }
    public String getShopId() {

        if(BuildConfig.USER_TYPE.equalsIgnoreCase("fashion")) {
            return pref.getString(ShopId, "5f33d77ee10e3a0017c72aea");
        }
        else {
            return pref.getString(ShopId, "5f131026a7cd970017e7b655");
        }
    }


    public String getUserId() {
        return pref.getString(UserId, null);
    }

    public void setUserId(String userId) {
        editor.putString(UserId, userId);
        editor.apply();
    }

    public String getdeliveryAddress() {
        return pref.getString(deliveryAddress, null);
    }

    public void setdeliveryAddress(String deliveryaddress) {
        editor.putString(deliveryAddress, deliveryaddress);
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
        return pref.getString(Email, null);
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

    public void setShopCategory(List<String> dataList) {
        if (dataList != null) {
            Set<String> set = new HashSet<String>(dataList);
            editor.putStringSet(shopCategory, set);
            editor.commit();
        }
    }

    public List<String> getShopCategory() {
        Set<String> list = pref.getStringSet(shopCategory, null);
        if (list != null) {
            return new ArrayList<>(list);
        }
        return null;
    }


    public void setShopCategoryName(List<String> dataList) {
        if (dataList != null) {
            Set<String> set = new HashSet<String>(dataList);
            editor.putStringSet(shopCategoryName, set);
            editor.commit();
        }
    }

    public List<String> getShopCategoryName() {
        Set<String> list = pref.getStringSet(shopCategoryName, null);
        if (list != null) {
            return new ArrayList<>(list);
        }
        return null;
    }


    public void setAchievments(List<Boolean> dataList) {
        if (dataList != null) {
            editor.putInt(dataList + "_size", dataList.size());

            for (int i = 0; i < dataList.size(); i++) {
                editor.putBoolean(achievments + "_" + i, dataList.get(i));
                editor.commit();
            }
        }
    }

    public Boolean[] getAchievments() {
        int size = pref.getInt(achievments + "_size", 0);
        Boolean array[] = new Boolean[size];
        for(int i=0;i<size;i++)
            array[i] = pref.getBoolean(achievments + "_" + i, false);
        return array;
    }

    public void setShopTimeSlot(List<String> dataList) {
        if (dataList != null) {
            Set<String> set = new HashSet<String>(dataList);
            editor.putStringSet(shopTimeSlot, set);
            editor.commit();
        }
    }

    public List<String> getShopTimeSlot() {
        Set<String> list = pref.getStringSet(shopTimeSlot, null);
        if (list != null) {
            return new ArrayList<>(list);
        }
        return null;
    }


    public void setShopDeliveryCharges(List<String> dataList) {
        if (dataList != null) {
            Set<String> set = new HashSet<String>(dataList);
            editor.putStringSet(shopDeliveryCharges, set);
            editor.commit();
        }
    }

    public List<String> getShopDeliveryCharges() {
        Set<String> list = pref.getStringSet(shopDeliveryCharges, null);
        if (list != null) {
            return new ArrayList<>(list);
        }
        return null;
    }

    public String getShopPh() {
        return    pref.getString(shopPh, null);
    }

    public void setShopPh(String ph) {
        editor.putString(shopPh, ph);
        editor.apply();
    }


    public void logout(){
        LoginManager.getInstance().logOut();

        editor.clear();
        editor.commit();
    }
}
