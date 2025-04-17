package io.github.mockup.algol.exception.base;

/**
 * Algol例外
 */
public class AlgolException extends Exception {
	public AlgolException() {
	}

	public AlgolException(String message) {
		super(message);
	}

	public AlgolException(Throwable t) {
		super(t);
	}

	public AlgolException(String message, Throwable t) {
		super(message, t);
	}
}
