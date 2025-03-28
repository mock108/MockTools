package io.github.mockup.zenith.core.exeception.base;

/**
 * Zenith非検査例外
 */
public abstract class ZenithRuntimeException extends RuntimeException {
	public ZenithRuntimeException() {
	}

	public ZenithRuntimeException(String message) {
		super(message);
	}

	public ZenithRuntimeException(Throwable t) {
		super(t);
	}

	public ZenithRuntimeException(String message, Throwable t) {
		super(message, t);
	}
}
