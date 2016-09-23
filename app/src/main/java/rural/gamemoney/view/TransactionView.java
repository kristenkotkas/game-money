package rural.gamemoney.view;

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
import rural.gamemoney.MainActivity;

public class TransactionView extends View {
    private boolean isIncome;
    private int id;
    private int moneyAmount;
    private String userNameText;
    private Context context;
    private int correntMoneyInt;
    private LinearLayout linContentBox;
    private LinearLayout linDivider;
    private LinearLayout space;
    private LinearLayout hisContent;
    private LinearLayout infoContent;
    private TextView username;
    private Typeface fontId;
    private Typeface fontMoney;
    private TextView textId;
    private TextView textMoneyAmount;
    private int colorFill;
    private int dp55 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());
    private int dp10 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
    private int dp5 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    private String incomeType;
    private TextView currentMoney;

    public TransactionView(int id, int moneyAmount, boolean isIncome, String userNameText, int currentMoney, Context context) {
        super(context);
        this.id = id;
        this.moneyAmount = moneyAmount;
        this.context = context;
        this.isIncome = isIncome;
        this.userNameText = userNameText;
        this.correntMoneyInt = currentMoney;
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
        this.currentMoney = new TextView(context);
        this.infoContent = new LinearLayout(context);
        this.username = new TextView(context);

        StringBuilder moneyCurrent = new StringBuilder(Integer.toString(correntMoneyInt));

        this.username.setText(userNameText);
        this.username.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.username.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/35));
        this.username.setBackgroundColor(Color.WHITE);
        this.username.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        this.username.setPadding(dp10, 0, dp10, (int)(MainActivity.screenHeight/640));
        this.username.setTypeface(fontMoney);
        this.username.setTextColor(Color.parseColor("#afafaf"));

        this.linContentBox.setOrientation(LinearLayout.HORIZONTAL);
        this.linContentBox.setMinimumHeight((int)(MainActivity.screenHeight/9.3));
        this.linContentBox.setBackgroundColor(colorFill);
        this.linContentBox.setPadding(0, dp5, 0, dp5);
        this.linContentBox.setGravity(Gravity.CENTER);

        this.textId.setLayoutParams(new ActionBar.LayoutParams((int)(MainActivity.screenHeight/9.3), ViewGroup.LayoutParams.WRAP_CONTENT));
        this.textId.setText(Integer.toString(id));
        this.textId.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/18));
        this.textId.setBackgroundColor(Color.TRANSPARENT);
        this.textId.setTextColor(Color.WHITE);
        this.textId.setAlpha((float) 0.26);
        this.textId.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.textId.setTypeface(fontId);

        this.textMoneyAmount.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.textMoneyAmount.setText(incomeType + addSeparators(new StringBuilder(Integer.toString(moneyAmount))) + " $");
        this.textMoneyAmount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/22));
        this.textMoneyAmount.setBackgroundColor(Color.TRANSPARENT);
        this.textMoneyAmount.setTextColor(Color.WHITE);
        this.textMoneyAmount.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.textMoneyAmount.setTypeface(fontMoney);
        this.textMoneyAmount.setGravity(Gravity.RIGHT);

        this.currentMoney.setText(addSeparators(moneyCurrent) + " $");
        this.currentMoney.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/38));
        this.currentMoney.setBackgroundColor(Color.TRANSPARENT);
        this.currentMoney.setTextColor(Color.WHITE);
        this.currentMoney.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.currentMoney.setTypeface(fontMoney);

        this.infoContent.setMinimumHeight(dp55);
        this.infoContent.setOrientation(LinearLayout.VERTICAL);
        this.infoContent.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.infoContent.setBackgroundColor(Color.TRANSPARENT);
        this.linContentBox.setGravity(Gravity.CENTER);

        this.linDivider.setLayoutParams(new LinearLayoutCompat.LayoutParams((int)(MainActivity.screenHeight/80), ViewGroup.LayoutParams.FILL_PARENT));
        this.linDivider.setBackgroundColor(Color.WHITE);
        this.linDivider.setAlpha((float) 0.26);

        this.space = new LinearLayout(context);
        this.space.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, (int)(MainActivity.screenHeight/70)));
        this.space.setBackgroundColor(Color.TRANSPARENT);

        this.infoContent.addView(textMoneyAmount);
        this.infoContent.addView(currentMoney);
        this.linContentBox.addView(textId);
        this.linContentBox.addView(linDivider);
        this.linContentBox.addView(infoContent);

        this.hisContent = new LinearLayout(context);
        this.hisContent.setOrientation(LinearLayout.VERTICAL);
        this.hisContent.setGravity(Gravity.RIGHT);

        hisContent.addView(linContentBox);
        hisContent.addView(username);
        hisContent.addView(space);

        return hisContent;
    }

    public static String addSeparators(StringBuilder string) {
        if (string.length() >= 4 && string.length() <= 6) {
            string.insert(string.length()-3, ",");
            return string.toString();
        }

        if (string.length() >= 7 && string.length() <= 9) {
            string.insert(string.length()-6, ",");
            string.insert(string.length()-3, ",");
            return string.toString();
        }

        if (string.length() >= 10 && string.length() <= 12) {
            string.insert(string.length()-9, ",");
            string.insert(string.length()-6, ",");
            string.insert(string.length()-3, ",");
            return string.toString();
        }

        else
            return string.toString();
    }
}
