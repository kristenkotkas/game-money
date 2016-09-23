package rural.gamemoney;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static String userName;
    public static List<String> usersList = new ArrayList<>();
    private static String gamer;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Player.userName = userName;
    }

    public static String getGamer() {
        return gamer;
    }

    public static void setGamer(String gamer) {
        Player.gamer = gamer;
    }
}