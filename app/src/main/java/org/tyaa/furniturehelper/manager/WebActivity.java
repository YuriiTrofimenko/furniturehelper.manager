package org.tyaa.furniturehelper.manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.tyaa.furniturehelper.manager.common.Global;

public class WebActivity extends AppCompatActivity {
    private String mWebUrl;
    private WebView mWebView;
    public static final int RESULT_WEB = 0;
    public static final int RESULT_MAP_WEB = 1;

    private enum Mode {
        Link
        , Map
        , Nothing
    }
    private Mode mCurrentMode = Mode.Nothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent incomingIntent = getIntent();

        if (incomingIntent.hasExtra(LinksEditActivity.EXTRA_WEB_URL)){
            mCurrentMode = Mode.Link;
            mWebUrl = incomingIntent.getStringExtra(LinksEditActivity.EXTRA_WEB_URL);
        }
        else if (incomingIntent.hasExtra(LinksEditActivity.EXTRA_WEB_MAP_URL)){
            mCurrentMode = Mode.Map;
            mWebUrl = incomingIntent.getStringExtra(LinksEditActivity.EXTRA_WEB_MAP_URL);
        }

        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.webProgressBar);
        progressBar.setMax(100); // значения в диапазоне 0-100
        final TextView titleTextView = (TextView)findViewById(R.id.webTitleTextView);

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Global.currentUrl = url;

                if (mCurrentMode == Mode.Link){
                    setResult(WebActivity.RESULT_WEB);
                }
                else if (mCurrentMode == Mode.Map){
                    setResult(WebActivity.RESULT_MAP_WEB);
                }

                return false;
            }

            /*@Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                //Global.currentUrl = url;
                //setResult(WebActivity.RESULT_WEB);
                return super.shouldInterceptRequest(view, url);
            }*/
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int progress) {
                if (progress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }

            public void onReceivedTitle(WebView webView, String title) {
                titleTextView.setText(title);
            }
        });
        Log.d("asd", mWebUrl);
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
}
