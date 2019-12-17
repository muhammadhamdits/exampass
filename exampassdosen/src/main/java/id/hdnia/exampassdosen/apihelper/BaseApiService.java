package id.hdnia.exampassdosen.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiService {
    //Login
    @FormUrlEncoded
    @POST("api/login/dosen")
    Call<ResponseBody> loginRequest(@Field("username") String username, @Field("password") String password);
}
