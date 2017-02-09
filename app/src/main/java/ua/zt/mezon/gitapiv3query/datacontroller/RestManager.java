package ua.zt.mezon.gitapiv3query.datacontroller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.zt.mezon.gitapiv3query.model.callback.GitQService;
import ua.zt.mezon.gitapiv3query.model.helper.Constants;

/**
 * Created by MezM on 08.02.2017.
 */

public class RestManager {

    private GitQService mGitQService;

    public GitQService getGitQService() {
        if (mGitQService == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mGitQService = retrofit.create(GitQService.class);
        }
        return mGitQService;
    }
}
