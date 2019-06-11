package ohm.softa.a12.icndb;

import ohm.softa.a12.model.JokeDto;
import ohm.softa.a12.model.ResponseWrapper;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.concurrent.CompletableFuture;

/**
 * @author Peter Kurfer
 * Created on 12/28/17.
 */
public interface ICNDBApi {

    /**
     * Fetch the count of jokes the API is aware of
     * @return count wrapped in a ResponseWrapper object
     */
    @GET("/jokes/count")
    CompletableFuture<ResponseWrapper<Integer>> getJokeCount();

    /**
     * Fetch a random joke
     * @return JokeDto wrapped in a ResponseWrapper object
     */
    @GET("/jokes/random")
    CompletableFuture<ResponseWrapper<JokeDto>> getRandomJoke();

    /**
     * Fetch a random joke
     * replaces 'Chuck' and 'Norris' by the provided values
     * @param firstName name to replace 'Chuck'
     * @param lastName name to replace 'Norris'
     * @return JokeDto wrapped in a ResponseWrapper object
     */
    @GET("/jokes/random")
    CompletableFuture<ResponseWrapper<JokeDto>> getRandomJoke(@Query("firstName") String firstName, @Query("lastName") String lastName);

    /**
     * Fetch the joke corresponding to the given id
     * @param id id of the joke to fetch
     * @return JokeDto wrapped in a ResponseWrapper object
     */
    @GET("/jokes/{id}")
    CompletableFuture<ResponseWrapper<JokeDto>> getJoke(@Path("id") int id);

}
