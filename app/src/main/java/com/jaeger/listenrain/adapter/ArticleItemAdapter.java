package com.jaeger.listenrain.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.facebook.drawee.view.SimpleDraweeView;
import com.jaeger.listenrain.R;
import com.jaeger.listenrain.activity.ArticleDetailActivity;
import com.jaeger.listenrain.entity.Article;

import java.util.ArrayList;

/**
 * Created by Jaeger on 15/8/6.
 * Schoolmate
 */
public class ArticleItemAdapter extends RecyclerView.Adapter<ArticleItemAdapter.ArticleViewHolder> {
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
    public void onBindViewHolder(ArticleViewHolder viewHolder, int position) {
        Article article = articles.get(position);
        viewHolder.tvTitle.setText(article.getTitle());
        viewHolder.tvDate.setText(article.getDate());
        viewHolder.ivCover.setImageURI(Uri.parse(article.getCoverUrl()));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView ivCover;
        TextView tvTitle;
        TextView tvDate;

        public ArticleViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArticleDetailActivity.class);
                    intent.putExtra("article", articles.get(getPosition()));
                    context.startActivity(intent);
//                    Toast.makeText(context, "" + getPosition(), Toast.LENGTH_LONG).show();
                }
            });
            ivCover = (SimpleDraweeView) view.findViewById(R.id.iv_cover);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
        }
    }

    public class FootViewHolder extends RecyclerView.ViewHolder {

//        SimpleDraweeView ivCover;
//        TextView tvTitle;
//        TextView tvDate;

        public FootViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArticleDetailActivity.class);
                    intent.putExtra("article", articles.get(getPosition()));
                    context.startActivity(intent);
//                    Toast.makeText(context, "" + getPosition(), Toast.LENGTH_LONG).show();
                }
            });
//            ivCover = (SimpleDraweeView) view.findViewById(R.id.iv_cover);
//            tvTitle = (TextView) view.findViewById(R.id.tv_title);
//            tvDate = (TextView) view.findViewById(R.id.tv_date);
        }
    }
}
