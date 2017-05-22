package com.hwoodall.android.simpletodolist;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ListItem {

    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private boolean mSolved;

    public ListItem(){
        this(UUID.randomUUID());
    }

    public ListItem(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
