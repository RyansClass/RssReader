package jp.ryans.rssreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * ImageViewに設定する画像をPicassoでWebから取得し表示する
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
        ImageView iv = (ImageView)convertView.findViewById(R.id.itemImage);
        if( data.isImage() ) {
            // URLを指定して画像を設定する
            Picasso.with(this.getContext()).load(data.getImage()).into(iv);
        } else {
            // 画像の代わりにアイコンを設定する
            iv.setImageResource(R.drawable.ic_launcher_foreground);
        }
        // タイトルを設定する
        ((TextView) convertView.findViewById(R.id.itemTitle)).setText(data.getTitle());
        // 説明を設定する
        ((TextView) convertView.findViewById(R.id.itemDescription)).setText(data.getDescription());

        return convertView;
    }


}
