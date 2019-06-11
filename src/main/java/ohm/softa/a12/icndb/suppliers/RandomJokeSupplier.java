package ohm.softa.a12.icndb.suppliers;

import ohm.softa.a12.icndb.ICNDBApi;
import ohm.softa.a12.icndb.ICNDBService;
import ohm.softa.a12.model.JokeDto;
import ohm.softa.a12.model.ResponseWrapper;
import org.apache.commons.lang3.NotImplementedException;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @author Peter Kurfer
 */
public final class RandomJokeSupplier implements Supplier<ResponseWrapper<JokeDto>> {

	/**
	 * ICNDBAPI proxy
	 */
	private final ICNDBApi icndbApi;

	public RandomJokeSupplier() {
		icndbApi = ICNDBService.getInstance();
	}

	@Override
	public ResponseWrapper<JokeDto> get() {
		/*
		 * alternate more function solution
		 * return tryCatch(() -> icndbApi.getRandomJoke().get(), () -> null); */
		try {
			/*try to fetch next random joke */
			return icndbApi.getRandomJoke().get();
		} catch (InterruptedException | ExecutionException e) {
			/* return null if error occurred */
			return null;
		}
	}
}
