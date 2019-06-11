package ohm.softa.a12.icndb;

import ohm.softa.a12.model.JokeDto;
import ohm.softa.a12.model.ResponseWrapper;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.CompletableFuture;

/**
 * ICNDB singleton service
 * acts as proxy to an underlying Retrofit proxy object
 * @author Peter Kurfer
 */
public final class ICNDBService implements ICNDBApi {

    /**
     * Singleton instance
     */
    private static final ICNDBService instance = new ICNDBService();

    /**
     * Retrofit proxy
     */
    private final ICNDBApi icndbApi;

    /**
     * Access the singleton instance
     */
    public static ICNDBService getInstance() {
        return instance;
    }

    private ICNDBService() {
        /* Initialize Retrofit */
        var retrofit = new Retrofit.Builder()
                .baseUrl("http://api.icndb.com")
                .addConverterFactory(GsonConverterFactory.create())
                /* CallAdapterFactory required to wrap calls in CompletableFutures */
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .build();

        icndbApi = retrofit.create(ICNDBApi.class);
    }

    /**
     * @inheritDoc
     */
    @Override
    public CompletableFuture<ResponseWrapper<Integer>> getJokeCount() {
        return icndbApi.getJokeCount();
    }

    /**
     * @inheritDoc
     */
    @Override
    public CompletableFuture<ResponseWrapper<JokeDto>> getRandomJoke() {
        return icndbApi.getRandomJoke();
    }

    /**
     * @inheritDoc
     */
    @Override
    public CompletableFuture<ResponseWrapper<JokeDto>> getRandomJoke(String firstName, String lastName) {
        return icndbApi.getRandomJoke(firstName, lastName);
    }

    /**
     * @inheritDoc
     */
    @Override
    public CompletableFuture<ResponseWrapper<JokeDto>> getJoke(int id) {
        return icndbApi.getJoke(id);
    }
}
