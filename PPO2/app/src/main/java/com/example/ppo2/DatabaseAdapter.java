package com.example.ppo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context)
    {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open()
    {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    private Cursor getAllEntries()
    {
        String[] columns = new String[] {DatabaseHelper.ID, DatabaseHelper.COLOR,
                DatabaseHelper.TITLE, DatabaseHelper.PREPARATION, DatabaseHelper.OPA,
                DatabaseHelper.CHILL, DatabaseHelper.CYCLE, DatabaseHelper.JOPA,
                DatabaseHelper.SETCHILL};
        return  database.query(DatabaseHelper.TABLE, columns, null, null,
                null, null, null);
    }

    public List<Params> getSequences()
    {
        ArrayList<Params> sequences = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID));
                int color = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLOR));
                String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE));
                int prepare = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PREPARATION));
                int work = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.OPA));
                int chill = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CHILL));
                int cycles = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CYCLE));
                int sets = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.JOPA));
                int setChill = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SETCHILL));
                sequences.add(new Params(id, color, title, prepare, work, chill, cycles, sets, setChill));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return sequences;
    }

    public long getCount()
    {
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    public Params getSequence(int id)
    {
        Params sequence = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst())
        {
            int color = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLOR));
            String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE));
            int prepare = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PREPARATION));
            int work = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.OPA));
            int chill = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CHILL));
            int cycles = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.CYCLE));
            int sets = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.JOPA));
            int setChill = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SETCHILL));
            sequence = new Params(id, color, title, prepare, work, chill, cycles, sets, setChill);
        }
        cursor.close();
        return sequence;
    }

    public long insert(Params sequence)
    {

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLOR, sequence.color);
        cv.put(DatabaseHelper.TITLE, sequence.title);
        cv.put(DatabaseHelper.PREPARATION, sequence.prepare);
        cv.put(DatabaseHelper.OPA, sequence.work);
        cv.put(DatabaseHelper.CHILL, sequence.chill);
        cv.put(DatabaseHelper.CYCLE, sequence.cycle);
        cv.put(DatabaseHelper.JOPA, sequence.sets);
        cv.put(DatabaseHelper.SETCHILL, sequence.setChill);
        return  database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long delete(int id)
    {

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(Params sequence)
    {
        String whereClause = DatabaseHelper.ID + "=" + String.valueOf(sequence.id);
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLOR, sequence.color);
        cv.put(DatabaseHelper.TITLE, sequence.title);
        cv.put(DatabaseHelper.PREPARATION, sequence.prepare);
        cv.put(DatabaseHelper.OPA, sequence.work);
        cv.put(DatabaseHelper.CHILL, sequence.chill);
        cv.put(DatabaseHelper.CYCLE, sequence.cycle);
        cv.put(DatabaseHelper.JOPA, sequence.sets);
        cv.put(DatabaseHelper.SETCHILL, sequence.setChill);
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }

    public void clear()
    {
        dbHelper.onUpgrade(database, 0, 0);
    }
}