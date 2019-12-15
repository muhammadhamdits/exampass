package id.hdnia.exampass;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SP_MAHASISWA_APP = "spMahasiswaApp";
    private static final String SP_TOKEN = null;

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_MAHASISWA_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveToken(String value){
        spEditor.putString(SP_TOKEN, value);
        spEditor.commit();
    }

    public String getToken(){
        return sp.getString(SP_TOKEN, "");
    }
}
