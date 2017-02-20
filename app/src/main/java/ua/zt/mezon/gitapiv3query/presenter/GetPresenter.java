package ua.zt.mezon.gitapiv3query.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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


    private static final boolean DEBUGON = true;
    private static MainListFragment mMainListFragment;
    private static Fragment mRepoDetailFragment;

    private static GetPresenter ourInstance = new GetPresenter();

    GitQService mApi;
    RestManager mManager = RestManager.getInstance();
    MainActivity mView;
    private String mQuestion = "Best Android repo ";
    private RepositoryItemsAdapter mRepositoryItemsAdapter = new RepositoryItemsAdapter(this);
    private RepositoryDetailGitItemsAdapter mRepoDetailsAdapter = new RepositoryDetailGitItemsAdapter();
    private CompositeDisposable mCompositeDisposable, mCompositeDisposable2;

    private GetPresenter() {
    }

    public static MainListFragment getmMainListFragment() {
        return mMainListFragment;
    }

    public static GetPresenter getInstance() {
        return ourInstance;
    }

    public static GetPresenter GetPresenterInstanceMainFragment(MainListFragment mlf) {
        mMainListFragment = mlf;
        return ourInstance;
    }

    public static GetPresenter GetPresenterInstanceRepoDetailFragment(RepoDetailFragment repoDetailFragment) {
        mRepoDetailFragment = repoDetailFragment;
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
    }

    public void loadRepositories(String query, final boolean isRefresh) {
//        if (!isRefresh)
        mView.showProgress(true);
        this.mQuestion = query;
// I mean some 1.8 sugar set code look better. Like this
//   mCompositeDisposable.add(mApi.getQveryRepositiories(query, "", "")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::handleResponse,this::handleError));

////  private void handleResponse(ArrayList<RepositoryItem> intRepositoryItemList) {
//        ArrayList<RepositoryItem> tRepositoryItemList
//        ArrayList<RepositoryItem> tRepositoryItemList = new ArrayList<>(intRepositoryItemList);
//        mRepositoryItemsAdapter.reset();
//        if (tRepositoryItemList.size() > 0) {
//            for (RepositoryItem ritem : tRepositoryItemList) {//expand from lambda readability only
//                mRepositoryItemsAdapter.addRepositoryItem(ritem);
//            }
//
//        }
//
//    private void handleError(Throwable error) {
//        mView.onRequestFail(e.toString()+error.getLocalizedMessage());
//
// After compiling i got error
// //        Error:Library writing phase: I/O error when accessing file
// // Then go in old 1.7 way.
// Sad but true
//


        Consumer<? super Response<GithubData>> handleResponse = new Consumer<Response<GithubData>>() {
            @Override
            public void accept(Response<GithubData> response) throws Exception {
                if (response.isSuccessful()) {
                    ArrayList<RepositoryItem> tRepositoryItemList = response.body().getItems();
                    mRepositoryItemsAdapter.reset();

                    if (mRepoDetailsAdapter != null) {
                        mRepoDetailsAdapter.reset();
                    }

                    if (tRepositoryItemList.size() > 0) {
                        for (RepositoryItem ritem : tRepositoryItemList) {
                            mRepositoryItemsAdapter.addRepositoryItem(ritem);
                        }
                    }

//                    if (!isRefresh)
                    mView.showProgress(false);
                    showMainListFragment();

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

        loadRepoDetails(mRepositoryItemsAdapter.getSelectedRepositoryItem(position));

    }

    public RepositoryDetailGitItemsAdapter getmRepoDetailsAdapter() {
        return mRepoDetailsAdapter;
    }

    public void loadRepoDetails(final RepositoryItem repositoryItem) {
        mView.showProgress(true);


        Consumer<? super Response<List<GitSubscriber>>> handleResponse = new Consumer<Response<List<GitSubscriber>>>() {


            @Override
            public void accept(Response<List<GitSubscriber>> response) throws Exception {
                if (response.isSuccessful()) { //response.isSuccessful()
                    ArrayList<GitSubscriber> tGitSubscriberList = (ArrayList<GitSubscriber>) response.body(); //response.body();
                    mRepoDetailsAdapter.reset();
                    if (tGitSubscriberList.size() > 0) {
                        for (GitSubscriber ritem : tGitSubscriberList) {
                            mRepoDetailsAdapter.addGitSubscriberItem(ritem);
                        }
                        mRepoDetailsAdapter.setmRepoDetailFragment_urlto(repositoryItem.getOwner().getAvatarUrl());
                        mRepoDetailsAdapter.setmRepoDetailFragment_detail_name(repositoryItem.getName());
                        mView.showProgress(false);

                        showRepodetailFragment();


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

        mCompositeDisposable2.add(mApi.getQverySubscribers(repositoryItem.getSubscribers_url())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(handleResponse, handleError));

    }

    public void showMainListFragment() {

        FragmentManager fManager = mView.getSupportFragmentManager();
        if (mMainListFragment == null) {
            mMainListFragment = new MainListFragment();
        }

        if (fManager.findFragmentById(mMainListFragment.getId()) != null) {
            fManager.beginTransaction().remove(mMainListFragment).commit();
            fManager.executePendingTransactions();
        } else {

            if (mMainListFragment == null) {
                mMainListFragment = new MainListFragment();
            }

        }

        FragmentTransaction f = fManager.beginTransaction();

        //        set animator
//        f.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        f.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);

        if ((mView.findViewById(R.id.detail_container) != null)) {
// Two Panel mode
            f.replace(R.id.container, mMainListFragment)

                    .commit();
        } else {
// One Panel mode
            f.replace(R.id.container, mMainListFragment)

                    .commit();
        }

    }

    public void showRepodetailFragment() {

        FragmentManager fManager = mView.getSupportFragmentManager();

        if (mRepoDetailFragment == null) {
            mRepoDetailFragment = new RepoDetailFragment();
        }


//        remove/create fragment
        if (fManager.findFragmentById(mRepoDetailFragment.getId()) != null) {
            fManager.beginTransaction().remove(mRepoDetailFragment).commit();

            fManager.executePendingTransactions();

        } else {
            if (mRepoDetailFragment == null) {
                mRepoDetailFragment = new RepoDetailFragment();
            }

        }

        FragmentTransaction f = fManager.beginTransaction();

        //        set animator
//        f.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        f.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);

        if ((mView.findViewById(R.id.detail_container) != null)) {
// Two Panel mode
//            if (DEBUGON) Log.d(TAG, "showRepodetailFragment()  Two Panel mode" );


            if (fManager.findFragmentById(R.id.detail_container) == null) {
                f.add(R.id.detail_container, mRepoDetailFragment);
//                d(TAG, "showRepodetailFragment() (f.add(R.id.detail_container, " );
            } else {
                f.replace(R.id.detail_container, mRepoDetailFragment);
//                d(TAG, "showRepodetailFragment() replace(R.id.detail_container " );
            }
            f.commit();


        } else {
// One Panel mode
            f.replace(R.id.container, mRepoDetailFragment)
                    .addToBackStack(null)
                    .commit();
        }


    }


}


