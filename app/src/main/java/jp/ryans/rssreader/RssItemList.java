package jp.ryans.rssreader;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ryan on 2017/12/09.
 */

public class RssItemList extends ArrayList<RssItemData> {

    /**
     * RSSの取得URL
     */
    private String url;

    /**
     * RSSのタイトル
     */
    private String title;

    /**
     * RSSの説明
     */
    private String description;

    /**
     * コンストラクタ
     */
    public RssItemList() {

        super();

        this.url = "";

        this.title = "";

        this.description = "";

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if ( null == url ) {
            this.url = "";
        } else {
            this.url = url;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if ( null == title ) {
            this.title = "";
        } else {
            this.title = title;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if ( null == description ) {
            this.description = "";
        } else {
            this.description = description;
        }
    }

    /**
     * 画像のURLを指定してアイテムを取得
     * @param image 画像のURL
     * @return 存在すればアイテムを返却。存在しなければnullを返却
     */
    public RssItemData getItemForImage(String image) {
        for(RssItemData item: this) {
            // 画像のURLと等しいか検査
            if(item.getImage().equals(image)) {

                return item;
            }
        }
        return null;
    }

}
