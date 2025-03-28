package io.github.mockup.polaris.core.exception.base;

/**
 * Polaris標準例外
 */
public abstract class PolarisException extends Exception {
	public PolarisException() {
	}

	public PolarisException(String message) {
		super(message);
	}

	public PolarisException(Throwable t) {
		super(t);
	}

	public PolarisException(String message, Throwable t) {
		super(message, t);
	}
}
