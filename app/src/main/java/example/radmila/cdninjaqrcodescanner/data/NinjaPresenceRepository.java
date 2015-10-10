package example.radmila.cdninjaqrcodescanner.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NinjaPresenceRepository extends AbstractRepository<NinjaPresence> {

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String[] columns = {NinjaSQLiteHelper.COLUMN_NAME_NINJA_ID,
            NinjaSQLiteHelper.COLUMN_NAME_NINJA_NAME, NinjaSQLiteHelper.COLUMN_NAME_DATE};

    public NinjaPresenceRepository(Context context) {
        super(context);
    }

    public NinjaPresence save(NinjaPresence ninja) {
        ContentValues values = new ContentValues();
        values.put(NinjaSQLiteHelper.COLUMN_NAME_NINJA_NAME, ninja.getName());
        values.put(NinjaSQLiteHelper.COLUMN_NAME_DATE, DATE_FORMAT.format(ninja.getDate()));
        long insertId = getDatabase().insert(NinjaSQLiteHelper.TABLE_NAME, null,
                values);
        Cursor cursor = getDatabase().query(NinjaSQLiteHelper.TABLE_NAME,
                columns, NinjaSQLiteHelper.COLUMN_NAME_NINJA_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        NinjaPresence createdNinjaPresence = retrieveNinjaPresence(cursor);
        cursor.close();
        return createdNinjaPresence;
    }

    public void delete(NinjaPresence ninja) {
        int id = ninja.getId();
        Log.i(NinjaSQLiteHelper.class.getName(), "Deleting NinjaPresence with id: " + id);
        getDatabase().delete(NinjaSQLiteHelper.TABLE_NAME, NinjaSQLiteHelper.COLUMN_NAME_NINJA_ID
                + " = " + id, null);
    }

    @Override
    public void clear() {
        getDbHelper().clearTable(NinjaSQLiteHelper.TABLE_NAME);
    }

    private NinjaPresence retrieveNinjaPresence(Cursor cursor) {
        NinjaPresence ninja = new NinjaPresence();
        ninja.setId(cursor.getInt(0));
        ninja.setName(cursor.getString(1));
        try {
            ninja.setDate(DATE_FORMAT.parse(cursor.getString(2)));
        } catch (ParseException e) {
            Log.e(NinjaSQLiteHelper.class.getName(), "Parse exception when parsing NinjaPresence date.", e);
        }
        return ninja;
    }

    @Override
    public List<NinjaPresence> findAll() {
        List<NinjaPresence> ninjas = new ArrayList<NinjaPresence>();

        Cursor cursor = getDatabase().query(NinjaSQLiteHelper.TABLE_NAME,
                columns, null, null, null, null, NinjaSQLiteHelper.COLUMN_NAME_DATE + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NinjaPresence ninja = retrieveNinjaPresence(cursor);
            ninjas.add(ninja);
            cursor.moveToNext();
        }
        // close the cursor
        cursor.close();
        return ninjas;
    }
}
