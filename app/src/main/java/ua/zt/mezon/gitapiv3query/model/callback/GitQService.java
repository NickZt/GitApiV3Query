package ua.zt.mezon.gitapiv3query.model.callback;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import ua.zt.mezon.gitapiv3query.model.pojo.GitSubscriber;
import ua.zt.mezon.gitapiv3query.model.pojo.GithubData;

/**
 * Created by MezM on 08.02.2017.
 */
public interface GitQService   {
//Observable
//@FormUrlEncoded
   // @GET(Constants.SEARCH_REP)
@GET(Constants.SEARCH_REP)
Observable<Response<GithubData>> getQveryRepositiories(@Query("q") String query,
                                                       @Query("sort") String sortBy,
                                                       @Query("order") String orderBy);
    @GET
    Observable<Response<List<GitSubscriber>>> getQverySubscribers(
            @Url String url
    );
   // Observable<Response<Subscribers>> getSubs_cat();
}
