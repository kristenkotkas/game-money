package rural.gamemoney.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;
import rural.gamemoney.MainActivity;

public class HeaderText extends TextView {

    public HeaderText(Context context) {
        super(context);
        init();
    }

    public HeaderText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        setBackgroundColor(Color.TRANSPARENT);
        setTextSize(MainActivity.screenWidth/24);
        setTypeface(MainActivity.font);
        setTextColor(Color.parseColor("#afafaf"));
    }
}
