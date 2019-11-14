package com.laioffer.tinnews.tin;

import com.laioffer.tinnews.retrofit.response.News;

import java.util.List;

public class TinPresenter implements TinContract.Presenter {

    private TinContract.View view;
    private TinContract.Model model;

    public TinPresenter () {
        this.model = new TinModel(); // bond presenter with model
        this.model.setPresenter(this);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onViewAttached(TinContract.View view) {
        this.view = view;
        this.model.fetchData();
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }

    @Override
    public void showNewsCard(List<News> newsList) {
        if (view != null) {
            view.showNewsCard(newsList);
        }
    }

    @Override
    public void saveFavoriteNews(News news) {
        model.saveFavoriteNews(news);
    }

    @Override
    public void onError() {
        if (view != null) {
            view.onError();
        }
    }

    @Override
    public void onSavedSuccess() {
        if (view != null) {
            view.onSavedSuccess();
        }
    }
}
