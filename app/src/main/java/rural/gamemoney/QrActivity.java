package rural.gamemoney;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

public class QrActivity extends Activity {
    private ImageView iwQrCode;
    private static TextView qrInfo;
    private Typeface fontText;
    private static String text;
    private Intent actMainScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        qrInfo = (TextView) findViewById(R.id.qrInfo);
        fontText = Typeface.createFromAsset(this.getAssets(), "ticketfont2.ttf");
        actMainScreen = new Intent("rural.gamemoney.MainScreenActivity");

        System.out.println("act started");
        iwQrCode = (ImageView) this.findViewById(R.id.imageViewQrCode);
        Bitmap bitmap = getIntent().getParcelableExtra("pic");
        iwQrCode.setImageBitmap(bitmap);

        qrInfo.setTypeface(fontText);
        qrInfo.setTextColor(Color.parseColor("#afafaf"));
        qrInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/20));
        qrInfo.setText(text);
    }

    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getStringExtra("sender").equals("banker")) {
                startActivity(actMainScreen);
            }
            else finish();
        } catch (Exception e) {
            finish();
        }
    }

    public static void setQrInfo(String string) {
        text = string;
    }
}