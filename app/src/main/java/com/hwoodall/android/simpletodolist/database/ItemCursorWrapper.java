package com.hwoodall.android.simpletodolist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.hwoodall.android.simpletodolist.ListItem;
import com.hwoodall.android.simpletodolist.database.ItemDbSchema.ItemTable;

import java.util.Date;
import java.util.UUID;

public class ItemCursorWrapper extends CursorWrapper {

    public ItemCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public ListItem getItem() {
        String uuidString = getString(getColumnIndex(ItemTable.Cols.UUID));
        String title = getString(getColumnIndex(ItemTable.Cols.TITLE));
        String desc = getString(getColumnIndex(ItemTable.Cols.DESC));
        long date = getLong(getColumnIndex(ItemTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(ItemTable.Cols.SOLVED));

        ListItem item = new ListItem(UUID.fromString(uuidString));
        item.setTitle(title);
        item.setDescription(desc);
        item.setDate(new Date(date));
        item.setSolved(isSolved != 0);

        return item;
    }
}
