package rural.gamemoney;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import rural.gamemoney.database.DatabaseOperations;

public class GameMakerBanker extends Activity {
    private Typeface font;
    private TableLayout makerTable;
    private Button btnNewUser;
    private ScrollView scrollView;
    private Button removeUserField;
    private Button makeGame;
    private Context context;
    private int nrOfPlayers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_maker_banker);
        font = Typeface.createFromAsset(getAssets(), "ticketfont2.ttf");
        btnNewUser = (Button) findViewById(R.id.makeNewUser);
        makerTable = (TableLayout) findViewById(R.id.makerTable);
        scrollView = (ScrollView) findViewById(R.id.makerScrollView);
        removeUserField = (Button) findViewById(R.id.removeUserField);
        makeGame = (Button) findViewById(R.id.makeGameBanker);
        context = this;

        scrollView.setPadding(0, (int) (MainActivity.screenHeight/8), 0, 0);

        decorateButton(btnNewUser);
        decorateButton(removeUserField);
        decorateButton(makeGame);

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nrOfPlayers++;
                makerTable.addView(addUsername());
            }
        });

        removeUserField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (makerTable.getChildCount() != 0) {
                    nrOfPlayers--;
                    makerTable.removeViewAt(makerTable.getChildCount()-1);
                }
            }
        });

        makeGame.setOnClickListener(new View.OnClickListener() {
            private StringBuilder users;
            @Override
            public void onClick(View view) {
                users = new StringBuilder();
                Player.usersList.clear();
                DatabaseOperations DB = new DatabaseOperations(context);
                DB.putUsers(DB, "banker");
                for (int i = 0; i < makerTable.getChildCount(); i++) {
                    String user = ((EditText)((LinearLayout)makerTable.getChildAt(i)).getChildAt(0)).getText().toString().trim();
                    if (user.length() !=0 && !Player.usersList.contains(user)) {
                        Player.usersList.add(user);
                        users.append(user + "\n");
                        DB.putUsers(DB, user);
                    }
                }

                System.out.println(Player.usersList.toString());

                if (Player.usersList.size() != 0) {
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(users.toString(), BarcodeFormat.QR_CODE, 350, 350);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                        Intent intent = new Intent(context, QrActivity.class);
                        intent.putExtra("pic", bitmap);
                        intent.putExtra("sender", "banker");
                        QrActivity.setQrInfo("let each player scan this");
                        Player.setGamer("banker");
                        context.startActivity(intent);
                        finish();

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public LinearLayout addUsername() {
        LinearLayout space = new LinearLayout(this);
        space.setLayoutParams(new ActionBar.LayoutParams((int) (MainActivity.screenWidth /1.5), MainActivity.screenHeight/50));
        space.setBackgroundColor(Color.TRANSPARENT);

        EditText editTextUsername = new EditText(this);
        editTextUsername.setLayoutParams(new ActionBar.LayoutParams((int) (MainActivity.screenWidth /1.3), MainActivity.screenHeight/12));
        editTextUsername.setBackgroundColor(Color.WHITE);
        editTextUsername.setTypeface(font);
        editTextUsername.setTextColor(Color.parseColor("#AFAFAF"));
        editTextUsername.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/25));
        editTextUsername.setGravity(Gravity.CENTER);
        editTextUsername.setPadding(10,0,10,0);
        editTextUsername.setHint("type player name");
        editTextUsername.setHintTextColor(Color.parseColor("#CECECE"));

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setBackgroundColor(Color.TRANSPARENT);
        container.setGravity(Gravity.CENTER);

        TextView playerCount = new TextView(this);
        playerCount.setLayoutParams(new ActionBar.LayoutParams((int) (MainActivity.screenWidth /1.3), ViewGroup.LayoutParams.WRAP_CONTENT));
        playerCount.setBackgroundColor(Color.TRANSPARENT);
        playerCount.setTypeface(font);
        playerCount.setTextColor(Color.parseColor("#AFAFAF"));
        playerCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/35));
        playerCount.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        playerCount.setPadding((int) MainActivity.screenWidth/40,10,10,0);
        playerCount.setText("player #" + Integer.toString(nrOfPlayers));

        container.addView(editTextUsername);
        container.addView(playerCount);
        container.addView(space);

        return container;
    }

    public void decorateButton(Button button) {
        button.setTypeface(font);
        button.setTextColor(Color.parseColor("#afafaf"));
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/26));
        button.setMinimumWidth((int)(MainActivity.screenWidth /2.7));
        button.setMinimumHeight((MainActivity.screenHeight/12));
    }
}
