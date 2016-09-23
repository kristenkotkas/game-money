package rural.gamemoney;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

public class AnimDemoActivity extends Activity {
    private Button btnAnim;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);

        relativeLayout = (RelativeLayout) findViewById(R.id.animRelLay);

        btnAnim = new Button(this);

        relativeLayout.addView(btnAnim);


        final TranslateAnimation translateAnimation =
                new TranslateAnimation(
                        Animation.ABSOLUTE, 0,
                        Animation.RELATIVE_TO_PARENT, 1,
                        Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE, 100);
        translateAnimation.setDuration(5000);


        btnAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAnim.startAnimation(translateAnimation);
            }
        });
    }
}
