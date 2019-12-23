package id.hdnia.exampasspengawas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.hdnia.exampasspengawas.apihelper.BaseApiService;
import id.hdnia.exampasspengawas.apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
    BaseApiService baseApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        baseApiService = UtilsApi.getAPIService();
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        String passcode = intent.getStringExtra("passcode");
        String kelas_id = intent.getStringExtra("kelas_id");
        verify(token, passcode, kelas_id);
    }

    private void verify(String token, String passcode, String kelas_id) {
        baseApiService.verify(token, passcode, kelas_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String kode = new JSONObject(response.body().string()).getString("kode");
                        if (kode.equals("200")){
                            Toast.makeText(ResultActivity.this, "(V) Success, Card is valid", Toast.LENGTH_SHORT).show();
                        } else if (kode.equals("404")){
                            Toast.makeText(ResultActivity.this, "(X) Meh, Card is invalid!", Toast.LENGTH_SHORT).show();
                            ResultActivity.this.finish();
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ResultActivity.this, "Failed to get data :(", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ResultActivity.this, "Connection error :(", Toast.LENGTH_LONG).show();
            }
        });
    }
}
