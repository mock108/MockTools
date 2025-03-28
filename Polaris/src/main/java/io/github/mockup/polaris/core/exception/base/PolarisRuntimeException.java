package io.github.mockup.polaris.core.exception.base;

/**
 * Polaris非検査例外
 */
public abstract class PolarisRuntimeException extends RuntimeException {
	public PolarisRuntimeException() {
	}

	public PolarisRuntimeException(String message) {
		super(message);
	}

	public PolarisRuntimeException(Throwable t) {
		super(t);
	}

	public PolarisRuntimeException(String message, Throwable t) {
		super(message, t);
	}
}
