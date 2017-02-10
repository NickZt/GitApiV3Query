package ua.zt.mezon.gitapiv3query.presenter;

import android.util.Log;

import java.util.ArrayList;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import ua.zt.mezon.gitapiv3query.MainActivity;
import ua.zt.mezon.gitapiv3query.MainListFragment;
import ua.zt.mezon.gitapiv3query.datacontroller.RestManager;
import ua.zt.mezon.gitapiv3query.model.adapter.RepositoryItemsAdapter;
import ua.zt.mezon.gitapiv3query.model.callback.GitQService;
import ua.zt.mezon.gitapiv3query.model.pojo.GithubData;
import ua.zt.mezon.gitapiv3query.model.pojo.RepositoryItem;

/**
 * Created by MezM on 08.02.2017.
 */
public class GetPresenter implements RepositoryItemsAdapter.RepositoryItemClickListener {
    private static GetPresenter ourInstance = new GetPresenter();
    GitQService mApi;
    RestManager mManager=RestManager.getInstance();
    MainActivity mView;
    private String mQuestion = "Best Android repo ";
    static MainListFragment mMainListFragment;

    private RepositoryItemsAdapter mRepositoryItemsAdapter = new RepositoryItemsAdapter(this);

    public RepositoryItemsAdapter getmRepositoryItemsAdapter() {
        return mRepositoryItemsAdapter;
    }
    public static GetPresenter getInstance() {
        return ourInstance;
    }

    private GetPresenter() {
    }

    public  void iniGetPresenter(MainActivity view) {
        this.mView = view;
        mApi = mManager.getGitQService();
//        return ourInstance;
    }
    public  static GetPresenter GetPresenterInstanceMainFragment(MainListFragment mlf)
    {
        mMainListFragment=mlf;
        return ourInstance;
    }

    public void loadRepositories(String query, final boolean isRefresh) {
        if (!isRefresh)
            mView.showProgress(true);
        this.mQuestion = query;

        Observable<Response<GithubData>> status =mApi.getQveryRepositiories(query, "", "");
        status.subscribe(new Subscriber<Response<GithubData>>() {

            @Override
            public void onCompleted() {
                mView.showProgress(false);

                if (isRefresh) {
                    mView.onRefreshDone();
                }


            }

            @Override
            public void onError(Throwable e) {
                mView.onRequestFail(e.toString());
            }

            @Override
            public void onNext(Response<GithubData> response) {
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
        });


//        mApi.getQveryRepositiories(mQuestion, "", "") //"star", "desc" if need
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Subscriber<Response<GithubData>>() {
//
//                    @Override
//                    public void onCompleted() {
//                        mView.showProgress(false);
//
//                        if (isRefresh) {
//                            mView.onRefreshDone();
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.onRequestFail(e.toString());
//                    }
//
//                    @Override
//                    public void onNext(Response<GithubData> response) {
//                        if (response.isSuccessful()) {
//                            ArrayList<RepositoryItem> tRepositoryItemList = response.body().getItems();
//                            mRepositoryItemsAdapter.reset();
//                            if (tRepositoryItemList.size() > 0) {
//                                for (RepositoryItem ritem : tRepositoryItemList) {
//                                    mRepositoryItemsAdapter.addRepositoryItem(ritem);
//                                }
//                            }
//
//
//                        } else {
//                            int sc = response.code();
//                            switch (sc) {
//                                case 400:
//                                    Log.e("Error 400", "Bad Request");
//                                    break;
//                                case 404:
//                                    Log.e("Error 404", "Not Found");
//                                    break;
//                                default:
//                                    Log.e("Error", "Generic Error");
//                            }
//                        }
//
//
//                    }
//                });
    }

    @Override
    public void onClick(int position) {
// todo:  prepare data for  extended repo screen adapter
    }
}

