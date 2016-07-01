package rural.gamemoney;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static TableLayout tableContent;
    private static TextView textMoneyCurrent;
    private Typeface fontMoney;
    private int textCurrentSize;
    private Button btnPayment;
    private Intent actPayment;
    private static MoneyCurrent moneyCurrent;
    private static Context context;
    private static int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        tableContent = (TableLayout) findViewById(R.id.MainScrollTable);
        textMoneyCurrent = (TextView) findViewById(R.id.moneyCurrent);
        btnPayment = (Button) findViewById(R.id.btnPayment);
        fontMoney = Typeface.createFromAsset(this.getAssets(), "GOTHIC.TTF");
        textCurrentSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        actPayment = new Intent("rural.gamemoney.Payment");

        moneyCurrent = new MoneyCurrent();

        textMoneyCurrent.setText(Integer.toString(moneyCurrent.getMoneyCurrent()));
        textMoneyCurrent.setBackgroundColor(Color.TRANSPARENT);
        textMoneyCurrent.setTypeface(fontMoney);
        textMoneyCurrent.setTextSize(textCurrentSize);
        textMoneyCurrent.setTextColor(Color.WHITE);

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(actPayment);
            }
        });
    }

    public static void updateCurrent(Integer amount, boolean isIncome) {
        textMoneyCurrent.setText(Integer.toString(moneyCurrent.getMoneyCurrent()));
        addHistory(amount, isIncome);
    }

    public static void addHistory(Integer amount, boolean isIncome) {
        id++;
        tableContent.addView(new Transaction(id, amount, isIncome, context).ContentMaker(), 0);
    }
}
