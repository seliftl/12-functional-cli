package de.thro.inf.prg3.a12.samples;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Peter Kurfer
 * Created on 12/29/17.
 */
public abstract class PseudoRandomNumberGenerator {

	private PseudoRandomNumberGenerator() {

	}

	public static Stream<Integer> pseudoRandomNumbers() {
		/* create a new Stream by calling `Stream.generate` and passing a new Supplier instance */
		return Stream.generate(new PseudoRandomNumberSupplier());
	}

	private static final class PseudoRandomNumberSupplier implements Supplier<Integer> {

		private final Random random;

		public PseudoRandomNumberSupplier() {
			random = new Random();
		}

		/* override required `get` method of Supplier */
		@Override
		public final Integer get() {
			return random.nextInt();
		}
	}
}
