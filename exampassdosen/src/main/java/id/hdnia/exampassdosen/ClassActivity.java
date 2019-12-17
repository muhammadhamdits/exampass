package id.hdnia.exampassdosen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.hdnia.exampassdosen.apihelper.BaseApiService;
import id.hdnia.exampassdosen.apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassActivity extends AppCompatActivity {
    BaseApiService baseApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.getSpToken().equals("")){
            logout();
        } else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_class);

            baseApiService = UtilsApi.getAPIService();
            String token = sharedPrefManager.getSpToken();

            getClass(token);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId()==R.id.logout){
            sharedPrefManager.saveSPToken("");
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getClass(String token){
        baseApiService.getClass(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Toast.makeText(ClassActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
                    } catch (JSONException | IOException e) {
                        logout();
                        e.printStackTrace();
                    }
                } else{
                    Toast.makeText(ClassActivity.this, "Failed to get data :(", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ClassActivity.this, "Error with connection :(", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void logout(){
        Toast.makeText(ClassActivity.this, "Your session expired. You are now logged out.", Toast.LENGTH_SHORT).show();
        sharedPrefManager.saveSPToken("");
        Intent intent = new Intent(ClassActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
