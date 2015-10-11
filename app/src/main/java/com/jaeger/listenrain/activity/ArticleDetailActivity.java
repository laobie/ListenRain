package com.jaeger.listenrain.activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jaeger.listenrain.R;
import com.jaeger.listenrain.base.BaseActivity;
import com.jaeger.listenrain.entity.Article;
import com.jaeger.listenrain.widget.CustomScrollView;
import com.jaeger.listenrain.widget.TitleBar;

/**
 * Created by Jaeger on 15/9/30.
 * ListenRain
 */
public class ArticleDetailActivity extends BaseActivity {
    private SimpleDraweeView ivCover;
    private TitleBar titleBar;
    private CustomScrollView svRoot;
    private TextView tvContent;
    private Article article;


    @Override
    protected void initIntentParam(Intent intent) {
        article = (Article) intent.getSerializableExtra("article");
    }

    @Override
    protected void beforeInitView() {
        super.beforeInitView();

    }


    @Override
    protected void initView() {

        setContentView(R.layout.activity_article_detail);
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        tvContent = (TextView) findViewById(R.id.tv_content);
        ivCover = (SimpleDraweeView) findViewById(R.id.iv_cover);
        svRoot = (CustomScrollView) findViewById(R.id.sv_root);
    }

    @Override
    protected void setViewStatus() {
        titleBar.setTitle(article.getTitle());
        tvContent.setText(article.getContent());
        ivCover.setImageURI(Uri.parse(article.getCoverUrl()));


        svRoot.setScrollListener(new CustomScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldX, int OldY) {
                if (svRoot.getScrollY() >= (ivCover.getHeight() - titleBar.getHeight())) {
                    titleBar.setColor(false);
                } else {
                    titleBar.setColor(true);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
