package com.jaeger.listenrain.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jaeger.listenrain.R;
import com.jaeger.listenrain.base.BaseRecycleAdapter;
import com.jaeger.listenrain.entity.Article;

import java.util.List;

/**
 * Created by Jaeger on 15/8/6.
 * ListenRain
 */
public class ArticleItemAdapter extends BaseRecycleAdapter {

    private List<Article> articles;

    public ArticleItemAdapter(Context context, List<Article> articles) {
        super(context);
        this.articles = articles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArticleBaseViewHolder(inflater.inflate(R.layout.list_item_article, parent, false));
    }

    @Override
    public void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder viewHolder, final int position) {
        ArticleBaseViewHolder articleViewHolder = (ArticleBaseViewHolder) viewHolder;
        Article article = articles.get(position);
        articleViewHolder.tvTitle.setText(article.getTitle());
        articleViewHolder.tvDate.setText(article.getDate());
        articleViewHolder.ivCover.setImageURI(Uri.parse(article.getCoverUrl()));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleBaseViewHolder extends BaseViewHolder {

        SimpleDraweeView ivCover;
        TextView tvTitle;
        TextView tvDate;

        public ArticleBaseViewHolder(View view) {
            super(view);
            ivCover = (SimpleDraweeView) view.findViewById(R.id.iv_cover);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
        }
    }
}
