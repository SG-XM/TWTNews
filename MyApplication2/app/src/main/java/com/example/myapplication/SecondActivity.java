package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;

public class SecondActivity extends AppCompatActivity {
    private String url;
    private String title;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NewsContentBean newsContentBean = (NewsContentBean) msg.obj;
            WebView webView = findViewById(R.id.news_content);
            String content = newsContentBean.getData().getContent() +
                    " <p class=\"twt-s-lighter twt-m-a-0\">供稿人：" + newsContentBean.getData().getGonggao() + "</p>\n" +
                    "        <p class=\"twt-s-lighter twt-m-a-0\">审稿人：" + newsContentBean.getData().getShengao() + "</p>\n" +
                    "        <p class=\"twt-s-lighter twt-m-a-0\">摄影人：" + newsContentBean.getData().getSheying() + "</p>";
            content = "\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <b><p style=\"text-align: center; font-size:20px; font-family:宋体;\">" + title + "</p></b><hr />" +
                    "</head>" + content;
            webView.loadData(content, "text/html; charset=UTF-8", null);

//            NewsBean newsBean = (NewsBean) msg.obj;
//            WebView webView = findViewById(R.id.news_content);
//            newsBean.content += " <p class=\"twt-s-lighter twt-m-a-0\">供稿人：" + newsBean.gonggao + "</p>\n" +
//                    "        <p class=\"twt-s-lighter twt-m-a-0\">审稿人：" + newsBean.shengao + "</p>\n" +
//                    "        <p class=\"twt-s-lighter twt-m-a-0\">摄影人：" + newsBean.sheying + "</p>";
//            newsBean.content = "\n" +
//                    "<head>\n" +
//                    "    <meta charset=\"UTF-8\">\n" +
//                    "    <b><p style=\"text-align: center; font-size:20px; font-family:宋体;\">" + title + "</p></b><hr />" +
//                    "</head>" + newsBean.content;
//            webView.loadData(newsBean.content, "text/html; charset=UTF-8", null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = findViewById(R.id.second_toolbar);
        toolbar.setTitle("天外天新闻");
        setSupportActionBar(toolbar);
        ////////////////////////////////////////////////////辣么大表达式/////////////////////////////////////
        if (getSupportActionBar() != null) {//不要用getActionBar会返回null
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示back按钮
            toolbar.setNavigationOnClickListener(v -> onBackPressed());//设置back按钮点击事件
        }

        //////////////////////////////////////// ///从intent中获取信息////////////////////////////////////////////////
        Intent intent;
        intent = getIntent();
        int index = intent.getIntExtra("index", -1);
        title = intent.getStringExtra("title");
        toolbar.setTitle(title);

        ////////////////////////////////////////根据index生成url////////////////////////////////////////////
        if (index != -1) {
            url = "https://open.twtstudio.com/api/v1/news/" + index;
        }
        //////////////////////////////////////////////retrofit获取新闻具体内容.//////////////////////////////////////////
        Retrofit retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://open.twtstudio.com/api/v1/news/")
                .build();
        APIInterface apiInterface = retrofit.create(APIInterface.class);
        retrofit2.Call<NewsContentBean> retrofitCall = apiInterface.repo(index);
        retrofitCall.enqueue(new retrofit2.Callback<NewsContentBean>() {
            @Override
            public void onResponse(retrofit2.Call<NewsContentBean> call, retrofit2.Response<NewsContentBean> response) {
                if (response.isSuccessful()) {
                    NewsContentBean newsContentBean = response.body();
                    Message message = Message.obtain();
                    message.obj = newsContentBean;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<NewsContentBean> call, Throwable t) {

            }
        });

        //////////////////////////////////////////////retrofit获取新闻具体内容.//////////////////////////////////////////


        //////////////////////////////////////okhttp获取新闻具体内容///////////////////////////////

//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request
//                .Builder()
//                .url(url)
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                NewsBean newsBean;
//                String result = response.body().string();
//                newsBean = getNewsDataByString(result);
//
//                Message message = Message.obtain();
//                message.obj = newsBean;
//                handler.sendMessage(message);
//
//            }
//        });

        //new NewsAysncTask().execute(url);

    }
    //////////////////////////////////////////////////////字符串解析////////////////////////////////////////////////////////////////

    public NewsBean getNewsDataByString(String jsonData) {
        //String jsonData = "";
        NewsBean newsBean = new NewsBean();
        try {
            //  InputStream is = new URL(url).openStream();
            //jsonData = readJosnData(is);
            ////////////////////////////////将字符串解析为需要的格式////////////////////////////////////////
            JSONObject jsonObject = new JSONObject(jsonData);
            //JSONArray jsonArray = jsonObject.getJSONArray("data");
            jsonObject = jsonObject.getJSONObject("data");
            newsBean.title = jsonObject.getString("subject");
            newsBean.content = jsonObject.getString("content");
            newsBean.gonggao = jsonObject.getString("gonggao");
            newsBean.shengao = jsonObject.getString("shengao");
            newsBean.sheying = jsonObject.getString("sheying");
            newsBean.visitcount = jsonObject.getInt("visitcount");
            //JSONArray jsonArray = jsonObject.getJSONArray("data");
            // for (int i = 0; i < jsonArray.length(); i++) {
//                NewsBean newsBean = new NewsBean();
//                jsonObject = jsonArray.getJSONObject(i);
//                newsBean.title = jsonObject.getString("subject");
//                newsBean.content = jsonObject.getString("summary");
//                newsBean.imgUrl = jsonObject.getString("pic");
//                newsBean.id = jsonObject.getInt("index");
//                mlist.add(newsBean);
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsBean;
    }

    //////////////////////////////////////////////////////字符串解析////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////设置菜单////////////////////////////////////
    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        new MenuInflater(this).inflate(R.menu.toolbar, menu);
        menu.removeItem(menu.findItem(R.id.toolbar_setting).getItemId());
        menu.findItem(R.id.toolbar_refresh).setShowAsAction(SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_refresh:
                new NewsAysncTask().execute(url);
        }
        return super.onOptionsItemSelected(item);
    }

    ////////////////////////////////////////////////发起网络请求返回list////////////////////////////////////////////
    public NewsBean getNewsData(String url) {
        String jsonData = "";
        NewsBean newsBean = new NewsBean();
        try {
            InputStream is = new URL(url).openStream();
            jsonData = readJosnData(is);
            ////////////////////////////////将字符串解析为需要的格式////////////////////////////////////////
            JSONObject jsonObject = new JSONObject(jsonData);
            //JSONArray jsonArray = jsonObject.getJSONArray("data");
            jsonObject = jsonObject.getJSONObject("data");
            newsBean.title = jsonObject.getString("subject");
            newsBean.content = jsonObject.getString("content");
            newsBean.gonggao = jsonObject.getString("gonggao");
            newsBean.shengao = jsonObject.getString("shengao");
            newsBean.sheying = jsonObject.getString("sheying");
            newsBean.visitcount = jsonObject.getInt("visitcount");
            //JSONArray jsonArray = jsonObject.getJSONArray("data");
            // for (int i = 0; i < jsonArray.length(); i++) {
//                NewsBean newsBean = new NewsBean();
//                jsonObject = jsonArray.getJSONObject(i);
//                newsBean.title = jsonObject.getString("subject");
//                newsBean.content = jsonObject.getString("summary");
//                newsBean.imgUrl = jsonObject.getString("pic");
//                newsBean.id = jsonObject.getInt("index");
//                mlist.add(newsBean);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsBean;
    }

    ///////////////////////////////////读入流返回字符串/////////////////////////////////
    public String readJosnData(InputStream is) {
        String line = "";
        String result = "";
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private class NewsAysncTask extends AsyncTask<String, Void, NewsBean> {


        @Override
        protected NewsBean doInBackground(String... strings) {
            return getNewsData(url);
        }

        /////////////////////////////////////////////////////异步执行后显示内容///////////////////////////////////////////////////////////////
        @Override
        protected void onPostExecute(NewsBean newsBean) {
            super.onPostExecute(newsBean);
            WebView webView = findViewById(R.id.news_content);
            newsBean.content += " <p class=\"twt-s-lighter twt-m-a-0\">供稿人：" + newsBean.gonggao + "</p>\n" +
                    "        <p class=\"twt-s-lighter twt-m-a-0\">审稿人：" + newsBean.shengao + "</p>\n" +
                    "        <p class=\"twt-s-lighter twt-m-a-0\">摄影人：" + newsBean.sheying + "</p>";
            newsBean.content = "\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <b><p style=\"text-align: center; font-size:20px; font-family:宋体;\">" + title + "</p></b><hr />" +
                    "</head>" + newsBean.content;
            webView.loadData(newsBean.content, "text/html; charset=UTF-8", null);
        }
    }
}
