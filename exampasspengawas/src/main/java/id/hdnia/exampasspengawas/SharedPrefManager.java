package id.hdnia.exampasspengawas;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_PENGAWAS_APP = "spPengawasApp";
    public static final String SP_TOKEN = "";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_PENGAWAS_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPToken(String token){
        spEditor.putString(SP_TOKEN, token);
        spEditor.commit();
    }

    public String getSpToken(){
        return sp.getString(SP_TOKEN, "");
    }
}
