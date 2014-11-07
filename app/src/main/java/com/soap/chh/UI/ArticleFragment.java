package com.soap.chh.ui;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.soap.chh.BuildConfig;
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
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getActivity().getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
        webView.loadUrl(mUrl);

//        String s = "原文：\n" +
//                "    <a href=\"http://livesino.net/archives/7612.live\" target=\"_blank\">\n" +
//                "        http://livesino.net/archives/7612.live\n" +
//                "    </a>\n" +
//                "    <br>\n" +
//                "    <br>\n" +
//                "    <a href=\"http://livesino.net/images/farm34/-Xbox-One-Kinect--Windows-PC_839D/Microsoft-Puebla-0925-640x504.jpg\"\n" +
//                "    target=\"_blank\">\n" +
//                "        <img id=\"aimg_q6b86\" onclick=\"zoom(this, this.src, 0, 0, 1)\" class=\"zoom\"\n" +
//                "        width=\"320\" height=\"213\" src=\"http://livesino.net/images/farm34/-Xbox-One-Kinect--Windows-PC_839D/Microsoft-Puebla-0925-640x504_thumb.jpg\"\n" +
//                "        border=\"0\" alt=\"\">\n" +
//                "    </a>\n" +
//                "    <br>\n" +
//                "    <div align=\"left\">\n" +
//                "        微软今天宣布了一批 Kinect 相关的内容。对于消费者来说，微软已经推出了\n" +
//                "        <a href=\"http://www.microsoft.com/en-us/kinectforwindows/purchase/default.aspx#tab=2\"\n" +
//                "        target=\"_blank\">\n" +
//                "            <font color=\"#0066cc\">\n" +
//                "                Kinect Adapter for Windows 配件\n" +
//                "            </font>\n" +
//                "        </a>\n" +
//                "        ，主要是可以让 Windows 8/8.1 设备通过 USB3.0 连接上 Xbox One Kinect，价格 50 美元，会在 41 各国家市场内上市销售。\n" +
//                "    </div>\n" +
//                "    <a href=\"http://livesino.net/images/farm34/-Xbox-One-Kinect--Windows-PC_839D/KFW-homepage-marquee2.jpg\"\n" +
//                "    target=\"_blank\">\n" +
//                "        <img id=\"aimg_Ka18L\" onclick=\"zoom(this, this.src, 0, 0, 1)\" class=\"zoom\"\n" +
//                "        width=\"640\" height=\"244\" src=\"http://livesino.net/images/farm34/-Xbox-One-Kinect--Windows-PC_839D/KFW-homepage-marquee2_thumb.jpg\"\n" +
//                "        border=\"0\" alt=\"\">\n" +
//                "    </a>\n" +
//                "    <br>\n" +
//                "    对于开发者来说，Kinect SDK 2.0 已经正式发布，可以\n" +
//                "    <a href=\"http://go.microsoft.com/fwlink/?LinkID=403899&amp;\n" +
//                "    clcid=0x409\" target=\"_blank\">\n" +
//                "        <font color=\"#0066cc\">\n" +
//                "            免费下载\n" +
//                "        </font>\n" +
//                "    </a>\n" +
//                "    ，商业应用的开发授权无需付费。Kinect SDK 2.0 最早从去年 11 月开始的开发者预览版，到 6 月的公开预览版，再到如今整个 Kinect\n" +
//                "    SDK 2.0 已经相对完善。\n" +
//                "    <br>\n" +
//                "    在 Windows Store 应用商店，也已经有几款应用已经可以利用 Kinect for Windows 2 体感设备：\n" +
//                "    <br>\n" +
//                "    <ul>\n" +
//                "        <li>\n" +
//                "            <a href=\"http://apps.microsoft.com/windows/en-us/app/kinect-evolution/f9513fa1-9432-4a51-b19f-d25e021e276b\"\n" +
//                "            target=\"_blank\">\n" +
//                "                <font color=\"#0066cc\">\n" +
//                "                    Kinect Evolution\n" +
//                "                </font>\n" +
//                "            </a>\n" +
//                "            ：帮助开发者理解 Kinect for Windows 2 的核心技术。\n" +
//                "        </li>\n" +
//                "        <li>\n" +
//                "            <a href=\"http://apps.microsoft.com/webpdp/app/78FE234F-290A-4373-B940-84C0E028E212\"\n" +
//                "            target=\"_blank\">\n" +
//                "                <font color=\"#0066cc\">\n" +
//                "                    YAKiT\n" +
//                "                </font>\n" +
//                "            </a>\n" +
//                "            ：制作动画的娱乐应用。\n" +
//                "        </li>\n" +
//                "        <li>\n" +
//                "            <a href=\"http://apps.microsoft.com/windows/zh-cn/app/3d-builder/75F3F766-13B3-45E9-A62F-29590D5781F2\"\n" +
//                "            target=\"_blank\">\n" +
//                "                <font color=\"#0066cc\">\n" +
//                "                    3D Builder\n" +
//                "                </font>\n" +
//                "            </a>\n" +
//                "            ：通过 Kinect 扫描对象 3D 结构，并可以 3D 打印出来。\n" +
//                "            <br>\n" +
//                "        </li>\n" +
//                "    </ul>\n" +
//                "    <a href=\"http://livesino.net/images/farm34/-Xbox-One-Kinect--Windows-PC_839D/Microsoft-Puebla-0431-695x463.jpg\"\n" +
//                "    target=\"_blank\">\n" +
//                "        <img id=\"aimg_NsSWc\" onclick=\"zoom(this, this.src, 0, 0, 1)\" class=\"zoom\"\n" +
//                "        width=\"640\" height=\"426\" src=\"http://livesino.net/images/farm34/-Xbox-One-Kinect--Windows-PC_839D/Microsoft-Puebla-0431-695x463_thumb.jpg\"\n" +
//                "        border=\"0\" alt=\"\">\n" +
//                "    </a>\n" +
//                "    <br>\n" +
//                "    <a href=\"http://livesino.net/images/farm34/-Xbox-One-Kinect--Windows-PC_839D/Kinect-Care-Innovations-1162-695x458.jpg\"\n" +
//                "    target=\"_blank\">\n" +
//                "        <img id=\"aimg_SF4p2\" onclick=\"zoom(this, this.src, 0, 0, 1)\" class=\"zoom\"\n" +
//                "        width=\"640\" height=\"422\" src=\"http://livesino.net/images/farm34/-Xbox-One-Kinect--Windows-PC_839D/Kinect-Care-Innovations-1162-695x458_thumb.jpg\"\n" +
//                "        border=\"0\" alt=\"\">\n" +
//                "    </a>\n" +
//                "    <br>\n" +
//                "    微软合作伙伴也开始使用 Kinect for Windows 在教育、物理治疗领域开发了相关方案。（via\n" +
//                "    <a href=\"http://blogs.microsoft.com/blog/2014/10/22/microsoft-releases-kinect-sdk-2-0-new-adapter-kit/\"\n" +
//                "    target=\"_blank\">\n" +
//                "        <font color=\"#0066cc\">\n" +
//                "            Microsoft\n" +
//                "        </font>\n" +
//                "    </a>\n" +
//                "    ）\n" +
//                "    <br>\n" +
//                "    <a href=\"http://livesino.net/tag/developer\" target=\"_blank\">\n" +
//                "        <font color=\"#0066cc\">\n" +
//                "            Developer\n" +
//                "        </font>\n" +
//                "    </a>\n" +
//                "    <a href=\"http://livesino.net/tag/developer-tools\" target=\"_blank\">\n" +
//                "        <font color=\"#0066cc\">\n" +
//                "            Developer Tools\n" +
//                "        </font>\n" +
//                "    </a>\n" +
//                "    <a href=\"http://livesino.net/tag/hardware\" target=\"_blank\">\n" +
//                "        <font color=\"#0066cc\">\n" +
//                "            Hardware\n" +
//                "        </font>\n" +
//                "    </a>\n" +
//                "    <a href=\"http://livesino.net/tag/kinect\" target=\"_blank\">\n" +
//                "        <font color=\"#0066cc\">\n" +
//                "            Kinect\n" +
//                "        </font>\n" +
//                "    </a>\n" +
//                "    <a href=\"http://livesino.net/tag/kinect-2\" target=\"_blank\">\n" +
//                "        <font color=\"#0066cc\">\n" +
//                "            Kinect 2\n" +
//                "        </font>\n" +
//                "    </a>\n" +
//                "    <a href=\"http://livesino.net/tag/kinect-for-windows\" target=\"_blank\">\n" +
//                "        <font color=\"#0066cc\">\n" +
//                "            Kinect for Windows\n" +
//                "        </font>\n" +
//                "    </a>\n" +
//                "    <a href=\"http://livesino.net/tag/kinect-for-windows-2\" target=\"_blank\">\n" +
//                "        <font color=\"#0066cc\">\n" +
//                "            Kinect for Windows 2\n" +
//                "        </font>\n" +
//                "    </a>\n" +
//                "    <a href=\"http://livesino.net/tag/sdk\" target=\"_blank\">\n" +
//                "        <font color=\"#0066cc\">\n" +
//                "            SDK\n" +
//                "        </font>\n" +
//                "    </a>\n" +
//                "    <a href=\"http://livesino.net/tag/windows\" target=\"_blank\">\n" +
//                "        <font color=\"#0066cc\">\n" +
//                "            Windows\n" +
//                "        </font>\n" +
//                "    </a>\n" +
//                "    <a href=\"http://livesino.net/tag/xbox-one\" target=\"_blank\">\n" +
//                "        <font color=\"#0066cc\">\n" +
//                "            Xbox One\n" +
//                "        </font>\n" +
//                "    </a>\n" +
//                "    <br>";
//        webView.loadData(s, "text/html;charset=UTF-8", null);
    }

}
