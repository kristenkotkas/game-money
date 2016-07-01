package rural.gamemoney;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Payment extends Activity implements View.OnClickListener{
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnErase;
    private Typeface fontText;
    private TextView totalToPay;
    private int btnTextSize;
    private Button btnPay;
    private Button btnGet;
    StringBuilder transactionAmountText = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        fontText = Typeface.createFromAsset(this.getAssets(), "ticketfont2.ttf");
        btnTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn0 = (Button) findViewById(R.id.btn0);
        btnErase = (Button) findViewById(R.id.btnErase);
        totalToPay = (TextView) findViewById(R.id.totalToPay);
        btnPay = (Button) findViewById(R.id.btnPayPayment);
        btnGet = (Button) findViewById(R.id.btnGetPayment);

        Button[] btnArray = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnErase};

        totalToPay.setTypeface(fontText);
        totalToPay.setTextColor(Color.parseColor("#afafaf"));
        totalToPay.setTextSize(btnTextSize);

        for (Button btn: btnArray) {
            btn.setTypeface(fontText);
            btn.setTextColor(Color.parseColor("#afafaf"));
            btn.setTextSize(btnTextSize);
            btn.setOnClickListener(this);
        }

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transactionAmountText.length() != 0) {
                    System.out.println("payment completed");
                    int toPay = Integer.parseInt(transactionAmountText.toString());
                    MoneyCurrent.setMoneyCurrent(MoneyCurrent.getMoneyCurrent()-toPay);
                    transactionAmountText.delete(0, transactionAmountText.length());
                    totalToPay.setText(transactionAmountText);
                    MainActivity.updateCurrent(toPay, false);
                }
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transactionAmountText.length() != 0) {
                    System.out.println("money receive");
                    int toGet = Integer.parseInt(transactionAmountText.toString());
                    MoneyCurrent.setMoneyCurrent(MoneyCurrent.getMoneyCurrent()+ toGet);
                    transactionAmountText.delete(0, transactionAmountText.length());
                    totalToPay.setText(transactionAmountText);
                    MainActivity.updateCurrent(toGet, true);
                }
            }
        });
    }

    public void transactionAmount(String nr) {
        if (nr.equals("-1") && transactionAmountText.length() != 0) {
            transactionAmountText.deleteCharAt(transactionAmountText.length()-1);
        } else {
            if (!nr.equals("-1")) {
                transactionAmountText.append(nr);
            }
        }
        totalToPay.setText(transactionAmountText);
    }

    @Override
    public void onClick(View view) {
        if (transactionAmountText.length() < 9) {
            switch (view.getId()) {
                case R.id.btn0:
                    if (transactionAmountText.length() != 0) {
                        transactionAmount("0");
                    }
                    break;
                case R.id.btn1:
                    transactionAmount("1");
                    break;
                case R.id.btn2:
                    transactionAmount("2");
                    break;
                case R.id.btn3:
                    transactionAmount("3");
                    break;
                case R.id.btn4:
                    transactionAmount("4");
                    break;
                case R.id.btn5:
                    transactionAmount("5");
                    break;
                case R.id.btn6:
                    transactionAmount("6");
                    break;
                case R.id.btn7:
                    transactionAmount("7");
                    break;
                case R.id.btn8:
                    transactionAmount("8");
                    break;
                case R.id.btn9:
                    transactionAmount("9");
                    break;
                default:
                    break;
            }
        }
        btnErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transactionAmount("-1");
                if (transactionAmountText.length() == 0) {
                }
            }
        });
    }
}
