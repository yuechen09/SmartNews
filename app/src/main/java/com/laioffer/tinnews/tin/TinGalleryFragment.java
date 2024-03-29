

package com.laioffer.tinnews.tin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.laioffer.tinnews.R;

import com.laioffer.tinnews.mvp.MvpFragment;

import com.laioffer.tinnews.retrofit.response.News;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
     // tinbasicfragment is extended by mvpfragment, and we need presenter in mvpfragment
    // tingalleryfragment is a view, so we need to extend tincontract.view
public class TinGalleryFragment extends MvpFragment<TinContract.Presenter> implements TinNewsCard.OnSwipeListener,
    TinContract.View{

    private SwipePlaceHolderView mSwipeView;

    public static TinGalleryFragment newInstance() {

        Bundle args = new Bundle();

        TinGalleryFragment fragment = new TinGalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tin_gallery, container, false);

        mSwipeView = view.findViewById(R.id.swipeView);

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tin_news_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tin_news_swipe_out_msg_view));

        view.findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        view.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });


        return view;
    }

    @Override
    public void showNewsCard(List<News> newsList) {
        mSwipeView.removeAllViews();
        for (News news : newsList) {
            TinNewsCard tinNewsCard = new TinNewsCard(news, mSwipeView, this);
            mSwipeView.addView(tinNewsCard);
        }
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Same news has been saved before", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSavedSuccess() {
        Toast.makeText(getContext(), "You successfully liked the news", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLike(News news) {
        presenter.saveFavoriteNews(news);
    }

    @Override
    public TinContract.Presenter getPresenter() {
        return new TinPresenter();
    }
}
