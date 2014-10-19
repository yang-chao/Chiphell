package com.soap.chh.ui.base;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.soap.chh.Config;
import com.soap.chh.R;
import com.soap.chh.io.JSONHandler;
import com.soap.chh.io.RequestManager;
import com.soap.chh.model.NetToDBRequest;

import static com.soap.chh.util.LogUtils.LOGD;
import static com.soap.chh.util.LogUtils.makeLogTag;

/**
 * Created by yc-mac on 14-10-4.
 */
public abstract class BaseListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = makeLogTag(BaseListFragment.class);

    private BaseListAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mFooterView;

    private int mPageIndex = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestData(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = createAdapter();
        if (mAdapter == null) {
            throw new IllegalStateException("ListAdapter should not be null");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    int lastPos = layoutManager.findLastVisibleItemPosition();
                    LOGD(TAG, "lastPos: " + lastPos);
                    LOGD(TAG, "cursor count: " + mAdapter.getCursor().getCount());
                    if (mAdapter != null && mAdapter.getCursor().getCount() - 1 == lastPos) {
                        LOGD(TAG, "加载下一页");
                        requestData(true);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (mRecyclerView == null || mRecyclerView.getChildCount() == 0) ? 0 : mRecyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);

        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_footer, null);
    }

    protected abstract BaseListAdapter createAdapter();

    protected abstract JSONHandler createJSONHandler(boolean more);

    protected abstract String getUrl();

    protected abstract Uri getUri();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.release();
        }
    }

    @Override
    public void onRefresh() {
        requestData(false);
    }

    /**
     * 请求网络数据
     */
    private void requestData(boolean more) {
        if (more) { // 加载更多
            mPageIndex++;
        } else { // 重新加载
            mPageIndex = 1;
        }

        Request<Boolean> request = new NetToDBRequest<Boolean>(createJSONHandler(more),
                String.format(getUrl(), mPageIndex, Config.PAGE_COUNT), new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if (response) {
                    LOGD(TAG, "onResponse: Download json data from net and insert into db successfullly");

                    if (mAdapter != null && isAdded()) {
                        Cursor cursor = getActivity().getContentResolver().query(getUri(), null, null, null, null);
//                        if (cursor != null) {
//                            cursor.moveToPosition((mPageIndex - 1) * Config.PAGE_COUNT);
//                        }
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

}
