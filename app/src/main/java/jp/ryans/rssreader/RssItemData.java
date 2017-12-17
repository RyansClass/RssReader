package jp.ryans.rssreader;

import java.io.Serializable;

/**
 * Created by ryan on 2017/12/09.
 */

public class RssItemData implements Serializable {

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

    public void setImage(String image) {
        if (null == image) {
            this.image = "";
        } else {
            this.image = image.trim();
        }

    }
}
