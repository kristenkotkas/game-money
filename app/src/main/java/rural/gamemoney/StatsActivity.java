package rural.gamemoney;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import rural.gamemoney.view.StatView;

import java.util.ArrayList;

public class StatsActivity extends Activity {
    private TableLayout tableLayout;
    private ArrayList<String> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        tableLayout = (TableLayout) findViewById(R.id.statsTableLayout);
        players = new ArrayList<>(Player.usersList);
        System.out.println(players.toString());

        if (tableLayout.getChildCount() == 0) {
            for (String player: players) {
                if (!player.equals(Player.getUserName())) {
                    tableLayout.addView(new StatView(this, player).createStat());
                }
            }
        }

    }
}
