package jp.ryans.rssreader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.util.Log;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements CatchResponse,AdapterView.OnItemClickListener {

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
            Log.d(this.getClass().getSimpleName(),"セーブされたリストを使用");

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
            Log.d(this.getClass().getSimpleName(),"通信を行い取得");
        }
        // メインアクティビティからListViewのインスタンスを取得します
        ListView listView = (ListView) findViewById(R.id.rssList);
        // 作成したアダプタクラスのHeaderAdapterのインスタンスを生成します
        RssListAdapter adapter = new RssListAdapter((Context)this.getApplicationContext(), R.layout.layout_rss_item, rssList );
        // リストビューにアダプタを設定します。
        listView.setAdapter(adapter);
        // リストビューにリスナーを設定します
        listView.setOnItemClickListener(this);

    }

    /**
     * アクティビティが終了する前に現在のリストを保存します
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 取得したRSSリストを保存
        outState.putSerializable("RssList",rssList);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if( rssList.isEmpty() ) {
            Log.d(this.getClass().getSimpleName(),"受信を待つ");
        }
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
                }
            }
        }
        ListView li = ((ListView) findViewById(R.id.rssList));
        RssListAdapter ad = (RssListAdapter) li.getAdapter();
        ad.notifyDataSetChanged();
        Log.d(this.getClass().getSimpleName(),"通信からコールバック");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        RssItemData data = rssList.get(position);

        Intent it = new Intent(this,WebViewActivity.class);
        it.putExtra("link", data.getLink());
        startActivity(it);
    }
}
