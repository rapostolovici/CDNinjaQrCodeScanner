package example.radmila.cdninjaqrcodescanner.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NinjaSQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "ninjas";
    public static final String COLUMN_NAME_NINJA_ID = "id";
    public static final String COLUMN_NAME_NINJA_NAME = "name";
    public static final String COLUMN_NAME_DATE = "date";

    private static final String DATABASE_NAME = "ninjas.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + COLUMN_NAME_NINJA_ID
            + " integer primary key autoincrement, " + COLUMN_NAME_NINJA_NAME
            + " text not null, " + COLUMN_NAME_DATE + " date);";

    public NinjaSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(NinjaSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void clearTable(String table) {
        Log.i(NinjaSQLiteHelper.class.getName(),
                "Deleting all entries from table " + table);
        getWritableDatabase().execSQL("DELETE FROM " + table);
    }
}
