package example.radmila.cdninjaqrcodescanner.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
public abstract class AbstractRepository<T> implements Repository<T> {
    // Database fields
    private SQLiteDatabase database;
    private NinjaSQLiteHelper dbHelper;

    public AbstractRepository(Context context) {
        dbHelper = new NinjaSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public NinjaSQLiteHelper getDbHelper() {
        return dbHelper;
    }
}