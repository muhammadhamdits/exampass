package id.hdnia.exampassdosen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import id.hdnia.exampassdosen.apihelper.BaseApiService;
import id.hdnia.exampassdosen.apihelper.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassDetailActivity extends AppCompatActivity {
    TextView mataKuliah, kelas, ruangan, tanggal, waktu;
    RecyclerView rvClassDetail;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    SharedPrefManager sharedPrefManager;
    BaseApiService baseApiService;
    FloatingActionButton buttonScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.getSpToken().equals("")){
            logout();
        } else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_class_detail);

            try {
                Intent intent = getIntent();
                JSONObject detailKelas = new JSONObject(Objects.requireNonNull(intent.getStringExtra("data")));

                init(detailKelas);
                getClassDetail(sharedPrefManager.getSpToken(), detailKelas.getInt("kelas_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    public void logout(){
        Toast.makeText(ClassDetailActivity.this, "Your session expired. You are now logged out.", Toast.LENGTH_SHORT).show();
        sharedPrefManager.saveSPToken("");
        Intent intent = new Intent(ClassDetailActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    public void init(JSONObject data){
        mataKuliah = findViewById(R.id.matkul);
        kelas = findViewById(R.id.kelas);
        ruangan = findViewById(R.id.ruangan);
        tanggal = findViewById(R.id.tanggal);
        waktu = findViewById(R.id.waktu);
        buttonScan = findViewById(R.id.button_scan);
        baseApiService = UtilsApi.getAPIService();

        try {
            mataKuliah.setText(data.getString("nama_mk"));
            kelas.setText(data.getString("nama_kelas"));
            ruangan.setText(data.getString("ruang_ujian"));
            tanggal.setText(data.getString("tanggal_ujian"));
            waktu.setText(data.getString("mulai")+" - "+data.getString("selesai"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ClassDetailActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getClassDetail(String spToken, int kelas_id) {
        baseApiService.getClassDetail(spToken, String.valueOf(kelas_id)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        rvClassDetail = findViewById(R.id.rv_list_detail);
                        rvClassDetail.setHasFixedSize(true);
                        rvClassDetail.setLayoutManager(layoutManager);

                        adapter = new ClassDetailAdapter(jsonObject.getJSONArray("mahasiswas"), new ClassDetailAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(JSONObject item) {
                                try {
                                    Toast.makeText(ClassDetailActivity.this, item.getString("nama"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        rvClassDetail.setAdapter(adapter);
                    } catch (IOException | JSONException e) {
                        logout();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ClassDetailActivity.this, "Failed to get data :(", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ClassDetailActivity.this, "Connection error :(", Toast.LENGTH_LONG).show();
            }
        });
    }
}
