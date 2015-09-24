package be.ryan.popularmovies.tmdb;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by ryan on 23/09/15.
 */
public class TmdbRestClient {

    private static TmdbRestClient mRestClient = null;
    private TmdbService mService = null;

    private TmdbRestClient() {
    }

    public static TmdbRestClient getInstance() {
        if (mRestClient == null) {
            mRestClient = new TmdbRestClient();
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(TmdbWebServiceContract.BASE_URL)
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addQueryParam(TmdbWebServiceContract.QUERY_PARAM_API_KEY, TmdbWebServiceContract.API_KEY);
                        }
                    })
                    .build();
            mRestClient.mService = restAdapter.create(TmdbService.class);
        }
        return mRestClient;
    }

    public TmdbService getService() {
        return this.mService;
    };
}
