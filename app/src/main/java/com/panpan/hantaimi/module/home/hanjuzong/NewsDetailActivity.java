package com.panpan.hantaimi.module.home.hanjuzong;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.panpan.hantaimi.base.RxBaseActivity;
import com.panpan.hantaimi.biz.NewsdetailBiz;
import com.panpan.hantaimi.entity.hanjuzong.NewsdetailInfo;
import com.panpan.hantaimi.network.RetrofitHelper;
import com.panpan.hantaimi.utils.CommonException;
import com.panpan.hantaimi.utils.ConstantUtil;
import com.panpan.hantaimi.utils.ShareUtil;
import com.panpan.hantanmi.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * 直播播放界面
 */
public class NewsDetailActivity extends RxBaseActivity {
    @BindView(R.id.iv_header)
    ImageView mIvHeader;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_source)
    TextView mTvSource;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.wv_news)
    WebView mWvNews;
    @BindView(R.id.nested_view)
    NestedScrollView mNestedView;
    @BindView(R.id.tv_load_empty)
    TextView mTvLoadEmpty;
    @BindView(R.id.tv_load_error)
    TextView mTvLoadError;
    @BindView(R.id.pb_loading)
    ContentLoadingProgressBar mPbLoading;


    private WebViewClientBase webViewClient = new WebViewClientBase();
    private String link;
    private String title;
    private String img;
    private String xuan;
    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            link = intent.getStringExtra(ConstantUtil.EXTRA_URL );
            title = intent.getStringExtra(ConstantUtil.EXTRA_TITLE);
            img = intent.getStringExtra(ConstantUtil.EXTRA_IMG_URL);
            xuan=intent.getStringExtra(ConstantUtil.xuan);
        }
        mWvNews.setDrawingCacheEnabled(true);
        mWvNews.setWebViewClient(webViewClient);
        WebSettings settings =  mWvNews.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        getLiveUrl();


    }
    @OnClick({ R.id.ic_fan_go, R.id.ic_share_go})
    public void sayHi(ImageView mImageView) {
        switch (mImageView.getId()) {
            case R.id.ic_fan_go:
                finish();
                break;
            case R.id.ic_share_go:
                ShareUtil.shareLink("http://m.hanju.cc"+link,
                        title, NewsDetailActivity.this);
                break;

            default:
                break;
        }
    }
    @Override
    public void initToolBar() {
//        mToolbar.setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void getLiveUrl() {
        if(xuan.equals("wen"))
        RetrofitHelper.gethanjuAPI()
                .gethantupianid(link)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    try {
                        byte[] responseBytes=responseBody.bytes();
                        String responseUrl = new String(responseBytes,"gb2312");
                        Document doc = Jsoup.parse(responseUrl);
                        NewsdetailBiz mNewsdetailBiz=new NewsdetailBiz();
                        NewsdetailInfo mNewsdetailInfo;
                        mNewsdetailInfo=mNewsdetailBiz.NewsdetailNewsItems(doc);
                            Glide.with(this)
                                    .load( mNewsdetailInfo.getImg())
                                    .into(mIvHeader);
                            mTvTitle.setText(title);
                            mTvLoadEmpty.setVisibility(View.GONE);
                         mWvNews.loadData(getHtmlData(mNewsdetailInfo.getLink()),"text/html; " +
                                 "charset=utf-8", "utf-8");
                        mTvLoadEmpty.setVisibility(View.GONE);

                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    } catch (CommonException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finishTask();
                }, throwable -> initEmptyView());
        else if(xuan.equals("tu")){
            RetrofitHelper.gethanjuAPI(1)
                    .gethantupianid(link)
                    .compose(this.bindToLifecycle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseBody -> {
                        try {
                            byte[] responseBytes=responseBody.bytes();
                            String responseUrl = new String(responseBytes,"gb2312");
                            Document doc = Jsoup.parse(responseUrl);
                            NewsdetailBiz mNewsdetailBiz=new NewsdetailBiz();
                            NewsdetailInfo mNewsdetailInfo;
                            mNewsdetailInfo=mNewsdetailBiz.NewstuNewsItems(doc);
                            Glide.with(this)
                                    .load(mNewsdetailInfo.getImg())
                                    .into(mIvHeader);
                            mTvTitle.setText(title);
                            mWvNews.loadData(getNewContent(mNewsdetailInfo.getLink()),
                                    "text/html; charset=utf-8", "utf-8");
                            mTvLoadEmpty.setVisibility(View.GONE);

                        } catch (StringIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        } catch (CommonException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        finishTask();
                    }, throwable -> initEmptyView());
        }

    }
    public class WebViewClientBase extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mWvNews.getSettings().setBlockNetworkImage(false);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            String errorHtml = "<html><body><h2>找不到网页</h2></body></html>";
            view.loadDataWithBaseURL(null, errorHtml, "text/html", "UTF-8", null);
        }

    }
    //对爬虫得的doc进行编码，处理图片显示格式！！
    public static String getNewContent(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            Elements element=doc.getElementsByTag("ul");
            Elements elements1=doc.getElementsByTag("h1");
            Elements elements2=doc.getElementsByTag("p");
            String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
            String ss= "<html>" + head + "<body>"+elements1.get(0).toString()+elements2.get(0).toString()
                    +element.get(1).toString();
            for(int i=0;i<elements.size();i++)
            {
                ss=ss+elements.get(i).toString()+"<p>"+elements.get(i).attr("alt")+"</p>";
            }
            ss=ss+ "来源:<a href=\"http://www.hanju.cc\" target=\"_blank\">韩剧网</a>" +
                    " http://www.hanju.cc</div></div></body></html>";
            return ss;
        } catch (Exception e) {
            return null;
        }
    }
    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML +
                "来源:<a href=\"http://www.hanju.cc\" target=\"_blank\">韩剧网</a>" +
                " http://www.hanju.cc</div></div></body></html>";
    }

    public static void launch(Activity activity, String title, String link,String img,String xuan) {
        Intent mIntent = new Intent(activity, NewsDetailActivity.class);
        mIntent.putExtra(ConstantUtil.EXTRA_IMG_URL, img);
        mIntent.putExtra(ConstantUtil.EXTRA_URL, link);
        mIntent.putExtra(ConstantUtil.EXTRA_TITLE, title);
        mIntent.putExtra(ConstantUtil.xuan, xuan);
        activity.startActivity(mIntent);
    }


    private void initEmptyView() {
        hideProgress();
        mTvLoadError.setVisibility(View.VISIBLE);
        mTvLoadEmpty.setVisibility(View.GONE);
    }
    public void showProgress() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mPbLoading.setVisibility(View.GONE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
