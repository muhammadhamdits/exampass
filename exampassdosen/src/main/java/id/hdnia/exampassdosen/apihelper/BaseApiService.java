package id.hdnia.exampassdosen.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {
    //Login
    @FormUrlEncoded
    @POST("api/login/dosen")
    Call<ResponseBody> loginRequest(@Field("username") String username, @Field("password") String password);

    @POST("api/kartu-ujian/kelasdosen")
    Call<ResponseBody> getClass(@Header("Authorization") String token);

    @POST("api/kartu-ujian/mhskelas/{kelas_id}")
    Call<ResponseBody> getClassDetail(@Header("Authorization") String token, @Path("kelas_id") String kelas_id);
}
