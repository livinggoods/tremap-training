package com.expansion.lg.kimaru.training.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UtilFunctions {
    /**
     * Check if column exists in SQLite database
     * @param db
     * @param table
     * @param column
     * @return
     */
    public static boolean isColumnExists (SQLiteDatabase db, String table, String column) {
        Cursor cursor = db.rawQuery("PRAGMA table_info("+ table +")", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                if (column.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }

        return false;
    }
}
