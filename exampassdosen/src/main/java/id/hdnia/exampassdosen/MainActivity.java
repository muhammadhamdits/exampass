package id.hdnia.exampassdosen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import id.hdnia.exampassdosen.apihelper.BaseApiService;
import id.hdnia.exampassdosen.apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button login;
    BaseApiService baseApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_password);
        login = findViewById(R.id.button_login);
        baseApiService = UtilsApi.getAPIService();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLogin();
            }
        });
    }

    private void requestLogin() {
        baseApiService.loginRequest(username.getText().toString(), password.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_LONG).show();
                        //                    JSONObject jsonObject = new JSONObject(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error with connection :(", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to get data :(", Toast.LENGTH_LONG).show();
            }
        });
    }
}
