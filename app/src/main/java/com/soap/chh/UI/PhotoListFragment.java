package com.soap.chh.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soap.chh.Config;
import com.soap.chh.R;
import com.soap.chh.io.JSONHandler;
import com.soap.chh.io.PhotoHandler;
import com.soap.chh.provider.ChhContract;
import com.soap.chh.ui.base.BaseListAdapter;
import com.soap.chh.ui.base.BaseListFragment;
import com.squareup.picasso.Picasso;

import static com.soap.chh.util.LogUtils.LOGD;

/**
 * Created by yc-mac on 14-10-4.
 */
public class PhotoListFragment extends BaseListFragment {

    public static PhotoListFragment newInstance() {
        PhotoListFragment fragment = new PhotoListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseListAdapter createAdapter() {
        Cursor cursor = getActivity().getContentResolver().query(getUri(), null, null, null, null);
        return new PhotoAdapter(getActivity(), cursor);
    }

    @Override
    protected JSONHandler createJSONHandler(boolean more) {
        return new PhotoHandler(getActivity(), more);
    }

    @Override
    protected String getUrl() {
        return Config.URL_PHOTO_LIST;
    }

    @Override
    protected Uri getUri() {
        return ChhContract.Photo.CONTENT_URI;
    }


    private static class PhotoAdapter extends BaseListAdapter {

        public PhotoAdapter(Context context, Cursor cursor) {
            super(context, cursor);
            mContext = context;
            mCursor = cursor;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mInflater.inflate(R.layout.adapter_photo, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            mCursor.moveToPosition(position);
            ((ViewHolder) holder).title.setText(mCursor.getString(mCursor.getColumnIndex(ChhContract.Photo.PHOTO_TITLE)));
            ((ViewHolder) holder).digest.setText(mCursor.getString(mCursor.getColumnIndex(ChhContract.Photo.PHOTO_DIGEST)));
            ((ViewHolder) holder).time.setText(mCursor.getString(mCursor.getColumnIndex(ChhContract.Photo.PHOTO_TIME)));
            Picasso.with(mContext)
                   .load(mCursor.getString(mCursor.getColumnIndex(ChhContract.Photo.PHOTO_IMAGE)))
                   .into(((ViewHolder) holder).image);
        }

        @Override
        public int getItemCount() {
            return mCursor == null ? 0 : mCursor.getCount();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

            private TextView title;
            private TextView digest;
            private TextView time;
            private ImageView image;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                time = (TextView) itemView.findViewById(R.id.time);
                digest = (TextView) itemView.findViewById(R.id.digest);
                image = (ImageView) itemView.findViewById(R.id.image);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                mCursor.moveToPosition(getPosition());
                Intent intent = new Intent(mContext, ArticleActivity.class);
                intent.putExtra(ArticleFragment.ARG_TITLE, mCursor.getString(mCursor.getColumnIndex(ChhContract.PhotoColumns.PHOTO_TITLE)));
                intent.putExtra(ArticleFragment.ARG_URL, mCursor.getString(mCursor.getColumnIndex(ChhContract.PhotoColumns.PHOTO_LINK)));
                mContext.startActivity(intent);
            }
        }
    }
}
