package de.thro.inf.prg3.a12.util;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Collection of functional helper methods
 * @author Peter Kurfer
 */
public abstract class FunctionalUtils {
	private FunctionalUtils() {

	}

	/**
	 * Try-Catch wrapper catching all exceptions and returning a fallback value
	 *
	 * @param exceptionalSupplier supplier allowed to throw exceptions in `get` method
	 * @param supplier            fallback supplier that have to return a valid fallback value
	 * @param <T>                 type of the element to retrieve by supplier
	 * @return return value of ExceptionalSupplier or fallback value if call has thrown exception
	 */
	public static <T> T tryCatch(ExceptionalSupplier<? extends T> exceptionalSupplier, Supplier<? extends T> supplier) {
		Objects.requireNonNull(exceptionalSupplier);
		Objects.requireNonNull(supplier);
		try {
			return exceptionalSupplier.get();
		} catch (Exception e) {
			return supplier.get();
		}
	}

	/**
	 * Try-Catch wrapper catching all supplied exception types and returning a fallback value
	 * if unexpected exception was thrown it will be rethrown
	 *
	 * @param exceptionalSupplier supplier allowed to throw exceptions in `get` method
	 * @param supplier            fallback supplier that have to return a valid fallback value
	 * @param expected            exception types that are expected to be thrown
	 * @param <T>                 type of the element to retrieve by supplier
	 * @return return value of ExceptionalSupplier or fallback value if call has thrown exception
	 * @throws Exception if an unexpected exception was thrown
	 */
	public static <T> T tryCatch(ExceptionalSupplier<? extends T> exceptionalSupplier, Supplier<? extends T> supplier, Class<? extends Exception>... expected) throws Exception {
		try {
			return exceptionalSupplier.get();
		} catch (Exception e) {
			boolean isExpected = Arrays.stream(expected).anyMatch(aClass -> aClass.isInstance(e));
			if (isExpected) {
				return supplier.get();
			}
			throw e;
		}
	}

	/**
	 * Helper method to zip two streams to one
	 * The resulting stream will be as long as the shorter one of both streams
	 *
	 * @param stream1 fist stream to zip
	 * @param stream2 second stream to zip
	 * @param zypper  zypper function to supply an aggregate object for the resulting stream
	 * @param <T>     Type of the first stream
	 * @param <U>     Type of the second stream
	 * @param <R>     Type of the resulting stream
	 * @return zipped stream
	 */
	public static <T, U, R> Stream<R> zip(
		Stream<? extends T> stream1,
		Stream<? extends U> stream2,
		BiFunction<? super T, ? super U, ? extends R> zypper
	) {
		/* ensure zypper function is not null */
		Objects.requireNonNull(zypper);
		/* ensure stream is not null and retrieve spliterator */
		Spliterator<? extends T> tSpliterator = Objects.requireNonNull(stream1).spliterator();
		Spliterator<? extends U> uSpliterator = Objects.requireNonNull(stream2).spliterator();

		/* Sorting is lost */
		int characteristics = tSpliterator.characteristics() & uSpliterator.characteristics() & ~(Spliterator.DISTINCT | Spliterator.SORTED);

		/* determine resulting size */
		long zipSize = ((characteristics & Spliterator.SIZED) != 0) ? Math.min(tSpliterator.getExactSizeIfKnown(), uSpliterator.getExactSizeIfKnown()) : -1;

		/* get iterators */
		Iterator<? extends T> tIterator = Spliterators.iterator(tSpliterator);
		Iterator<? extends U> uIterator = Spliterators.iterator(uSpliterator);

		/* create merging iterator which iterates both iterators as long as both have more elements */
		Iterator<R> resultIterator = new Iterator<R>() {
			@Override
			public boolean hasNext() {
				return tIterator.hasNext() && uIterator.hasNext();
			}

			@Override
			public R next() {
				/* apply BiFunction to create aggregates */
				return zypper.apply(tIterator.next(), uIterator.next());
			}
		};

		/* create new spliterator based on merging iterator */
		Spliterator<R> split = Spliterators.spliterator(resultIterator, zipSize, characteristics);

		/* return new Stream */
		return (stream1.isParallel() | stream2.isParallel()) ? StreamSupport.stream(split, true) : StreamSupport.stream(split, false);
	}

	/**
	 * Supplier that allows throwing Exceptions
	 *
	 * @param <T> type of the element supplied
	 */
	@FunctionalInterface
	public interface ExceptionalSupplier<T> {
		T get() throws Exception;
	}
}
