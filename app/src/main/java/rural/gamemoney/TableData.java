package rural.gamemoney;

import android.provider.BaseColumns;

public class TableData {

    public TableData() {
    }

    public static abstract class TableInfo implements BaseColumns {
        public static final String COL_TRANS_ID = "Id";
        public static final String COL_TRANS_AMOUNT = "Amount";
        public static final String COL_ISINCOME = "IsIncome";
        public static final String COL_USERNAME = "Username";
        public static final String COL_MONEY_CURRENT = "CorrentMoney";
        public static final String DATABASE_NAME = "GameMoneyDB";
        public static final String TABLE_NAME = "GameMoneyTable";
    }
}
