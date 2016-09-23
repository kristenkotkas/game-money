package rural.gamemoney.view;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import rural.gamemoney.MainActivity;
import rural.gamemoney.database.DatabaseOperations;

public class StatView extends View {
    private String username;
    private Context context;

    private TextView textViewUsername;
    private TextView textViewIncome;
    private TextView textViewExpense;
    private TextView textViewTotalBalance;

    private int income;
    private int expense;
    private int totalBalance;

    private Typeface font;

    public StatView(Context context, String username) {
        super(context);
        this.username = username;
        this.context = context;
        font = Typeface.createFromAsset(context.getAssets(), "ticketfont2.ttf");
        calculate();
        totalBalance = income - expense;
    }


    public void calculate() {
        DatabaseOperations DB = new DatabaseOperations(context);
        Cursor cursor = DB.getInformation(DB);
        cursor.moveToFirst();

        do {
            if (!(cursor.getCount() == 0) && cursor.getString(3).equals(username)) {
                if (Boolean.parseBoolean(cursor.getString(2))) {
                    income = income + cursor.getInt(1);
                }
                else {
                    expense = expense + cursor.getInt(1);
                }
            }
        }
        while (cursor.moveToNext());

        cursor.close();
        DB.close();
    }

    public LinearLayout createStat() {
        final LinearLayout incomeExpenseHolder = new LinearLayout(context);
        LinearLayout content = new LinearLayout(context);
        LinearLayout space = new LinearLayout(context);

        textViewUsername = new TextView(context);
        textViewIncome = new TextView(context);
        textViewExpense = new TextView(context);
        textViewTotalBalance = new TextView(context);

        //declaration on TextViews
        //setLayoutParams width, height

        decorateTextView(textViewUsername, (int)(MainActivity.screenWidth/1.2), (int)(MainActivity.screenHeight/12.5),
                Color.WHITE, Color.parseColor("#AFAFAF"), 20, username);
        decorateTextView(textViewIncome, (int)(MainActivity.screenWidth/2.4), (int)(MainActivity.screenHeight/25),
                Color.parseColor("#92CA70"), Color.WHITE, 32, "+" + Integer.toString(income) + " $");
        decorateTextView(textViewExpense, (int)(MainActivity.screenWidth/2.4), (int)(MainActivity.screenHeight/25),
                Color.parseColor("#F2797B"), Color.WHITE, 32, "-" + Integer.toString(expense) + " $");
        decorateTextView(textViewTotalBalance, (int)(MainActivity.screenWidth/2.4), (int)(MainActivity.screenHeight/25),
                Color.WHITE, Color.parseColor("#AFAFAF"), 32, Integer.toString(totalBalance) + " $");

        incomeExpenseHolder.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        incomeExpenseHolder.setOrientation(LinearLayout.HORIZONTAL);
        incomeExpenseHolder.setBackgroundColor(Color.TRANSPARENT);
        incomeExpenseHolder.addView(textViewIncome);
        incomeExpenseHolder.addView(textViewExpense);

        content.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        content.setBackgroundColor(Color.TRANSPARENT);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER);

        space.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, (int)(MainActivity.screenHeight/30)));
        space.setBackgroundColor(Color.TRANSPARENT);

        content.addView(textViewUsername);
        content.addView(incomeExpenseHolder);
        content.addView(textViewTotalBalance);
        content.addView(space);


        textViewUsername.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return content;
    }

    public void decorateTextView(TextView textView, int width, int height, int bGColor, int textColor, double textSizeDivider, String text) {
        textView.setLayoutParams(new ActionBar.LayoutParams(width, height));
        textView.setBackgroundColor(bGColor);
        textView.setTypeface(font);
        textView.setTextColor(textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/textSizeDivider));
        textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        textView.setText(text);
    }
}
