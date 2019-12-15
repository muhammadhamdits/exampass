package id.hdnia.exampass.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BaseApiService {
    // Fungsi ini untuk memanggil API http://10.0.2.2/mahasiswa/login.php
    @FormUrlEncoded
    @POST("api/login/users")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    @POST("api/kartu-ujian/kelasmhs")
    Call<ResponseBody> getClass(@Header("Authorization") String authToken);
}
