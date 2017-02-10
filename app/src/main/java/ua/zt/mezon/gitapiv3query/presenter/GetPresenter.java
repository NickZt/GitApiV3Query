package ua.zt.mezon.gitapiv3query.presenter;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import ua.zt.mezon.gitapiv3query.MainActivity;
import ua.zt.mezon.gitapiv3query.MainListFragment;
import ua.zt.mezon.gitapiv3query.R;
import ua.zt.mezon.gitapiv3query.RepoDetailFragment;
import ua.zt.mezon.gitapiv3query.datacontroller.RestManager;
import ua.zt.mezon.gitapiv3query.model.adapter.RepositoryDetailGitItemsAdapter;
import ua.zt.mezon.gitapiv3query.model.adapter.RepositoryItemsAdapter;
import ua.zt.mezon.gitapiv3query.model.callback.GitQService;
import ua.zt.mezon.gitapiv3query.model.pojo.GitSubscriber;
import ua.zt.mezon.gitapiv3query.model.pojo.GithubData;
import ua.zt.mezon.gitapiv3query.model.pojo.RepositoryItem;

/**
 * Created by MezM on 08.02.2017.
 */
public class GetPresenter implements RepositoryItemsAdapter.RepositoryItemClickListener {

    private static MainListFragment mMainListFragment;
    private static RepoDetailFragment mRepoDetailFragment;

    private static GetPresenter ourInstance = new GetPresenter();

    GitQService mApi;
    RestManager mManager = RestManager.getInstance();
    MainActivity mView;
    private String mQuestion = "Best Android repo ";
    private RepositoryItemsAdapter mRepositoryItemsAdapter = new RepositoryItemsAdapter(this);
    private RepositoryDetailGitItemsAdapter mRepoDetailsAdapter = new RepositoryDetailGitItemsAdapter();
    private CompositeDisposable mCompositeDisposable,mCompositeDisposable2;

    private GetPresenter() {
    }

    public static GetPresenter getInstance() {
        return ourInstance;
    }

    public static GetPresenter GetPresenterInstanceMainFragment(MainListFragment mlf) {
        mMainListFragment = mlf;
        return ourInstance;
    }

    public RepositoryItemsAdapter getmRepositoryItemsAdapter() {
        return mRepositoryItemsAdapter;
    }

    public void iniGetPresenter(MainActivity view) {
        this.mView = view;
        mApi = mManager.getGitQService();
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable2 = new CompositeDisposable();
//        return ourInstance;
    }

    public void loadRepositories(String query, final boolean isRefresh) {
        if (!isRefresh)
            mView.showProgress(true);
        this.mQuestion = query;

//        mCompositeDisposable.add(mApi.getQveryRepositiories(query, "", "")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleResponse,this::handleError));
//        Error:Library writing phase: I/O error when accessing file
// Then go in old 1.7 way.
// Sad but true

        Consumer<? super Response<GithubData>> handleResponse = new Consumer<Response<GithubData>>() {
            @Override
            public void accept(Response<GithubData> response) throws Exception {
                if (response.isSuccessful()) {
                    ArrayList<RepositoryItem> tRepositoryItemList = response.body().getItems();
                    mRepositoryItemsAdapter.reset();
                    if (tRepositoryItemList.size() > 0) {
                        for (RepositoryItem ritem : tRepositoryItemList) {
                            mRepositoryItemsAdapter.addRepositoryItem(ritem);
                        }
                    }


                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }


            }
        };
        Consumer<? super Throwable> handleError = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable e) throws Exception {

                mView.onRequestFail(e.toString() + e.getLocalizedMessage());
            }
        };

        mCompositeDisposable.add(mApi.getQveryRepositiories(query, "", "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(handleResponse, handleError));

    }


    @Override
    public void onClick(int position) {
// todo:  prepare data for  extended repo screen adapter

        loadRepoDetails(mRepositoryItemsAdapter.getSelectedRepositoryItem(position).getSubscribers_url(), false);

    }

    public static GetPresenter GetPresenterInstanceRepoDetailFragment(RepoDetailFragment repoDetailFragment) {
        mRepoDetailFragment = repoDetailFragment;
        return ourInstance;
    }

    public RepositoryDetailGitItemsAdapter getmRepoDetailsAdapter() {
        return mRepoDetailsAdapter;
    }

    public void loadRepoDetails(String url, final boolean isRefresh) {
        if (!isRefresh)
            mView.showProgress(true);





        Consumer<? super Response<List<GitSubscriber>>>handleResponse = new Consumer<Response<List<GitSubscriber>>>() {



            @Override
            public void accept(Response<List<GitSubscriber>> response) throws Exception {
                if (response.isSuccessful()) { //response.isSuccessful()
                    ArrayList<GitSubscriber> tGitSubscriberList = (ArrayList<GitSubscriber>) response.body(); //response.body();
                    mRepositoryItemsAdapter.reset();
                    if (tGitSubscriberList.size() > 0) {
                        for (GitSubscriber ritem : tGitSubscriberList) {
                            mRepoDetailsAdapter.addGitSubscriberItem(ritem);
                        }

                        FragmentTransaction f = mView.getSupportFragmentManager().beginTransaction();
                        RepoDetailFragment gitDetailFragment = new RepoDetailFragment();

                        f.add(R.id.fragment, gitDetailFragment);
                        // the fragment_container FrameLayout
                        if (mView.findViewById(R.id.detail_container) != null) {
                            f.add(R.id.detail_container, new RepoDetailFragment());
                        }
                        f.commit();



                    }


                } else {
//                    int sc = response.code();
//                    switch (sc) {
//                        case 400:
//                            Log.e("Error 400", "Bad Request");
//                            break;
//                        case 404:
//                            Log.e("Error 404", "Not Found");
//                            break;
//                        default:
//                            Log.e("Error", "Generic Error");
//                    }
                }


            }
        };
        Consumer<? super Throwable> handleError = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable e) throws Exception {

                mView.onRequestFail(e.toString() + e.getLocalizedMessage());
            }
        };

        mCompositeDisposable2.add(mApi.getQverySubscribers(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe( handleResponse, handleError));

    }






}


/*
//        Error:Library writing phase: I/O error when accessing file
// Then go in old 1.7 way.
// Sad but true


    private void handleResponse(ArrayList<RepositoryItem> intRepositoryItemList) {
ArrayList<RepositoryItem> tRepositoryItemList
        ArrayList<RepositoryItem> tRepositoryItemList = new ArrayList<>(intRepositoryItemList);
        mRepositoryItemsAdapter.reset();
        if (tRepositoryItemList.size() > 0) {
                                for (RepositoryItem ritem : tRepositoryItemList) {//expand from lambda readability only
                                    mRepositoryItemsAdapter.addRepositoryItem(ritem);
                                }

    }

    private void handleError(Throwable error) {
mView.onRequestFail(e.toString()+error.getLocalizedMessage());

    }


}*/