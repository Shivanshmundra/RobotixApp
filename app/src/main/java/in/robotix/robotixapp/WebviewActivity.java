package in.robotix.robotixapp;

/**
 * Created by USER on 16-07-2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    private WebView mWebView;
    private AppBarLayout mAppbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout.LayoutParams params;

    private String mUrl, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                mUrl= null;
                title = null;
            } else {
                mUrl= extras.getString("Link");
                title= extras.getString("Title");
            }
        } else {
            mUrl= (String) savedInstanceState.getSerializable("Link");
            title= (String) savedInstanceState.getSerializable("Title");
        }
        setContentView(R.layout.activity_webview);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tut);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_tut);
        params = (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();
        collapsingToolbar.setTitle(title);

        mContext = getApplicationContext();
        mActivity = WebviewActivity.this;
        mWebView = (WebView) findViewById(R.id.web_view);
        mAppbar =  (AppBarLayout) findViewById(R.id.appbar);

        renderWebPage(mUrl);
    }

    // Custom method to render a web page
    protected void renderWebPage(String urlToRender) {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Do something on page loading started
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Do something when page loading finished
                mAppbar.setExpanded(false);
//                params.setScrollFlags(0);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAppCachePath(mContext.getCacheDir().getPath());
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.loadUrl(urlToRender);
    }
}