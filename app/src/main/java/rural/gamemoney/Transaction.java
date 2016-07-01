package rural.gamemoney;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Transaction extends View {
    private boolean isIncome;
    private int id;
    private int moneyAmount;
    private Context context;
    private LinearLayout linContentBox;
    private LinearLayout linDivider;
    private LinearLayout space;
    private LinearLayout hisContent;
    private Typeface fontId;
    private Typeface fontMoney;
    private TextView textId;
    private TextView textMoneyAmount;
    private int colorFill;
    private int dp55 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());
    private int dp10 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
    private int dp5 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    private String incomeType;

    public Transaction(int id, int moneyAmount, boolean isIncome, Context context) {
        super(context);
        this.id = id;
        this.moneyAmount = moneyAmount;
        this.context = context;
        this.isIncome = isIncome;
        fontId = Typeface.createFromAsset(context.getAssets(), "GOTHICB.TTF");
        fontMoney = Typeface.createFromAsset(context.getAssets(), "ticketfont2.ttf");

        if (this.isIncome) {
            colorFill = Color.parseColor("#92ca70");
            incomeType = "+";
        } else {
            colorFill = Color.parseColor("#f2797b");
            incomeType = "-";
        }

    }

    public LinearLayout ContentMaker() {
        this.linContentBox = new LinearLayout(context);
        this.linDivider = new LinearLayout(context);
        this.textId = new TextView(context);
        this.textMoneyAmount = new TextView(context);

        this.linContentBox.setOrientation(LinearLayout.HORIZONTAL);
        this.linContentBox.setMinimumHeight(dp55);
        this.linContentBox.setBackgroundColor(colorFill);
        this.linContentBox.setPadding(0, dp5, 0, dp5);
        this.linContentBox.setGravity(Gravity.CENTER);

        this.textId.setLayoutParams(new ActionBar.LayoutParams(dp55, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.textId.setText(Integer.toString(id));
        this.textId.setTextSize(dp10);
        this.textId.setBackgroundColor(Color.TRANSPARENT);
        this.textId.setTextColor(Color.WHITE);
        this.textId.setAlpha((float) 0.26);
        this.textId.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.textId.setTypeface(fontId);

        this.textMoneyAmount.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.textMoneyAmount.setText(incomeType + Integer.toString(moneyAmount) + " $");
        this.textMoneyAmount.setTextSize(dp10);
        this.textMoneyAmount.setBackgroundColor(Color.TRANSPARENT);
        this.textMoneyAmount.setTextColor(Color.WHITE);
        this.textMoneyAmount.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.textMoneyAmount.setTypeface(fontMoney);

        this.linDivider.setLayoutParams(new LinearLayoutCompat.LayoutParams(dp5, ViewGroup.LayoutParams.FILL_PARENT));
        this.linDivider.setBackgroundColor(Color.WHITE);
        this.linDivider.setAlpha((float) 0.26);

        this.space = new LinearLayout(context);
        this.space.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, dp10));
        this.space.setBackgroundColor(Color.TRANSPARENT);

        this.linContentBox.addView(textId);
        this.linContentBox.addView(linDivider);
        this.linContentBox.addView(textMoneyAmount);

        this.hisContent = new LinearLayout(context);
        this.hisContent.setOrientation(LinearLayout.VERTICAL);


        hisContent.addView(linContentBox);
        hisContent.addView(space);

        return hisContent;
    }
}
