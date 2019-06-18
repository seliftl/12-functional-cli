package ohm.softa.a12.icndb;

import ohm.softa.a12.icndb.suppliers.AllJokesSupplier;
import ohm.softa.a12.model.JokeDto;
import ohm.softa.a12.model.ResponseWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTimeout;

/**
 * @author Peter Kurfer
 * Created on 12/28/17.
 */
class JokesGeneratorTests {

    private JokeGenerator jokeGenerator = new JokeGenerator();
	private static final Logger logger = LogManager.getLogger();


	@Test
    void testRandomStream() {
        /* timeout to ensure that stream does not loop forever */
        /* TODO implement a test for the random joke stream */
    }


    @Test
    void testJokesStream() {
        /* TODO implement a test for the linear jokes generator */

		/* ensure termination to avoid infinite stream error */
		assertTimeout(Duration.ofMinutes(1), () -> jokeGenerator.jokesStream()
			/* filter null objects */
			.filter(Objects::nonNull)
			/* unwrap ResponseWrapper */
			.map(ResponseWrapper::getValue)
			/* skip 200 elements to force overflow */
			.skip(20L)
			/* limit to 400 elements due to infinite stream */
			.limit(40L)
			/* unwrap actual joke String */
			.map(JokeDto::getJoke)
			/* apply consumer */
			.forEach(System.out::println));
    }

}
