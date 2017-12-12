package jp.ryans.rssreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ryan on 2017/12/09.
 */

public class RssListAdapter extends ArrayAdapter<RssItemData> {

    /**
     * アイテムリストのIDを保存しておいてクラス内で使用できるようにします
     */
    private int resource;

    public RssListAdapter(Context context, int resource, RssItemList itemList) {
        // 親クラスのコンストラクタを呼び出します
        super(context, resource, itemList);
        // リソースIDを保存します
        // ArrayAdapterはリソースIDを設定できるが取得するメソッドがない
        // 後で利用できるように保存しておく
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 変更するListViewのインスタンスがあるかチェックする
        // インスタンスがないのでレイアウトから生成する
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(resource, parent, false);
        }
        // リストからポジションを指定して表示するテキストを取得する
        RssItemData data = this.getItem(position);
        // 画像を設定する
        if( data.isImage() ) {
            // アイテムからBitmapのオブジェクトを取得しImageViewに設定
            ((ImageView)convertView.findViewById(R.id.itemImage)).setImageBitmap(data.getImageBitmap(this.getContext()));
        }
        // タイトルを設定する
        ((TextView) convertView.findViewById(R.id.itemTitle)).setText(data.getTitle());
        // 説明を設定する
        ((TextView) convertView.findViewById(R.id.itemDescription)).setText(data.getDescription());

        return convertView;
    }


}
