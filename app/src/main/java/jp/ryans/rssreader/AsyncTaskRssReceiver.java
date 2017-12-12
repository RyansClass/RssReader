package jp.ryans.rssreader;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
/**
 * Created by ryan on 2017/12/09.
 */

public class AsyncTaskRssReceiver extends AsyncTask<String,Integer,Object> {

    private String urlString;

    private CatchResponse callback;

     @Override
    protected Object doInBackground(String... strings) {

         InputStream is = null;
         URL url = null;
         Parser parser = null;

         this.urlString = "";

         switch(strings.length) {
             case 1:
                 // 受信のみ
                 this.urlString = strings[0];
                 is = getInputStream(this.urlString);
                 break;
             case 2:
                 // 受信してパースする
                 this.urlString = strings[0];
                 is = getInputStream(this.urlString);
                 parser = getParserInstance(strings[1]);
                 break;
         }

         if( null == is ) {
             return null;
         }
         if( null == parser) {
             ByteArrayOutputStream bio = new ByteArrayOutputStream();
             byte[] buffer = new byte[1024*1024];
             int len = 0;
             try {
                 while( -1 != (len = is.read(buffer,0,buffer.length-1))) {
                     bio.write(buffer,0,len);
                 }
             } catch (Exception e) {
                 Log.e(this.getClass().getSimpleName(),"入出力エラー " + e.getMessage());
             }

             try {
                 is.close();
                 bio.close();
             } catch (IOException e) {
                 Log.e(this.getClass().getSimpleName(),"クローズエラー");
             }

             return  bio.toByteArray();

         } else {

             Object obj = parser.parse(is);

             try {
                 is.close();
             } catch (IOException e) {
                 Log.e(this.getClass().getSimpleName(),"クローズエラー");
             }
             return obj;
         }
    }

    private InputStream getInputStream(String urlString) {
        InputStream is = null;
        try {
            // URLに日本語が含まれた場合の処理
            String encodedResult = new URI(urlString).toASCIIString();

            URL url = new URL(encodedResult);

            return  url.openConnection().getInputStream();

        } catch (Exception e) {

            Log.e(this.getClass().getSimpleName(),"接続できません");

        }
        return null;
    }

    private Parser getParserInstance(String className) {
        try {

            Parser parser =(Parser) Class.forName(className).newInstance();

            return parser;

        } catch (Exception e) {

            Log.e(this.getClass().getSimpleName(),"クラスが見つかりません");

        }
        return null;
    }

    public void setCallback(CatchResponse callback) {
       this.callback = callback;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object obj) {
        this.callback.setData(this.urlString,obj);
    }

}
