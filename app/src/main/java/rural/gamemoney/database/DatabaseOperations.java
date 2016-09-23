package rural.gamemoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOperations extends SQLiteOpenHelper {
    private static Context ctx;
    private static final int databaseVersion = 1;
    private String createQuery =
            "CREATE TABLE " + TableData.TableInfo.TABLE_NAME + "(" +
                    TableData.TableInfo.COL_TRANS_ID + " INTEGER," +
                    TableData.TableInfo.COL_TRANS_AMOUNT + " INTEGER," +
                    TableData.TableInfo.COL_ISINCOME + " TEXT," +
                    TableData.TableInfo.COL_USERNAME + " TEXT," +
                    TableData.TableInfo.COL_MONEY_CURRENT + " INTEGER);";
    private String createQuery2 =
            "CREATE TABLE " + TableData.TableInfo.TABLE_NAME_DATA + "(" +
                    TableData.TableInfo.COL_USER + " TEXT);";

    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, databaseVersion);
        Log.d("Database operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createQuery);
        db.execSQL(createQuery2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void putInformation(DatabaseOperations dop, int id, int amount, boolean isInocme, String userName, int moneyCurrent) {
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.COL_TRANS_ID, id);
        cv.put(TableData.TableInfo.COL_TRANS_AMOUNT, amount);
        cv.put(TableData.TableInfo.COL_ISINCOME, String.valueOf(isInocme));
        cv.put(TableData.TableInfo.COL_USERNAME, userName);
        cv.put(TableData.TableInfo.COL_MONEY_CURRENT, moneyCurrent);
        SQ.insert(TableData.TableInfo.TABLE_NAME, null, cv);
        Log.d("Database operations", "One row inserted");
    }

    public Cursor getInformation(DatabaseOperations dop) {
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {
                TableData.TableInfo.COL_TRANS_ID,
                TableData.TableInfo.COL_TRANS_AMOUNT,
                TableData.TableInfo.COL_ISINCOME,
                TableData.TableInfo.COL_USERNAME,
                TableData.TableInfo.COL_MONEY_CURRENT};
        return SQ.query(TableData.TableInfo.TABLE_NAME, columns, null, null, null, null, null);
    }

    public void putUsers(DatabaseOperations dop, String user) {
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.COL_USER, user);
        SQ.insert(TableData.TableInfo.TABLE_NAME_DATA, null, cv);
        Log.d("DOP", "User " + user +  " inserted");
    }

    public Cursor getUsers(DatabaseOperations dop) {
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = {TableData.TableInfo.COL_USER};
        return SQ.query(TableData.TableInfo.TABLE_NAME_DATA, columns, null, null, null, null, null);
    }
}
