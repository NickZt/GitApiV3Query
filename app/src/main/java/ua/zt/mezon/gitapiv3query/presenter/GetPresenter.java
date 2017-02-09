package ua.zt.mezon.gitapiv3query.presenter;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.zt.mezon.gitapiv3query.MainActivity;
import ua.zt.mezon.gitapiv3query.datacontroller.RestManager;
import ua.zt.mezon.gitapiv3query.model.adapter.RepositoryItemsAdapter;
import ua.zt.mezon.gitapiv3query.model.callback.GitQService;
import ua.zt.mezon.gitapiv3query.model.pojo.GithubData;
import ua.zt.mezon.gitapiv3query.model.pojo.RepositoryItem;

/**
 * Created by MezM on 08.02.2017.
 */
public class GetPresenter {
    GitQService mApi;
    RestManager mApiComponent;
    MainActivity mView;
    private String mQuestion="Best Android repo ";

    
    private RepositoryItemsAdapter mRepositoryItemsAdapter;


    public GetPresenter(MainActivity view) {
        this.mView = view;
        mApi = mApiComponent.getGitQService();
    }




    public void getFeed() {

        Call<List<RepositoryItem>> listCall = mManager.getFlowerService().getAllFlowers();
        listCall.enqueue(new Callback<List<Flower>>() {
            @Override
            public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) { //Response<List<Flower>> response

                if (response.isSuccessful()) {
                    List<Flower> flowerList = response.body();

                    for (int i = 0; i < flowerList.size(); i++) {
                        Flower flower = flowerList.get(i);

                        SaveIntoDatabase task = new SaveIntoDatabase();
                        task.execute(flower);

                        mFlowerAdapter.addFlower(flower);
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
                mDialog.dismiss();
            }













    public void loadRepositories(final boolean isRefresh) {
        if (!isRefresh)
            mView.showProgress(true);

        mApi.getQveryRepositiories(mQuestion, "star", "desc")
                .registerObserver(new Subscriber<Response<GithubData>>() {
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
                        mView.onRequestSuccess(response.body().getItems());
                    }
                });
    }
}