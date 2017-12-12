package jp.ryans.rssreader;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;


/**
 * Created by ryan on 2017/12/10.
 */

public class RssParser implements Parser {


    @Override
    public Object parse(InputStream is) {
        RssItemList list = new RssItemList();
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            RssItemData currentItem = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tag = null;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")) {
                            currentItem = new RssItemData();
                        } else if (currentItem != null) {
                            if (tag.equals("title")) {
                                currentItem.setTitle(parser.nextText());
                            } else if (tag.equals("description")) {
                                currentItem.setDescription(parser.nextText());
                            } else if (tag.equals("link")) {
                                currentItem.setLink(parser.nextText());
                            } else if (tag.equals("enclosure")) {
                                currentItem.setImage(parser.getAttributeValue(null,"url"));
                                parser.nextText();
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")) {
                            list.add(currentItem);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
