package io.github.mockup.algol.exception.base;

/**
 * Algol非検査例外
 */
public class AlgolRuntimeException extends RuntimeException {
	public AlgolRuntimeException() {
	}

	public AlgolRuntimeException(String message) {
		super(message);
	}

	public AlgolRuntimeException(Throwable t) {
		super(t);
	}

	public AlgolRuntimeException(String message, Throwable t) {
		super(message, t);
	}
}
