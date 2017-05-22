package com.hwoodall.android.simpletodolist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class ToDoActivity extends SingleFragmentActivity{

    public static final String EXTRA_ITEM_ID = "com.hwoodall.android.simpletodolist.item_id";

    public static Intent newIntent(Context packageContext, UUID itemId) {
        Intent intent = new Intent(packageContext, ToDoActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, itemId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_ITEM_ID);
        return ToDoFragment.newInstance(crimeId);
    }
}
