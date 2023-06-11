package com.example.pesanan.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Session";
    private static final String KEY_ID_PELANGGAN = "id_pelanggan";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ID_MENU = "id_menu";
    private static final String KEY_ID_DETAIL = "id_detail";
    private static final String KEY_LOGGED_IN = "loggedIn";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String id_pelanggan) {
        editor.putString(KEY_ID_PELANGGAN, id_pelanggan);
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.commit();
    }
    public void saveUsername(String username, String password){
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }
    public void createMenuSession(String id_Menu){
        editor.putString(KEY_ID_MENU, id_Menu);
        editor.commit();
    }

    public void createDetailId(String id_detail){
        editor.putString(KEY_ID_DETAIL, id_detail);
        editor.commit();
    }

    public void logout() {
        editor.remove(KEY_ID_PELANGGAN);
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_PASSWORD);
        editor.putBoolean(KEY_LOGGED_IN, false);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, true);
    }

    public String getId() {
        return sharedPreferences.getString(KEY_ID_PELANGGAN, null);
    }
    public String getIdMenu(){
        return sharedPreferences.getString(KEY_ID_MENU, null);
    }
    public String getUsername(){
        return sharedPreferences.getString(KEY_USERNAME,null);
    }
    public String getPassword(){
        return sharedPreferences.getString(KEY_PASSWORD,null);
    }
    public String getIdDetail(){
        return  sharedPreferences.getString(KEY_ID_DETAIL, null);
    }
}

