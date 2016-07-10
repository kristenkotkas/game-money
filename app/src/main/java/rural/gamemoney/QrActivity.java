package rural.gamemoney;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class QrActivity extends Activity {
    private ImageView iwQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        System.out.println("act started");
        iwQrCode = (ImageView) this.findViewById(R.id.imageViewQrCode);
        Bitmap bitmap = getIntent().getParcelableExtra("pic");
        iwQrCode.setImageBitmap(bitmap);
    }
}