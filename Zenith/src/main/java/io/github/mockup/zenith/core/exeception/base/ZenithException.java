package io.github.mockup.zenith.core.exeception.base;

/**
 * Zenith標準例外
 */
public abstract class ZenithException extends Exception {
	public ZenithException() {
	}

	public ZenithException(String message) {
		super(message);
	}

	public ZenithException(Throwable t) {
		super(t);
	}

	public ZenithException(String message, Throwable t) {
		super(message, t);
	}
}
