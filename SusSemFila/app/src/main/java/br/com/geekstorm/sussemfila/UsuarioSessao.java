package br.com.geekstorm.sussemfila;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by josen on 16/05/2018.
 */
public class UsuarioSessao {
    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Context context;

    int PRIVATE_MODE = 0;

    private static final String PREFER_NAME = "AndroidExamplePref";

    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_CPF = "cpf";

    public static final String KEY_NOME = "nome";

    public static final String KEY_ID = "id";

    // Constructor
    public UsuarioSessao(Context context){
        this.context = context;
        pref = this.context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String cpf, String nome, int id){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing cpf in pref
        editor.putString(KEY_CPF, cpf);

        // Storing senha in pref
        editor.putString(KEY_NOME, nome);

        // Storing id in pref
        editor.putString(KEY_ID, id+"");


        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);

            return true;
        }
        return false;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user cpf
        user.put(KEY_CPF, pref.getString(KEY_CPF, null));

        // user name
        user.put(KEY_NOME, pref.getString(KEY_NOME, null));

        // user id
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        Log.i("Key id", user.get(KEY_ID));
        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}