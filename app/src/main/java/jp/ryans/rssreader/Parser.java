package jp.ryans.rssreader;

import java.io.InputStream;

/**
 * Created by ryan on 2017/12/09.
 */

public interface Parser {

    /**
     * パーサのパースを実装する
     * @param is パースするデータのストリーム
     * @return パースしたオブジェクトを返却する。詳細は実装するパーサが決定する
     */
    Object parse(InputStream is);


}
