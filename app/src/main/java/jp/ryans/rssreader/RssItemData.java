package jp.ryans.rssreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ryan on 2017/12/09.
 */

public class RssItemData {

    /**
     * アイテムのタイトル
     */
    private String title;

    /**
     * アイテムの説明
     */
    private String description;

    /**
     * アイテムのリンク先のURL
     */
    private String link;

    /**
     * アイテムの画像のURL
     */
    private String image;

    /**
     * アイテムの画像の保存先ファイルパス
     */
    private String imageFilePath;

    public RssItemData() {
        this.title = "";
        this.description = "";
        this.link = "";
        this.image = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if ( null == title ) {
            this.title = "";
        } else {
            this.title = title.trim();
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (null == description) {
            this.description = "";
        } else {
            this.description = description.trim();
        }
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        if (null == link) {
            this.link = "";
        } else {
            this.link = link.trim();
        }
    }

    public String getImage() {
        return image;
    }

    /**
     * 画像のURLが設定されているか検査
     * @return trueは画像のURLが設定されている。falseはURLが設定されていない
     */
    public boolean isImage() {

        if( image.isEmpty() ) {

            return false;
        }
        return true;
    }

    /**
     * 画像ファイルをBitmapオブジェクトで取得する
     * @param context アプリケーションContext
     * @return Bitmapを生成できればそれを返却し、生成できなければnullを返却します。
     */
    public Bitmap getImageBitmap(Context context) {
        if( null == imageFilePath) {
            return null;
        } else if ( imageFilePath.isEmpty()) {
            return null;
        }
        try {
            // imageFilePathが相対パスでも絶対パスでファイルを開く
            String path = context.getFileStreamPath(imageFilePath).getAbsolutePath();
            InputStream is = new FileInputStream(path);

            Bitmap bitmap = BitmapFactory.decodeStream(is);

            return bitmap;

        } catch (FileNotFoundException e) {

            Log.e(this.getClass().getSimpleName(), "画像ファイルの読み込みができませんでした。");
        }

        return null;
    }

    public void setImage(String image) {
        if (null == image) {
            this.image = "";
        } else {
            this.image = image.trim();
        }
    }

    /**
     * 画像データをファイルに保存します
     * ファイルパスは画像のURLから生成し一意なファイルパスとしています。
     * @param context アプリケーションContext
     * @param data 画像のバイナリーデータ
     */
    public void setImageData(Context context,byte[] data) {
        if ( null == data ) {
            return;
        }
        if( this.image.isEmpty() ) {
            return;
        }
        // ファイルパスを生成
        this.imageFilePath = this.image.replaceAll("http://" ,"http_");
        this.imageFilePath = this.imageFilePath.replaceAll("https://" ,"https_");
        this.imageFilePath = this.imageFilePath.replaceAll("/" ,"_");
        String path = context.getFileStreamPath(this.imageFilePath).getAbsolutePath();
        // ファイルに画像データを保存
        try {
            FileOutputStream os = new FileOutputStream(new File(path));

            os.write(data);

            os.close();

        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "画像ファイルの書き込みができませんでした。");
            // 書き込めないのでファイルパスをクリアしアクセスできないようにする
            this.imageFilePath = "";

        }
    }
}
