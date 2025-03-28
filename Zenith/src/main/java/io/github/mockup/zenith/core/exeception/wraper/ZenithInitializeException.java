package io.github.mockup.zenith.core.exeception.wraper;

/**
 * Zenith初期処理例外
 */
public abstract class ZenithInitializeException extends Exception {
	public ZenithInitializeException() {
	}

	public ZenithInitializeException(String message) {
		super(message);
	}

	public ZenithInitializeException(Throwable t) {
		super(t);
	}

	public ZenithInitializeException(String message, Throwable t) {
		super(message, t);
	}
}
