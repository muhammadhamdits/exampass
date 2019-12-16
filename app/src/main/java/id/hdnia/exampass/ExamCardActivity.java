package id.hdnia.exampass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ExamCardActivity extends AppCompatActivity {
    JSONObject data;
    Bitmap bitmap;
    ImageView qrImage;
    TextView mataKuliah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_card);

        qrImage = findViewById(R.id.qrimage);
        mataKuliah = findViewById(R.id.tv_matkul_examcard);

        Intent intent = getIntent();
        try {
            data = new JSONObject(Objects.requireNonNull(intent.getStringExtra("data")));
            String passcode = data.getString("passcode");
            String matkul = data.getString("nama_mk");
//            Toast.makeText(ExamCardActivity.this, passcode, Toast.LENGTH_LONG).show();
            QRGEncoder qrgEncoder = new QRGEncoder(passcode, null, QRGContents.Type.TEXT, 1200);
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
            mataKuliah.setText(matkul);
//            Toast.makeText(ExamCardActivity.this, data.toString(), Toast.LENGTH_LONG).show();
        } catch (JSONException | WriterException e) {
            e.printStackTrace();
        }
    }
}
