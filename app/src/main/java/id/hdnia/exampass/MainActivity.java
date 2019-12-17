package id.hdnia.exampass;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import id.hdnia.exampass.apihelper.BaseApiService;
import id.hdnia.exampass.apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText inputUsername;
    EditText inputPassword;
    Button buttonLogin;
    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getToken().equals("")){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
//            Toast.makeText(MainActivity.this, sharedPrefManager.getToken(), Toast.LENGTH_LONG).show();
            mContext    = this;
            mApiService = UtilsApi.getAPIService();

            inputUsername   = findViewById(R.id.input_username);
            inputPassword   = findViewById(R.id.input_password);
            buttonLogin     = findViewById(R.id.button_login);
            buttonLogin.setOnClickListener(this);
        } else{
            Intent intent = new Intent(MainActivity.this, ListUjianActivity.class);
            startActivity(intent);
        }
    }

    private void requestLogin() {
        mApiService.loginRequest(inputUsername.getText().toString(), inputPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                Toast.makeText(mContext, jsonRESULTS.getString("access_token"), Toast.LENGTH_SHORT).show();
                                String token = "Bearer "+jsonRESULTS.getString("access_token");
                                sharedPrefManager.saveToken(token);
                                Intent intent = new Intent(mContext, ListUjianActivity.class);
                                startActivity(intent);
                            } catch (JSONException | IOException e){
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "Username/Password salah!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Unable to reach server :(", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_login){
            requestLogin();
        }
    }
}
