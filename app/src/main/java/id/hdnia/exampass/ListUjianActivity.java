package id.hdnia.exampass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.hdnia.exampass.apihelper.BaseApiService;
import id.hdnia.exampass.apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUjianActivity extends AppCompatActivity{
    SharedPrefManager sharedPrefManager;
    String accessToken;
    BaseApiService mApiService;
    RecyclerView rvListUjian;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    JSONArray ujians;
    TextView titleListUjian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getToken().equals("")){
            startMainActivity();
        } else{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_ujian);

            accessToken = sharedPrefManager.getToken();
            mApiService = UtilsApi.getAPIService();
            titleListUjian = findViewById(R.id.tv_titlelistujian);
            requestListUjian();;
//            rvListUjian = findViewById(R.id.rv_list_ujian);
//            rvListUjian.setHasFixedSize(true);
//            layoutManager = new LinearLayoutManager(this);
//            rvListUjian.setLayoutManager(layoutManager);
//            mAdapter = new ListUjianAdapter(ujians);
//            Toast.makeText(ListUjianActivity.this, ujians.toString(), Toast.LENGTH_LONG).show();
//            rvListUjian.setAdapter(mAdapter);
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
        if(item.getItemId() == R.id.logout){
            sharedPrefManager.saveToken("");
            Toast.makeText(ListUjianActivity.this, "Berhasil logout", Toast.LENGTH_SHORT).show();
            startMainActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startMainActivity() {
        Intent intent = new Intent(ListUjianActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public  void requestListUjian(){
        mApiService.getClass(accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        ujians = jsonRESULTS.getJSONArray("ujians");
                        String user = ujians.getJSONObject(0).getString("nama");
//                        Toast.makeText(ListUjianActivity.this, ujians.toString(), Toast.LENGTH_SHORT).show();
                        titleListUjian.setText("List Ujian "+user);
                        rvListUjian = findViewById(R.id.rv_list_ujian);
                        rvListUjian.setHasFixedSize(true);
                        rvListUjian.setLayoutManager(layoutManager);
                        mAdapter = new ListUjianAdapter(ujians, new ListUjianAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(JSONObject item){
                                Intent intent = new Intent(ListUjianActivity.this, ExamCardActivity.class);
                                intent.putExtra("data", item.toString());
                                startActivity(intent);
                            }
                        });
//                        Toast.makeText(ListUjianActivity.this, ujians.toString(), Toast.LENGTH_LONG).show();
                        rvListUjian.setAdapter(mAdapter);
                    } catch (JSONException | IOException e){
                        Toast.makeText(ListUjianActivity.this, "Your Session Expired", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        sharedPrefManager.saveToken("");
                        startMainActivity();
                    }
                } else {
                    Toast.makeText(ListUjianActivity.this, "Failed to get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(ListUjianActivity.this, "Error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
