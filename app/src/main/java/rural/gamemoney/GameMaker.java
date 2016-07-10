package rural.gamemoney;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class GameMaker extends Activity {
    private Typeface font;
    private TableLayout makerTable;
    private Button btnNewUser;
    private ScrollView scrollView;
    private Button removeUserField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_maker);
        font = Typeface.createFromAsset(getAssets(), "ticketfont2.ttf");
        btnNewUser = (Button) findViewById(R.id.makeNewUser);
        makerTable = (TableLayout) findViewById(R.id.makerTable);
        scrollView = (ScrollView) findViewById(R.id.makerScrollView);
        removeUserField = (Button) findViewById(R.id.removeUserField);

        scrollView.setPadding(0, (int) (MainActivity.screenHeight/8), 0, 0);

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makerTable.addView(addUsername());
            }
        });

        removeUserField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (makerTable.getChildCount() != 0) {
                    makerTable.removeViewAt(makerTable.getChildCount()-1);
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

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setBackgroundColor(Color.TRANSPARENT);
        container.setGravity(Gravity.CENTER);

        container.addView(editTextUsername);
        container.addView(space);

        return container;
    }
}
