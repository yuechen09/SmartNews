package com.laioffer.tinnews.tin;

import com.laioffer.tinnews.mvp.MvpContract;
import com.laioffer.tinnews.retrofit.response.News;

import java.util.List;

public interface TinContract {


    interface View extends MvpContract.View<Presenter> {
        void showNewsCard(List<News> newsList);
        void onError();
        void onSavedSuccess();
    }

    interface Presenter extends MvpContract.Presenter<View, Model> {
        void showNewsCard(List<News> newsList);  // model pass the data to presenter
        void saveFavoriteNews(News news);
        void onError();
        void onSavedSuccess();
    }

    interface Model extends MvpContract.Model<Presenter> {
        void fetchData(String country);
        void saveFavoriteNews(News news);
    }
}
