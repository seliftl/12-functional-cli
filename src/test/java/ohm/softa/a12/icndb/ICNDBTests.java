package ohm.softa.a12.icndb;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Peter Kurfer
 */

public class ICNDBTests {
    private static final Logger logger = Logger.getLogger(ICNDBTests.class.getName());
    private static final int REQUEST_COUNT = 100;

    private ICNDBApi icndbApi;

    public ICNDBTests() {
        this.icndbApi = ICNDBService.getInstance();
    }

	@Test
	void testCollision() throws ExecutionException, InterruptedException {
		var jokeNumbers = new HashSet<>();
		var requests = 0;
		var collision = false;

		while (requests++ < REQUEST_COUNT) {
			var jokeCall = icndbApi.getRandomJoke();
			var joke = jokeCall.get().getValue();

			if (jokeNumbers.contains(joke.getId())) {
				logger.info(String.format("Collision at joke %s", joke.getId()));
				collision = true;
				break;
			}

            jokeNumbers.add(joke.getId());
            logger.info(joke.toString());
        }

        assertTrue(collision, String.format("Completed %d requests without collision; consider increasing REQUEST_COUNT", requests));
    }

	@Test
	void testGetRandomJokeWithChangedName() throws ExecutionException, InterruptedException {
		var j = icndbApi.getRandomJoke("Bruce", "Wayne").get().getValue();
		assertNotNull(j);
		assertFalse(j.getJoke().contains("Chuck"));
		assertFalse(j.getJoke().contains("Norris"));
		logger.info(j.toString());
	}

    @Test
    void testGetJokeById() throws ExecutionException, InterruptedException {

		var randomIds = new ArrayList<Integer>(10);

		for (var i = 0; i < 10; i++) {
			randomIds.add(icndbApi.getRandomJoke().get().getValue().getId());
		}

		for (var id : randomIds) {
			var j = icndbApi.getJoke(id).get().getValue();
			assertNotNull(j);
			assertTrue(randomIds.contains(j.getId()));
			logger.info(j.toString());
		}
	}

	@Test
	void testGetJokeCount() throws ExecutionException, InterruptedException {
		var jokeCount = icndbApi.getJokeCount().get().getValue();

        assertNotEquals(0, jokeCount);
    }

}
