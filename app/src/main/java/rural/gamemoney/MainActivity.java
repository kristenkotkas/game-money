package rural.gamemoney;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import rural.gamemoney.database.DatabaseOperations;
import rural.gamemoney.database.TableData;

public class MainActivity extends Activity {
    private Button btnContinue;
    private Button btnNewGame;
    private Intent actGameMakerBanker;
    private Intent actGameMakerPlayer;
    private Intent actMainScreen;
    public static int screenHeight;
    public static int screenWidth;
    private static Context context;
    private boolean gameExcists = true;
    public static Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        font = Typeface.createFromAsset(this.getAssets(), "ticketfont2.ttf");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        setContentView(R.layout.activity_main);

        context = this;


        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        decorateButton(btnContinue);
        decorateButton(btnNewGame);

        actGameMakerBanker = new Intent("rural.gamemoney.GameMakerBanker");
        actGameMakerPlayer = new Intent("rural.gamemoney.GameMakerPlayer");
        actMainScreen = new Intent("rural.gamemoney.MainScreenActivity");

        DatabaseOperations DB = new DatabaseOperations(this);
        Cursor cursor = DB.getUsers(DB);
        cursor.moveToFirst();

        if (cursor.getCount() == 0) {
            gameExcists = false;
            btnContinue.setEnabled(false);
        }

        if (cursor.getCount() != 0) {
            cursor.moveToLast();
            Player.setGamer(cursor.getString(0));
        }

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gameExcists) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("New game");
                    builder.setMessage("Are you sure you want to start a new game? Doing this will delete previous game.");
                    builder.setPositiveButton("new game", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteDB();
                            btnContinue.setEnabled(false);
                            gameTypeDialog();
                        }
                    });

                    builder.setNegativeButton("cancel", null);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    gameTypeDialog();
                }


            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(actMainScreen);
            }
        });
    }

    public void gameTypeDialog() {
        AlertDialog.Builder newGameBuilder = new AlertDialog.Builder(MainActivity.this);
        newGameBuilder.setTitle("Who are you going to be?");
        newGameBuilder.setMessage("Choose, who you will be. Note that there can only be one banker.");
        newGameBuilder.setPositiveButton("banker", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(actGameMakerBanker);
            }
        });

        newGameBuilder.setNegativeButton("player", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(actGameMakerPlayer);
            }
        });

        AlertDialog alertDialog = newGameBuilder.create();
        alertDialog.show();
    }

    public void deleteDB() {
        DatabaseOperations DB = new DatabaseOperations(context);
        SQLiteDatabase database = DB.getWritableDatabase();
        database.delete(TableData.TableInfo.TABLE_NAME, null, null);
        database.delete(TableData.TableInfo.TABLE_NAME_DATA, null, null);
        DB.close();
        gameExcists = false;
    }

    @Override
    public void onBackPressed() {
        //TODO: vist sobib
    }

    public void decorateButton(Button button) {
        button.setTypeface(font);
        button.setTextColor(Color.parseColor("#afafaf"));
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(MainActivity.screenHeight/26));
        button.setMinimumWidth((int)(MainActivity.screenWidth /2.7));
        button.setMinimumHeight((int)(MainActivity.screenHeight/12));
    }
}
