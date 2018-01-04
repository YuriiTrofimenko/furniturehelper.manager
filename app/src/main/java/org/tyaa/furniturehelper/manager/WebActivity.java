package org.tyaa.furniturehelper.manager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.tyaa.furniturehelper.manager.common.Global;

public class WebActivity extends AppCompatActivity {

    private String mWebUrl;
    private WebView mWebView;
    public static final int RESULT_WEB = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mWebUrl = getIntent().getStringExtra(LinksEditActivity.EXTRA_WEB_URL);

        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.webProgressBar);
        progressBar.setMax(100); // значения в диапазоне 0-100
        final TextView titleTextView = (TextView)findViewById(R.id.webTitleTextView);

        mWebView = (WebView) findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Global.currentUrl = url;
                setResult(WebActivity.RESULT_WEB);
                return false;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

                //Global.currentUrl = url;
                //setResult(WebActivity.RESULT_WEB);
                return super.shouldInterceptRequest(view, url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView webView, int progress) {

                if (progress == 100) {

                    progressBar.setVisibility(View.INVISIBLE);
                } else {

                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }

            public void onReceivedTitle(WebView webView, String title) {

                titleTextView.setText(title);
            }


        });

        mWebView.loadUrl(mWebUrl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //startActivity(new Intent(this, LinksEditActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {


        return super.onCreateView(parent, name, context, attrs);
    }
}
