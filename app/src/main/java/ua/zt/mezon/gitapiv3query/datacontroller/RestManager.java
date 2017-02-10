package ua.zt.mezon.gitapiv3query.datacontroller;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.zt.mezon.gitapiv3query.model.callback.GitQService;
import ua.zt.mezon.gitapiv3query.model.helper.Constants;

/**
 * Created by MezM on 08.02.2017.
 */

public class RestManager {
    private static RestManager ourInstance = new RestManager();
    private RestManager() {
    }

    public static RestManager getInstance() {
        return ourInstance;
    }

    private static GitQService mGitQService;

    public GitQService getGitQService() {
        if (mGitQService == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory (RxJava2CallAdapterFactory.create ())
                    .build();

            mGitQService = retrofit.create(GitQService.class);


        }
        return mGitQService;
    }
}
