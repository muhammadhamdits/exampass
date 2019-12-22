package id.hdnia.exampasspengawas.apihelper;

public class UtilsApi {
    public static final String BASE_URL_API = "http://central.si.fti.unand.ac.id/ujian/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
