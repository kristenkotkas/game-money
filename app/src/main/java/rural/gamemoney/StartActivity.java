package rural.gamemoney;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class StartActivity extends Activity {
    private ImageView btnPlayer;
    private ImageView btnBanker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnBanker = (ImageView) findViewById(R.id.imageBtnBanker);
        btnPlayer = (ImageView) findViewById(R.id.imageBtnPlayer);

        btnBanker.setMinimumHeight((int)(MainActivity.screenHeight/3));
        btnPlayer.setMinimumHeight((int)(MainActivity.screenHeight/3));
        btnBanker.setMaxHeight((int)(MainActivity.screenHeight/3));
        btnPlayer.setMaxHeight((int)(MainActivity.screenHeight/3));


    }
}
