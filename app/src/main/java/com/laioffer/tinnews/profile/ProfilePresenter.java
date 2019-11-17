package com.laioffer.tinnews.profile;

import android.view.View;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View view;
    private ProfileContract.Model model;

    ProfilePresenter() {
        this.model = new ProfileModel();
        this.model.setPresenter(this);
    }

    @Override
    public void onCacheCleared() {
        if (view != null) {   // presenter tells the view it has been deleted
            view.onCacheCleared();
        }
    }

    @Override
    public View.OnClickListener getCacheClearListener() {  // presenter will return a listener to view, the listener will be used in RowTextViewModel
        return view -> { // return a listener interface(view.OnClickListener)
            model.deleteAllNewsCache();  // when you click the listener on view, it will call model to delete
        };
    }

    @Override
    public View.OnClickListener getOnCountryChangeListener(String country) {
        return view -> {
            model.setDefaultCountry(country);
        };
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onViewAttached(ProfileContract.View view) {
        this.view = view;
        this.view.setView();
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }
}
