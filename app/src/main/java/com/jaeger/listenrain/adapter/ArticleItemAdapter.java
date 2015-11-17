package com.jaeger.listenrain.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jaeger.listenrain.R;
import com.jaeger.listenrain.base.BaseRecycleAdapter;
import com.jaeger.listenrain.entity.Article;

import java.util.ArrayList;

/**
 * Created by Jaeger on 15/8/6.
 * ListenRain
 */
public class ArticleItemAdapter extends BaseRecycleAdapter {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Article> articles;

    public ArticleItemAdapter(Context context, ArrayList<Article> articles) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.articles = articles;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ArticleViewHolder(inflater.inflate(R.layout.list_item_article, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ArticleViewHolder articleViewHolder = (ArticleViewHolder) viewHolder;
        Article article = articles.get(position);
        articleViewHolder.tvTitle.setText(article.getTitle());
        articleViewHolder.tvDate.setText(article.getDate());
        articleViewHolder.ivCover.setImageURI(Uri.parse(article.getCoverUrl()));
        articleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends ViewHolder implements View.OnClickListener {
        private View itemView;
        SimpleDraweeView ivCover;
        TextView tvTitle;
        TextView tvDate;

        public ArticleViewHolder(View view) {
            super(view);
            itemView = view;
            ivCover = (SimpleDraweeView) view.findViewById(R.id.iv_cover);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
