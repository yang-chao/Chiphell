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
import android.widget.TextView;

import com.soap.chh.Config;
import com.soap.chh.R;
import com.soap.chh.io.JSONHandler;
import com.soap.chh.io.NewsHandler;
import com.soap.chh.provider.ChhContract;
import com.soap.chh.ui.base.BaseListAdapter;
import com.soap.chh.ui.base.BaseListFragment;

import static com.soap.chh.util.LogUtils.makeLogTag;


public class NewsListFragment extends BaseListFragment {
    private static final String TAG = makeLogTag(NewsListFragment.class);

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsListFragment.
     */
    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseListAdapter createAdapter() {
        Cursor cursor = getActivity().getContentResolver().query(getUri(), null, null, null, null);
        return new NewsAdapter(getActivity(), cursor);
    }

    @Override
    protected JSONHandler createJSONHandler(boolean more) {
        return new NewsHandler(getActivity(), more);
    }

    @Override
    protected String getUrl() {
        return Config.URL_NEWS_LIST;
    }

    @Override
    protected Uri getUri() {
        return ChhContract.News.CONTENT_URI;
    }


    private static class NewsAdapter extends BaseListAdapter {

        public NewsAdapter(Context context, Cursor cursor) {
            super(context, cursor);
            mContext = context;
            mCursor = cursor;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//            switch (viewType) {
//                case R.layout.adapter_footer:
//                    return null;
//                default:
//                case R.layout.adapter_news_list:
//            }
            return new ViewHolder(mInflater.inflate(R.layout.adapter_news, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            mCursor.moveToPosition(position);
            ((ViewHolder) viewHolder).title.setText(mCursor.getString(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_TITLE)));
            ((ViewHolder) viewHolder).author.setText(mCursor.getString(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_AUTHOR)));
            ((ViewHolder) viewHolder).time.setText(mCursor.getString(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_TIME)));
            ((ViewHolder) viewHolder).messageCount.setText(mContext
                    .getString(R.string.adapter_news_message_count,
                            mCursor.getInt(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_MESSAGE_COUNT))));
        }

        @Override
        public int getItemCount() {
            return mCursor == null ? 0 : mCursor.getCount();
        }


        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView title;
            private TextView author;
            private TextView time;
            private TextView messageCount;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                time = (TextView) itemView.findViewById(R.id.time);
                author = (TextView) itemView.findViewById(R.id.author);
                messageCount = (TextView) itemView.findViewById(R.id.message_count);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                mCursor.moveToPosition(getPosition());
                Intent intent = new Intent(mContext, ArticleActivity.class);
                intent.putExtra(ArticleFragment.ARG_TITLE, mCursor.getString(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_TITLE)));
                intent.putExtra(ArticleFragment.ARG_URL, mCursor.getString(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_LINK)));
                mContext.startActivity(intent);
            }
        }
    }

}
