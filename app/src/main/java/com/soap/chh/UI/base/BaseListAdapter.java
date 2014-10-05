package com.soap.chh.ui.base;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

/**
 * Created by yc-mac on 14-10-4.
 */
public abstract class BaseListAdapter extends RecyclerView.Adapter {

    protected LayoutInflater mInflater;
    protected Cursor mCursor;
    protected Context mContext;

    public BaseListAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mInflater = LayoutInflater.from(context);
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        mCursor = newCursor;
        notifyDataSetChanged();
        return oldCursor;
    }

    /**
     * Change the underlying cursor to a new cursor. If there is an existing cursor it will be
     * closed.
     *
     * @param cursor The new cursor to be used
     */
    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    public void release() {
        if (mCursor != null) {
            mCursor.close();
        }
    }
}
