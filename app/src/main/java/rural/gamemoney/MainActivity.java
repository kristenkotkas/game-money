package rural.gamemoney;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
    private Intent actStart;
    private Intent actMaker;
    private static Context context;
    private static int id = 1;
    private int btnTextSize;
    public static int screenHeight;
    public static int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        btnTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        textCurrentSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        context = this;
        tableContent = (TableLayout) findViewById(R.id.MainScrollTable);
        textMoneyCurrent = (TextView) findViewById(R.id.moneyCurrent);
        btnPayment = (Button) findViewById(R.id.btnPayment);
        fontMoney = Typeface.createFromAsset(this.getAssets(), "GOTHIC.TTF");
        actPayment = new Intent("rural.gamemoney.Payment");
        actStart = new Intent("rural.gamemoney.StartActivity");
        actMaker = new Intent("rural.gamemoney.GameMaker");
        decorateButton(btnPayment);

        textMoneyCurrent.setText(TransactionView.addSeparators(new StringBuilder(Integer.toString(MoneyCurrent.getMoneyCurrent()))));
        textMoneyCurrent.setBackgroundColor(Color.TRANSPARENT);
        textMoneyCurrent.setTypeface(fontMoney);
        textMoneyCurrent.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (screenWidth /9));
        textMoneyCurrent.setTextColor(Color.WHITE);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(actPayment);
                //startActivity(actStart);
                //startActivity(actMaker);
            }
        });
        readData();

        tableContent.setPadding(MainActivity.screenWidth /20, 0, MainActivity.screenWidth /20, 0);
    }

    public static void updateCurrent(int amount, boolean isIncome, String username) {
        textMoneyCurrent.setText(TransactionView.addSeparators(new StringBuilder(Integer.toString(MoneyCurrent.getMoneyCurrent()))));
        addData(amount, isIncome, username);
    }

    public static void addData(int amount, boolean isIncome, String username) {
        DatabaseOperations DB = new DatabaseOperations(context);
        DB.putInformation(DB, id, amount, isIncome, username, MoneyCurrent.getMoneyCurrent());
        addHistory(id, amount, isIncome, username, MoneyCurrent.getMoneyCurrent());
        id++;
        DB.close();
    }

    public static void addHistory(int id, int amount, boolean isIncome, String username, int currentMoney) {
        tableContent.addView(new TransactionView(id, amount, isIncome, username, currentMoney,  context).ContentMaker(), 0);
    }

    public void decorateButton(Button button) {
        button.setTypeface(fontMoney);
        button.setTextColor(Color.parseColor("#afafaf"));
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/26));
        button.setMinimumWidth((int)(MainActivity.screenWidth /2.7));
        button.setMinimumHeight((int)(MainActivity.screenHeight/12));
    }

    public void readData() {
        DatabaseOperations DB = new DatabaseOperations(context);
        Cursor cursor = DB.getInformation(DB);
        cursor.moveToFirst();

        do {
            if (!(cursor.getCount() == 0)) {
                addHistory(cursor.getInt(0), cursor.getInt(1), Boolean.parseBoolean(cursor.getString(2)), cursor.getString(3), cursor.getInt(4));
            }
        }
        while (cursor.moveToNext());

        if (cursor.getCount() != 0) {
            cursor.moveToLast();
            id = cursor.getInt(0) + 1;
            MoneyCurrent.setMoneyCurrent(cursor.getInt(4));
        }

        textMoneyCurrent.setText(TransactionView.addSeparators(new StringBuilder(Integer.toString(MoneyCurrent.getMoneyCurrent()))));

        cursor.close();
        DB.close();
    }
}