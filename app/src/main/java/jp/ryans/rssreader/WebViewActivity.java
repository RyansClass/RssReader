package jp.ryans.rssreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        // 再表示の判定
        if( savedInstanceState == null ) {
            // 再表示ではないのでメインのアクティビティから呼び出された
            Intent intent = getIntent();
            // 表示するURLを取得
            link = intent.getStringExtra("link");
            if( "".equals(link) || link == null ) {
                // 表示するURLが設定されていない

            }
        }
        // Webビューワのインスタンスを取得
        WebView v = this.findViewById(R.id.webView);
        // ビューワ内で別もページを表示させる
        v.setWebViewClient(new WebViewClient());
        // ターゲットのURLを表示
        v.loadUrl(link);

    }
}
