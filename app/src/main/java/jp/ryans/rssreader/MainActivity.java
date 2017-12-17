package jp.ryans.rssreader;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements CatchResponse {

    /**
     * リストビューに表示するRSSのアイテムリスト
     */
    private RssItemList rssList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // セーブされたリストが存在するか検査します
        if ( null != savedInstanceState) {
            rssList =(RssItemList) savedInstanceState.get("RssList");

        } else {
            // Rssデータのリスト
            rssList = new RssItemList();
            // 取得するURL
            rssList.setUrl("https://feeds.lifehacker.jp/rss/lifehacker/index.xml");
            // 通信タスクの生成
            AsyncTaskRssReceiver rss = new AsyncTaskRssReceiver();
            // 通信結果をここのメソッドを呼び出す
            rss.setCallback(this);
            // タスクの実行
            rss.execute(rssList.getUrl(),RssParser.class.getName());
        }
        // メインアクティビティからListViewのインスタンスを取得します
        ListView listView = (ListView) findViewById(R.id.rssList);
        // 作成したアダプタクラスのHeaderAdapterのインスタンスを生成します
        RssListAdapter adapter = new RssListAdapter((Context)this.getApplicationContext(), R.layout.layout_rss_item, rssList );
        // リストビューにアダプタを設定します。
        listView.setAdapter(adapter);
    }

    /**
     * アクティビティが終了する前に現在のリストを保存します
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("RssList",rssList);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void setData(String urlString, Object data) {
        // RssのURLと等しいか検査
        if( rssList.getUrl().equals(urlString)) {
            // Rssを取得できたか検査
            if ( null != data) {
                // dataはパースされているか検査
                if( data instanceof RssItemList ) {
                    // 現在のリストを取得したデータに置きかえる
                    RssItemList d = (RssItemList) data;
                    rssList.clear();
                    rssList.setUrl(d.getUrl());
                    rssList.setTitle(d.getTitle());
                    rssList.setDescription(d.getDescription());
                    rssList.addAll(d);
                    // 表示を更新する
                    ((ListView) findViewById(R.id.rssList)).invalidate();
                }
            }
        }
    }
}
