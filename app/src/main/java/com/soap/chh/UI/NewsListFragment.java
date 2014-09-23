package com.soap.chh.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.soap.chh.Config;
import com.soap.chh.R;
import com.soap.chh.io.NewsHandler;
import com.soap.chh.io.RequestManager;
import com.soap.chh.model.NetToDBRequest;
import com.soap.chh.provider.ChhContract;

import static com.soap.chh.util.LogUtils.LOGD;
import static com.soap.chh.util.LogUtils.makeLogTag;


public class NewsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerView.OnScrollListener {
    private static final String TAG = makeLogTag(NewsListFragment.class);

    private Cursor mCursor;
    private NewsListAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private static final int ITEM = 0;
    private static final int FOOTER = 1;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCursor = getActivity().getContentResolver().query(ChhContract.News.CONTENT_URI, null, null, null, null);
        mAdapter = new NewsListAdapter(getActivity(), mCursor);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    /**
     * 请求网络数据
     */
    private void requestData() {
        Request<Boolean> request = new NetToDBRequest<Boolean>(new NewsHandler(getActivity()),
                String.format(Config.URL_NEWS_LIST, 1, 30), new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if (response) {
                    LOGD(TAG, "onResponse: Download json data from net and insert into db successfullly");

                    if (mAdapter != null) {
                        Cursor cursor = getActivity().getContentResolver().query(ChhContract.News.CONTENT_URI, null, null, null, null);
                        mAdapter.changeCursor(cursor);
                    }
                } else {
                    LOGD(TAG, "onResponse: Download json data or insert failed");
                }
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LOGD(TAG, "onErrorResponse : " + error.getMessage());
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        RequestManager.getRequestQueue().add(request);
    }

    @Override
    public void onScrollStateChanged(int newlState) {

    }

    @Override
    public void onScrolled(int dx, int dy) {
        int topRowVerticalPosition =
                (mRecyclerView == null || mRecyclerView.getChildCount() == 0) ? 0 : mRecyclerView.getChildAt(0).getTop();
        mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);

        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int lastPos = layoutManager.findLastVisibleItemPosition();
        if (mCursor != null && mCursor.getCount() == lastPos) {

        }
    }

    static class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

        private LayoutInflater mInflater;
        private Cursor mCursor;
        private Context mContext;

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

        public NewsListAdapter(Context context, Cursor cursor) {
            mContext = context;
            mCursor = cursor;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            switch (viewType) {
                case FOOTER:
                    return null;
                default:
                case ITEM:
                    return new ViewHolder(mInflater.inflate(R.layout.adapter_news_list, parent, false));

            }
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            mCursor.moveToPosition(position);
            viewHolder.title.setText(mCursor.getString(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_TITLE)));
            viewHolder.author.setText(mCursor.getString(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_AUTHOR)));
            viewHolder.time.setText(mCursor.getString(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_TIME)));
            viewHolder.messageCount.setText(mContext
                    .getString(R.string.adapter_news_message_count,
                            mCursor.getInt(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_MESSAGE_COUNT))));
        }

        @Override
        public int getItemCount() {
            return mCursor == null ? 0 : mCursor.getCount();
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

    }

}
