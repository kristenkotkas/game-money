package rural.gamemoney;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import rural.gamemoney.database.DatabaseOperations;
import rural.gamemoney.view.HeaderText;
import rural.gamemoney.view.TransactionView;

import java.util.ArrayList;
import java.util.List;

public class MainScreenActivity extends Activity {
    private static TableLayout tableContent;
    private static TextView textMoneyCurrent;
    private Typeface fontMoney;
    public static int textCurrentSize;
    private Button btnPayment;
    private Intent actPayment;
    private Intent actStart;
    private Intent actMaker;
    private Intent actStat;
    private static Context context;
    public static int id = 1;
    private int btnTextSize;
    public static int screenHeight;
    public static int screenWidth;
    private Button toStat;
    private HeaderText headerText;
    private static List<View> transViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        screenHeight = MainActivity.screenHeight;
        screenWidth = MainActivity.screenWidth;
        transViewList = new ArrayList<>();

        btnTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        textCurrentSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        context = this;
        tableContent = (TableLayout) findViewById(R.id.MainScrollTable);
        textMoneyCurrent = (TextView) findViewById(R.id.moneyCurrent);
        btnPayment = (Button) findViewById(R.id.btnPayment);
        toStat = (Button) findViewById(R.id.tostatmanu);
        headerText = (HeaderText) findViewById(R.id.tvtransferHello);
        fontMoney = Typeface.createFromAsset(this.getAssets(), "GOTHIC.TTF");
        actPayment = new Intent("rural.gamemoney.Payment");
        actStart = new Intent("rural.gamemoney.StartActivity");
        actStart = new Intent(context, MainActivity.class);
        actMaker = new Intent("rural.gamemoney.GameMakerBanker");
        actStat = new Intent("rural.gamemoney.StatsActivity");
        decorateButton(btnPayment);
        decorateButton(toStat);

        headerText.setTextSize(MainActivity.screenWidth/30);

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
                //startActivity(actStat);
            }
        });
        readData();

        toStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(actStat);
            }
        });

        tableContent.setPadding(MainActivity.screenWidth /20, 0, MainActivity.screenWidth /20, 0);
        headerText.setText("hello " + Player.getUserName());
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

        Cursor usersCursor = DB.getUsers(DB);
        usersCursor.moveToFirst();

        do {
            if (!(usersCursor.getCount() == 0) &&
                    !usersCursor.getString(0).equals("player") &&
                    !usersCursor.getString(0).equals("banker")) {
                String user = usersCursor.getString(0);
                if(!Player.usersList.contains(user)) {
                    Player.usersList.add(user);
                }
            }
        }
        while (usersCursor.moveToNext());

        if (usersCursor.getCount() != 0) {
            if (Player.getGamer().equals("player")) {
                usersCursor.moveToLast();
                Player.setUserName(usersCursor.getString(0));
            }
            else {
                Player.setUserName("banker");
            }
        }

        textMoneyCurrent.setText(TransactionView.addSeparators(new StringBuilder(Integer.toString(MoneyCurrent.getMoneyCurrent()))));

        cursor.close();
        usersCursor.close();
        DB.close();
    }

    @Override
    public void onBackPressed() {
        startActivity(actStart);
        //finish();
    }
}