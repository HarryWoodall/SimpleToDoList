package com.hwoodall.android.simpletodolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.hwoodall.android.simpletodolist.database.ItemBaseHelper;
import com.hwoodall.android.simpletodolist.database.ItemCursorWrapper;
import com.hwoodall.android.simpletodolist.database.ItemDbSchema.ItemTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBank {

    private static ItemBank sItemBank;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private ItemBank(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ItemBaseHelper(mContext).getWritableDatabase();
    }

    public static ItemBank get(Context context) {
        if (sItemBank == null) {
            sItemBank = new ItemBank(context);
        }
        return sItemBank;
    }

    public List<ListItem> getItems() {
        List<ListItem> items = new ArrayList<>();

        ItemCursorWrapper cursor = queryItems(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                items.add(cursor.getItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return items;
    }

    public ListItem getItem(UUID id) {
        ItemCursorWrapper cursor = queryItems(
                ItemTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getItem();
        } finally {
            cursor.close();
        }
    }


    private static ContentValues getContentValues(ListItem item) {
        ContentValues values = new ContentValues();
        values.put(ItemTable.Cols.UUID, item.getId().toString());
        values.put(ItemTable.Cols.TITLE, item.getTitle());
        values.put(ItemTable.Cols.DESC, item.getDescription());
        values.put(ItemTable.Cols.DATE, item.getDate().getTime());
        values.put(ItemTable.Cols.SOLVED, item.isSolved() ? 1 : 0);

        return values;
    }

    public void addItem(ListItem item) {
        ContentValues values = getContentValues(item);

        mDatabase.insert(ItemTable.NAME, null, values);
    }

    public void removeItem(ListItem item) {
        String uuidString = item.getId().toString();
        ContentValues values = getContentValues(item);

        mDatabase.delete(ItemTable.NAME, ItemTable.Cols.UUID + "= ?", new String[] { uuidString });
    }

    public void updateItem(ListItem item) {
        String uuidString = item.getId().toString();
        ContentValues values = getContentValues(item);

        mDatabase.update(ItemTable.NAME, values, ItemTable.Cols.UUID + "= ?", new String[] { uuidString });
    }

    private ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ItemTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ItemCursorWrapper(cursor);
    }
}
