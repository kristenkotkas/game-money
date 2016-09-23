package rural.gamemoney;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TextView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import rural.gamemoney.database.DatabaseOperations;

public class GameMakerPlayer extends Activity {
    private Button scanGame;
    private Intent actMainScreen;
    private TableLayout tableLayout;
    private Typeface fontText;
    private TextView hello;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_maker_player);
        scanGame = (Button) findViewById(R.id.scanGame);
        tableLayout = (TableLayout) findViewById(R.id.playerMakerTableLay);
        hello = (TextView) findViewById(R.id.whoareyou);
        context = this;

        hello.setVisibility(View.INVISIBLE);
        actMainScreen = new Intent("rural.gamemoney.MainScreenActivity");
        fontText = Typeface.createFromAsset(this.getAssets(), "ticketfont2.ttf");

        scanGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan();
                if (scanGame.getText().toString().equals("start game")){
                    DatabaseOperations DB = new DatabaseOperations(context);
                    DB.putUsers(DB, Player.getUserName());
                    finish();
                    //TODO: tagasi muuta setMoneyCyrrent(0)
                    MoneyCurrent.setMoneyCurrent(15000);
                    Player.setGamer("player");
                    MainScreenActivity.id = 1;
                    startActivity(actMainScreen);
                }
            }
        });
        decorate(scanGame);

        hello.setTypeface(fontText);
        hello.setTextColor(Color.parseColor("#afafaf"));
        hello.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/20));
    }

    public void scan() {
        IntentIntegrator integratorTicket = new IntentIntegrator(this);
        integratorTicket.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integratorTicket.setPrompt("scan users");
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

            String[] arrayUsers;
            arrayUsers = result.split("\n");

            for (String user: arrayUsers) {
                if (Player.usersList.size() != arrayUsers.length) {
                    Player.usersList.add(user);
                }
            }
            scanGame.setText("start game");
            addPlayers();
            hello.setVisibility(View.VISIBLE);
        }
    }

    public void addPlayers() {
        DatabaseOperations DB = new DatabaseOperations(context);
        DB.putUsers(DB, "player");
        for (String user: Player.usersList) {
            final Button button = new Button(this);
            Space space = new Space(this);

            space.setMinimumHeight(MainActivity.screenHeight/30);

            button.setText(user);
            button.setTypeface(fontText);
            button.setGravity(Gravity.CENTER);
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/21));
            button.setTextColor(Color.parseColor("#afafaf"));
            button.setBackgroundColor(Color.WHITE);

            button.setOnClickListener(new View.OnClickListener() {
                //boolean isActive = false;
                @Override
                public void onClick(View view) {
                    if (button.getCurrentTextColor() == Color.parseColor("#afafaf")) {
                        for (int i = 0; i < tableLayout.getChildCount()-1; i++) {
                            if (tableLayout.getChildAt(i) instanceof Button) {
                                tableLayout.getChildAt(i).setBackgroundColor(Color.WHITE);
                                ((Button)tableLayout.getChildAt(i)).setTextColor(Color.parseColor("#afafaf"));
                                //isActive = false;
                            }
                        }
                        button.setBackgroundColor(Color.parseColor("#92CA70"));
                        button.setTextColor(Color.WHITE);
                        Player.setUserName(button.getText().toString());
                        //isActive = true;
                        System.out.println(Player.getUserName());
                    } else {
                        button.setBackgroundColor(Color.WHITE);
                        button.setTextColor(Color.parseColor("#afafaf"));
                        //isActive = false;
                    }
                }
            });
            tableLayout.addView(button);
            tableLayout.addView(space);
            //todo lisada siia database insert
            DB.putUsers(DB, user);
        }
    }

    public void decorate(Button button) {
        button.setTypeface(fontText);
        button.setTextColor(Color.parseColor("#afafaf"));
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/26));
        button.setMinimumWidth((int)(MainActivity.screenWidth /2.7));
        button.setMinimumHeight((int)(MainActivity.screenHeight/12));
    }
}
