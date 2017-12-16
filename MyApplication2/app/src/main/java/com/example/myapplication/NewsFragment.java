package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by 上官轩明 on 2017/10/28.
 */

public class NewsFragment extends android.support.v4.app.Fragment {
    private String url;
    private View viewCache;//缓存fragment
    private LinearLayoutManager layoutManager;
    private int NEWS_LIST_ID = 1;
    private boolean loading = false;
    private List<NewsBean> newsBeanList;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news,container,false);
        //recyclerView = view.findViewById()

        if (viewCache == null) {
            viewCache = inflater.inflate(R.layout.fragment_news, container, false);

            RecyclerView recyclerView = viewCache.findViewById(R.id.recycler);

            /////////////////////////////////////////////////////////////设置fragment刷新/////////////////////////////////////////////////////
            swipeRefreshLayout = viewCache.findViewById(R.id.layout_swiperefresh);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    NEWS_LIST_ID = 1;
                    newsBeanList = new ArrayList<>();
                    myAdapter = new MyAdapter(newsBeanList);
                    myAdapter.setHasMore(true);
                    recyclerView.setAdapter(myAdapter);
                    layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    url = getArguments().getString("url");
                    new NewsAsyncTask(recyclerView, viewCache.getContext(), layoutManager).execute(url);

                }
            });
///////////////////////////////////////////////////////设置fragment刷新/////////////////////////////////////////////////////

            newsBeanList = new ArrayList<>();
            myAdapter = new MyAdapter(newsBeanList);
            recyclerView.setAdapter(myAdapter);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            url = getArguments().getString("url");
            new NewsAsyncTask(recyclerView, viewCache.getContext(), layoutManager).execute(url);

            ////////////////////////////////////////////////////上拉加载///////////////////////////////////////////////////////

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int totalcount = layoutManager.getItemCount();
                    int lastVisableItem = layoutManager.findLastVisibleItemPosition();
                    if (totalcount < (lastVisableItem + 2) && !loading) {//倒数第二个item就加载
                        loading = true;
                        ++NEWS_LIST_ID;
                        url = url.substring(0, url.length() - 1) + NEWS_LIST_ID;
                        new NewsAsyncTask(recyclerView, viewCache.getContext(), layoutManager).execute(url);
                    }
                }
            });
        }
        ////////////////////////////////////////////////////上拉加载///////////////////////////////////////////////////////


        ViewGroup parent = (ViewGroup) viewCache.getParent();
        if (parent != null) {
            parent.removeView(viewCache);
        }

        return viewCache;
    }

    public void refresh() {
        if (viewCache != null && loading == false) {
            loading = true;
            NEWS_LIST_ID = 1;
            RecyclerView recyclerView = viewCache.findViewById(R.id.recycler);
            url = getArguments().getString("url");
            newsBeanList.clear();
            new NewsAsyncTask(recyclerView, viewCache.getContext(), layoutManager).execute(url);
        }

    }


    /////////////////////////////////////内部类///////////////////////////////
    class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

        private RecyclerView recyclerView;
        private Context mcontext;
        private LinearLayoutManager layout;

        ///////////////////////////////////////////构造函数给recycler和context赋值/////////////////////////////////////////////////////////

        public NewsAsyncTask(RecyclerView recyclerView, Context context, LinearLayoutManager layout) {
            this.recyclerView = recyclerView;
            this.mcontext = context;
            this.layout = layout;
        }
        ///////////////////////////////////////////构造函数给recycler和context赋值/////////////////////////////////////////////////////////


        private List<NewsBean> getNewsData(String url) {
            String jsonData = "";
            List<NewsBean> mlist = new ArrayList<>();
            try {
                //InputStream is = new URL(url).openStream();
                jsonData = readJosnData(url);
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    NewsBean newsBean = new NewsBean();
                    jsonObject = jsonArray.getJSONObject(i);
                    newsBean.title = jsonObject.getString("subject");
                    newsBean.content = jsonObject.getString("summary");
                    newsBean.imgUrl = jsonObject.getString("pic");
                    newsBean.id = jsonObject.getInt("index");
                    mlist.add(newsBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mlist;
        }

        private String readJosnData(String url) {
            String result = "";
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request
                    .Builder()
                    .url(url)
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            String line = "";
//
//            InputStreamReader isr = null;
//            try {
//                isr = new InputStreamReader(is, "utf-8");
//                BufferedReader br = new BufferedReader(isr);
//                while ((line = br.readLine()) != null) {
//                    result += line;
//                }
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return result;
        }


        /////////////////////////////////////////////////////////异步任务///////////////////////////////////////////////////
        @Override
        protected List<NewsBean> doInBackground(String... strings) {
            loading = true;
            return getNewsData(strings[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsList) {
            super.onPostExecute(newsList);
            if (newsList.size() == 0) {
                myAdapter.setHasMore(false);
            }
            newsBeanList.addAll(newsBeanList.size(), newsList);
            //myAdapter.mlist = newsBeanList;
            myAdapter.notifyDataSetChanged();
            // recyclerView.setAdapter(myAdapter);
            loading = false;
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}




