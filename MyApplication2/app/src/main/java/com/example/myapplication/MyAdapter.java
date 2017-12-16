package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 上官轩明 on 2017/10/22.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<NewsBean> mlist = new ArrayList<>();
    private int index;
    private Bitmap bitmap;
    private Context mcontext;

    private final int TYPE_NORMAL = 0;
    private final int TYPE_FOOT = 1;

    private boolean hasMore = true;
    //private ImageLoader imageLoader = new ImageLoader();

    public MyAdapter(List<NewsBean> list) {
        mlist = list;
    }
///////////////////////////////////////////根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder//////////////////////////////////////////////////

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOT;
        } else {
            return TYPE_NORMAL;
        }
    }

    ///////////////////////////////////////////根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder//////////////////////////////////////////////////


    //为你的每一个view绑定相应的viewholder和相应的布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        mcontext = parent.getContext();
        if (viewType == TYPE_NORMAL) {
            //这个参数没看懂
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false));
        } else {
            return new FootViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot, parent, false));
            // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot, parent, false);
            //holder = new FootViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.newsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, SecondActivity.class);
                    //Toast.makeText(mcontext, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    intent.putExtra("index", mlist.get(position).id);
                    intent.putExtra("title", mlist.get(position).title);
                    mcontext.startActivity(intent);
                }
            });
//        holder.imgv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mcontext, SecondActivity.class);
//                intent.putExtra("index", mlist.get(position).id);
//                mcontext.startActivity(intent);
//            }
//        });
            viewHolder.content.setText(mlist.get(position).content);
            viewHolder.title.setText(mlist.get(position).title);
            if (mlist.get(position).imgUrl != "") {
                //holder.imgv.setTag(mlist.get(position).imgUrl);
                Glide.with(mcontext)
                        .load(mlist.get(position).imgUrl)
                        .centerCrop()
                        .placeholder(R.mipmap.logo)
                        .crossFade()
                        .into(viewHolder.imgv);
                // new ImageLoader(holder.imgv).execute(mlist.get(position).imgUrl);
            }
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            if (!hasMore) {
                footViewHolder.progressBar.setVisibility(View.INVISIBLE);
                footViewHolder.textView.setText("已经到底了");
                footViewHolder.textView.setVisibility(View.VISIBLE);
            } else {
                footViewHolder.progressBar.setVisibility(View.VISIBLE);
                // footViewHolder.textView.setText("已经到底了");
                footViewHolder.textView.setVisibility(View.INVISIBLE);
            }
            //footViewHolder.progressBar.setVisibility(ProgressBar.VISIBLE);
        }
    }


    //////////////////////////////////////////////////////////接口来对hasMore赋值////////////////////////////////////////////////////

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    //////////////////////////////////////////////////////////接口来对hasMore赋值////////////////////////////////////////////////////
    @Override
    public int getItemCount() {
        return mlist.size() + 1;//还要加上一个footviewitem
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View newsView;
        ImageView imgv;
        TextView title;
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            newsView = itemView;
            imgv = itemView.findViewById(R.id.img_news);
            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;
        TextView textView;

        public FootViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
            textView = itemView.findViewById(R.id.textview);
        }
    }
}
