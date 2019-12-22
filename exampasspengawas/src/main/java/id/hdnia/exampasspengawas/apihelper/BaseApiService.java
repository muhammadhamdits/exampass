package id.hdnia.exampasspengawas.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("api/login/admin")
    Call<ResponseBody> loginRequest(@Field("username") String username, @Field("password") String password);

    //Melihat daftar kelas
    @POST("api/kartu-ujian/kelasadmin")
    Call<ResponseBody> getClass(@Header("Authorization") String token);

    //Melihat detail kelas
    @POST("api/kartu-ujian/mhsruang/{kelas_id}/{ruangan_id}")
    Call<ResponseBody> getClassDetail(@Header("Authorization") String token, @Path("kelas_id") String kelas_id, @Path("ruangan_id") String ruangan_id);

    //Verifikasi kartu ujian
    @FormUrlEncoded
    @POST("api/kartu-ujian/verifikasi")
    Call<ResponseBody> verify(@Header("Authorization") String token, @Field("passcode") String passcode, @Field("kelas_id") String kelas_id);
}
