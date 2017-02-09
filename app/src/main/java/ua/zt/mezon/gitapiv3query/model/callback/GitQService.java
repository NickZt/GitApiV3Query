package ua.zt.mezon.gitapiv3query.model.callback;

import android.database.Observable;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ua.zt.mezon.gitapiv3query.model.helper.Constants;
import ua.zt.mezon.gitapiv3query.model.pojo.GithubData;

/**
 * Created by MezM on 08.02.2017.
 */
public interface GitQService   {
//Observable
    @GET(Constants.SEARCH_REP)
    Observable< Response<GithubData>> getQveryRepositiories(@Query("q") String query,
                                                            @Query("sort") String sortBy,
                                                            @Query("order") String orderBy);
}
