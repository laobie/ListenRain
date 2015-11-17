package com.jaeger.listenrain.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.jaeger.listenrain.R;
import com.jaeger.listenrain.activity.ArticleDetailActivity;
import com.jaeger.listenrain.adapter.ArticleItemAdapter;
import com.jaeger.listenrain.base.BaseFragment;
import com.jaeger.listenrain.base.BaseRecycleAdapter;
import com.jaeger.listenrain.entity.Article;
import com.jaeger.listenrain.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaeger on 15/8/6.
 * ListenRain
 */
public class SujinFragment extends BaseFragment {
    private RefreshLayout refreshLayout;
    private RecyclerView rvArticles;
    private ArticleItemAdapter adapter;
    private ArrayList<Article> articles = new ArrayList<>();
    int page = 0;

    @Override
    protected int setRootViewResId() {
        return R.layout.fragment_sujin;
    }

    @Override
    protected void beforeInitView() {
        super.beforeInitView();
    }

    @Override
    protected void initView() {
        rvArticles = (RecyclerView) rootView.findViewById(R.id.rv_articles);
        refreshLayout = (RefreshLayout) rootView.findViewById(R.id.rl_root);
    }

    @Override
    protected void setViewStatus() {
        rvArticles.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ArticleItemAdapter(getActivity(), articles);
        rvArticles.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefreshing() {
                page = 0;
                articles.clear();
                getArticleList();
            }

            public void onLoading() {
                page++;
                getArticleList();
            }

        });
        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, ArticleDetailActivity.class);
                intent.putExtra("article", articles.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getArticleList() {
        AVQuery<AVObject> query = AVQuery.getQuery("ArticleCloud");
        query.orderByDescending("index");
        query.setLimit(10);
        query.setSkip(page * 10);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Article article;
                    for (AVObject avObject : list) {
                        String title = avObject.getString("title");
                        String content = avObject.getString("content");
                        String coverUrl = avObject.getAVFile("cover").getUrl();
                        String date = avObject.getString("date");
                        article = new Article(title, content, coverUrl, date);
                        articles.add(article);
                    }

                    adapter.notifyDataSetChanged();
                    refreshLayout.finishRefreshing();
                } else {
                    showToastS(e.getMessage());
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
