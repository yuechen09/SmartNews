package com.laioffer.tinnews.tin;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.laioffer.tinnews.TinApplication;
import com.laioffer.tinnews.database.AppDatabase;
import com.laioffer.tinnews.retrofit.NewsRequestApi;
import com.laioffer.tinnews.retrofit.RetrofitClient;
import com.laioffer.tinnews.retrofit.response.News;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TinModel implements TinContract.Model {

    private TinContract.Presenter presenter;
    private final NewsRequestApi newsRequestApi;
    private final AppDatabase db;

    public TinModel() {
        newsRequestApi = RetrofitClient.getInstance().create(NewsRequestApi.class); // there may be multiple requests in NewsRequestAPI, not only top-headlins
        db = TinApplication.getDatabase();
    }


    @Override
    public void setPresenter(TinContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @SuppressLint("CheckResult")
    @Override
    public void fetchData(String country) {
        newsRequestApi.getNewsByCountry(country)
                .subscribeOn(Schedulers.io()) // background thread
                .observeOn(AndroidSchedulers.mainThread()) // UI thread
                .filter(baseResponse -> baseResponse != null && baseResponse.articles != null)
                .subscribe(baseResponse -> {
                    presenter.showNewsCard(baseResponse.articles);
                }, error -> {
                    Log.e("test test", "error", error);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveFavoriteNews(News news) {
        Disposable disposable = Completable.fromAction(() -> db.newsDao().insertNews(news)).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(() ->{
                presenter.onSavedSuccess();
        }, error -> {
                    if (error instanceof SQLiteConstraintException) { // if there are duplicated news to save
                        presenter.onError();
                    }
        });
    }

}
