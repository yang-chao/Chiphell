package com.soap.chh.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.soap.chh.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArticleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ArticleFragment extends Fragment {

    static final String ARG_TITLE = "title";
    static final String ARG_URL = "url";

    private String mTitle;
    private String mUrl;

    public static ArticleFragment newInstance(String title, String url) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            mUrl = getArguments().getString(ARG_URL);
        }
        getActivity().getActionBar().setTitle(mTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView = (WebView) view.findViewById(R.id.webview);
//        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        webView.loadUrl(mUrl);

        String s = "原文：\n" +
                " <a href=\"http://www.cnbeta.com/articles/338033.htm\" target=\"_blank\">\n" +
                "  http://www.cnbeta.com/articles/338033.htm\n" +
                " </a>\n" +
                " <br/>\n" +
                " <br/>\n" +
                " <br/>\n" +
                " 经过数月的公测，日前在苹果总部召开的iPad新品发布会上苹果终于公布了正式版OS X Yosemite。苹果表示消费者可以免费通过Mac App Store下载Yosemite，\n" +
                " <strong>\n" +
                "  而根据AddThis最新公布的数据在开放下载首日Yosemite下载量在OS X的系统占比就达到了2%，相比较2013年10月正式发布的OS X 10.9 Maverick的1.2%要快很多。\n" +
                " </strong>\n" +
                " <br/>\n" +
                " <div align=\"center\">\n" +
                "  <img alt=\"\" border=\"0\" class=\"zoom\" id=\"aimg_ZHn8V\" onclick=\"zoom(this, this.src, 0, 0, 1)\" onload=\"thumbImg(this)\" onmouseover=\"img_onmouseoverfunc(this)\" src=\"http://static.cnbetacdn.com/newsimg/2014/1018/79_1413613077.jpg_600x600.jpg\"/>\n" +
                " </div>\n" +
                " <div align=\"left\">\n" +
                "  如此快的升级自然离不开Yosemite装备的多项新特性，在前代Mavericks系统的基础上带来了非常显著的性能提升和体用优化，此外系统同时也加强了同iOS 8设备（包括\n" +
                "  <a href=\"http://aos.prf.hn/click/camref:100lcC/creativeref:305226\" target=\"_blank\">\n" +
                "   <font color=\"#0066cc\">\n" +
                "    iPhone\n" +
                "   </font>\n" +
                "  </a>\n" +
                "  和iPad）之间的联系沟通，推出了Handoff等诸多功能。\n" +
                " </div>\n" +
                " <div align=\"left\">\n" +
                "  Handoff让你可以在一台设备上开始某个操作，然后在另一台设备上接着刚才的进度进行。举个例子，你在上班的路上用iPhone看到了一篇报道，然后想要在办公室里用Mac接着读完；又或者你使用iPhone开始编写一封电子邮件，然后想要在Mac上把它写完。\n" +
                " </div>\n" +
                " <br/>\n" +
                " <br/>";
        webView.loadData(s, "text/html;charset=UTF-8", null);
    }

}
