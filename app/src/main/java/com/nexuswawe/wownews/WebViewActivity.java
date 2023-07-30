package com.nexuswawe.wownews;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class WebViewActivity extends AppCompatActivity {
    String url1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView myWebView = findViewById(R.id.webview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        String url = getIntent().getStringExtra("url");
        myWebView.loadUrl(url);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(myWebView, true);


        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // diğer WebView ayarları burada yapılabilir


        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                view.evaluateJavascript("(function() { " +
                        "var rejectButton = document.getElementById('onetrust-reject-all-handler'); " +
                        "if (rejectButton != null) { " +
                        "rejectButton.click();" +
                        "} " +
                        "})()", null);
                // Daha sonra, kabul düğmesini programatik olarak tıklamak için performClick() yöntemini çağırın:
                view.performClick();

            }
        });


        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Sayfa yüklendikten sonra yapılacak işlemler
                view.evaluateJavascript("(function() { " +
                        "var rejectButton = document.getElementById('onetrust-reject-all-handler'); " +
                        "if (rejectButton != null) { " +
                        "rejectButton.click();" +
                        "} " +
                        "})()", null);
                // Daha sonra, kabul düğmesini programatik olarak tıklamak için performClick() yöntemini çağırın:
                view.performClick();

            }
        });

    }

    @Override
    public void onBackPressed() {
        WebView webView = findViewById(R.id.webview);
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
