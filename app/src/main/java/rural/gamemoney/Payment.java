package rural.gamemoney;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Payment extends Activity{
    private PaymentButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnErase;
    private Typeface fontText;
    private TextView totalToPay;
    private String username;
    private Button btnPay;
    private static Button btnGet;
    private int getAmount;
    private TableLayout usersTable;
    private LinearLayout drawer;
    StringBuilder transactionAmountText = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        final Context context = this;
        fontText = Typeface.createFromAsset(this.getAssets(), "ticketfont2.ttf");

        btn1 = (PaymentButton) findViewById(R.id.btn1);
        btn2 = (PaymentButton) findViewById(R.id.btn2);
        btn3 = (PaymentButton) findViewById(R.id.btn3);
        btn4 = (PaymentButton) findViewById(R.id.btn4);
        btn5 = (PaymentButton) findViewById(R.id.btn5);
        btn6 = (PaymentButton) findViewById(R.id.btn6);
        btn7 = (PaymentButton) findViewById(R.id.btn7);
        btn8 = (PaymentButton) findViewById(R.id.btn8);
        btn9 = (PaymentButton) findViewById(R.id.btn9);
        btn0 = (PaymentButton) findViewById(R.id.btn0);
        btnErase = (PaymentButton) findViewById(R.id.btnErase);

        totalToPay = (TextView) findViewById(R.id.totalToPay);
        btnPay = (Button) findViewById(R.id.btnPayPayment);
        btnGet = (Button) findViewById(R.id.btnGetPayment);
        usersTable = (TableLayout) findViewById(R.id.usersList);
        drawer = (LinearLayout) findViewById(R.id.drawer);

        btnPay.setEnabled(false);

        addPlayers("Peggie\n" +
                "Claude\n" +
                "Bryce\n" +
                "Patric\n" +
                "Peggie\n" +
                "Claude\n" +
                "Bryce\n" +
                "Patric\n" +
                "Robert");

        decorateButton(btnPay);
        decorateButton(btnGet);

        btnGet.setMinWidth((int)(MainActivity.screenWidth /3));
        btnPay.setMinWidth((int)(MainActivity.screenWidth /3));
        btnGet.setMaxWidth((int)(MainActivity.screenWidth /3));
        btnPay.setMaxWidth((int)(MainActivity.screenWidth /3));

        PaymentButton[] btnArray = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnErase};

        totalToPay.setTypeface(fontText);
        totalToPay.setTextColor(Color.parseColor("#afafaf"));
        totalToPay.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/19));
        totalToPay.setMinWidth((int)(MainActivity.screenWidth /1.5));
        totalToPay.setMaxWidth((int)(MainActivity.screenWidth /1.5));
        totalToPay.setMaxHeight((int)(MainActivity.screenHeight/10));
        totalToPay.setMinHeight((int)(MainActivity.screenHeight/10));

        for (PaymentButton btn: btnArray) {
            decorateButton(btn);
            btn.setCurrentActivity(this);
        }

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transactionAmountText.length() != 0) {
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        System.out.println("jõudsin tegema");
                        BitMatrix bitMatrix = multiFormatWriter.encode(username + ":" + transactionAmountText, BarcodeFormat.QR_CODE, 250, 250);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                        Intent intent = new Intent(context, QrActivity.class);
                        intent.putExtra("pic", bitmap);
                        context.startActivity(intent);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                makeHistory(false);
                onBackPressed();
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan();
            }
        });
    }

    public void makeHistory(boolean isIncome) {
        if (transactionAmountText.length() != 0) {
            if (isIncome) {
                MoneyCurrent.setMoneyCurrent(MoneyCurrent.getMoneyCurrent() + getAmount);
                MainActivity.updateCurrent(getAmount, true, username);
            }
            else {
                int transferAmount = Integer.parseInt(transactionAmountText.toString());
                MoneyCurrent.setMoneyCurrent(MoneyCurrent.getMoneyCurrent() - transferAmount);
                MainActivity.updateCurrent(transferAmount, false, username);
            }
            transactionAmountText.delete(0, transactionAmountText.length());
            totalToPay.setText(TransactionView.addSeparators(new StringBuilder(transactionAmountText)));
            System.out.println("transaction completed");
        }
    }

    public void transactionAmount(String nr) {
        if (nr.equals("<-") && transactionAmountText.length() != 0) {
            transactionAmountText.deleteCharAt(transactionAmountText.length()-1);
        } else {

            if (!nr.equals("<-") && transactionAmountText.length() < 9 ) {
                if(!(transactionAmountText.length() == 0 && nr.equals("0"))) {
                    transactionAmountText.append(nr);
                }
            }
        }
        totalToPay.setText(TransactionView.addSeparators(new StringBuilder(transactionAmountText)));
    }

    public void decorateButton(Button button) {
        button.setTypeface(fontText);
        button.setTextColor(Color.parseColor("#afafaf"));
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/17));
        button.setMinWidth((int)(MainActivity.screenHeight/9));
        button.setMinHeight((int)(MainActivity.screenHeight/9));
        button.setMaxWidth((int)(MainActivity.screenHeight/9));
        button.setMaxHeight((int)(MainActivity.screenHeight/9));
    }

    public void addPlayers(String users) {
        String[] arrayUsers;
        arrayUsers = users.split("\n");

        for (String user: arrayUsers) {
            final Button button = new Button(this);
            Space space = new Space(this);

            space.setMinimumHeight(MainActivity.screenHeight/20);

            button.setText(user);
            button.setTypeface(fontText);
            button.setGravity(Gravity.CENTER);
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/17));
            button.setTextColor(Color.parseColor("#afafaf"));
            button.setBackgroundColor(Color.WHITE);

            button.setOnClickListener(new View.OnClickListener() {
                boolean isActive = false;
                @Override
                public void onClick(View view) {
                    if (!isActive) {
                        button.setBackgroundColor(Color.parseColor("#92CA70"));
                        button.setTextColor(Color.WHITE);
                        btnPay.setEnabled(true);
                        isActive = true;
                    } else {
                        button.setBackgroundColor(Color.WHITE);
                        button.setTextColor(Color.parseColor("#afafaf"));
                        btnPay.setEnabled(false);
                        isActive = false;
                    }
                    if (isActive) {
                        System.out.println(button.getText());
                    }
                }
            });

            usersTable.addView(button);
            usersTable.addView(space);
        }
    }

    public void scan() {
        IntentIntegrator integratorTicket = new IntentIntegrator(this);
        integratorTicket.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integratorTicket.setPrompt("scan");
        integratorTicket.setCameraId(0);
        integratorTicket.setBeepEnabled(false);
        integratorTicket.setBarcodeImageEnabled(true);
        integratorTicket.setOrientationLocked(true);
        integratorTicket.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null && scanResult.getContents() != null) {
            String result = scanResult.getContents();
            Log.d("code", result);
            System.out.println(result);
            getMoney(result);
        }
    }

    public void getMoney(String result) {
        String[] info;
        info = result.split(":");
        username = info[0];
        getAmount = Integer.parseInt(info[1]);
        System.out.println("usrname: " + username);
        System.out.println("getAmount: " + getAmount);
        transactionAmountText = new StringBuilder("income");
        makeHistory(true);
        onBackPressed();
    }
}
