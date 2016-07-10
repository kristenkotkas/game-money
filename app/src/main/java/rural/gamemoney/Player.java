package rural.gamemoney;

public class Player {
    private String userName;
    private int moneyGiven;
    private int moneyReceived;

    public Player(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public int getMoneyGiven() {
        return moneyGiven;
    }

    public void setMoneyGiven(int moneyGiven) {
        this.moneyGiven = moneyGiven;
    }

    public int getMoneyReceived() {
        return moneyReceived;
    }

    public void setMoneyReceived(int moneyReceived) {
        this.moneyReceived = moneyReceived;
    }
}