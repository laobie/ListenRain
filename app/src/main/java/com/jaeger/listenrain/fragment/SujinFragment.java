package com.jaeger.listenrain.fragment;

import android.animation.ValueAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.jaeger.listenrain.R;
import com.jaeger.listenrain.adapter.ArticleItemAdapter;
import com.jaeger.listenrain.base.BaseFragment;
import com.jaeger.listenrain.entity.Article;
import com.jaeger.listenrain.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaeger on 15/8/6.
 * Schoolmate
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
                getArticleList();
            }

            @Override
            public void completeRefresh() {

            }
        });

        refreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoading() {
                page++;
                getArticleList();
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
                        String coverUrl = avObject.getAVFile("cover").getThumbnailUrl(true, 360, 360);
                        String date = avObject.getString("date");
                        article = new Article(title, content, coverUrl, date);
                        articles.add(article);
                    }

                    adapter.notifyDataSetChanged();
                    refreshLayout.finishRefreshing();
                    ValueAnimator mUpBackAnimator = ValueAnimator.ofInt(0, 50);
                    final int temp = 0;
                    mUpBackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue() - temp;
//                            temp = (int) animation.getAnimatedValue();
                        }
                    });
//                    rvArticles.scrollTo(0, 50);

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
