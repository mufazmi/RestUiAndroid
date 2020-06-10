package com.socialcodia.restapi.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.socialcodia.restapi.model.User;

public class SharedPrefHandler
{
    private static final String SHARED_PREF_NAME = "SocialCodia";
    private static SharedPrefHandler mInstance;
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPrefHandler(Context context)
    {
        this.context = context;
    }

    public static synchronized SharedPrefHandler getInstance(Context context)
    {
        if (mInstance==null)
        {
            mInstance = new SharedPrefHandler(context);
        }
        return mInstance;
    }

    public void saveUser(User user)
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.USER_EMAIL, user.getEmail());
        editor.putInt(Constants.USER_ID,user.getId());
        editor.putString(Constants.USER_NAME,user.getName());
        editor.apply();
        editor.commit();
    }

    public User getUser()
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(Constants.USER_ID,-1),
                sharedPreferences.getString(Constants.USER_NAME,null),
                sharedPreferences.getString(Constants.USER_EMAIL,null)
                );
    }

    public Boolean isLoggedIn()
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt(Constants.USER_ID,-1);
        if (id!=-1)
        {
            return true;
        }
        return false;
    }

    public void doLogout()
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
