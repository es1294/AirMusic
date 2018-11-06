package com.example.es1294.airmusic;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class ManageUser {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "CurrentUser";

    public static final String KEY_USERID = "userId";

    public ManageUser(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void startLogin(String userId){
        editor.putString(KEY_USERID, userId);
        editor.commit();
    }

    public HashMap<String, String> getUserId(){
        HashMap<String,String> userID = new HashMap<String,String>();
        userID.put("userId", sharedPreferences.getString(KEY_USERID, null));

        return userID;
    }

}
