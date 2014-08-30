package com.soap.chh.ui;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.soap.chh.R;
import com.soap.chh.io.NewsHandler;
import com.soap.chh.io.RequestManager;
import com.soap.chh.io.model.News;
import com.soap.chh.model.NetToDBRequest;
import com.soap.chh.provider.ChhContract;

import org.json.JSONArray;

import java.text.SimpleDateFormat;

import static com.soap.chh.util.LogUtils.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class NewsListFragment extends Fragment {
    private static final String TAG = makeLogTag(NewsListFragment.class);

    private OnFragmentInteractionListener mListener;

    private Cursor mCursor;
    private NewsListAdapter mAdapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public NewsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Request<Boolean> request = new NetToDBRequest<Boolean>(new NewsHandler(getActivity()),
                "http://192.168.1.111:8000/news/0/30/", new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if (response) {
                    LOGD(TAG, "onResponse: Download json data from net and insert into db successfullly");

                    if (mAdapter != null) {
                        Cursor cursor = getActivity().getContentResolver().query(ChhContract.News.CONTENT_URI, null, null, null, null);
                        mAdapter.swapCursor(cursor);
                    }
                } else {
                    LOGD(TAG, "onResponse: Download json data or insert failed");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LOGD(TAG, "onErrorResponse : " + error.getMessage());
            }
        });

        RequestManager.getRequestQueue().add(request);
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
        mAdapter = new NewsListAdapter(mCursor);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    static class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

        private LayoutInflater mInflater;
        private Cursor mCursor;
        private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        static class ViewHolder extends RecyclerView.ViewHolder {

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
            }
        }

        public NewsListAdapter(Cursor cursor) {
            mCursor = cursor;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_news_list, viewGroup, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            mCursor.moveToPosition(i);
            viewHolder.title.setText(mCursor.getString(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_TITLE)));
            viewHolder.author.setText(mCursor.getString(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_AUTHOR)));
            viewHolder.time.setText(mCursor.getString(mCursor.getColumnIndex(ChhContract.NewsColumns.NEWS_TIME)));
            viewHolder.messageCount.setText(viewHolder.messageCount.getContext()
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

    }

}
