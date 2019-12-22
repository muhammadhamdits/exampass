package id.hdnia.exampasspengawas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import id.hdnia.exampasspengawas.apihelper.BaseApiService;
import id.hdnia.exampasspengawas.apihelper.UtilsApi;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView mScannerView;
    SharedPrefManager sharedPrefManager;
    BaseApiService baseApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScannerView = new ZXingScannerView(this);
        sharedPrefManager = new SharedPrefManager(this);
        baseApiService = UtilsApi.getAPIService();
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(com.google.zxing.Result result) {
//        Log.v("TAG", result.getText()); // Prints scan results
//        Log.v("TAG", result.getBarcodeFormat().toString());
//        mScannerView.resumeCameraPreview(this);

        Intent intent = getIntent();
        String passcode = result.getText();
        String kelas_id = String.valueOf(intent.getIntExtra("kelas_id", 0));
        String token = sharedPrefManager.getSpToken();

        Intent intent1 = new Intent(ScanActivity.this, ResultActivity.class);
        intent1.putExtra("token", token);
        intent1.putExtra("passcode", passcode);
        intent1.putExtra("kelas_id", kelas_id);
        startActivity(intent1);
    }
}
