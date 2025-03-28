package io.github.mockup.polaris.core.exception.wraper;

/**
 * Polaris初期処理例外
 */
public abstract class PolarisInitializeException extends Exception {
	public PolarisInitializeException() {
	}

	public PolarisInitializeException(String message) {
		super(message);
	}

	public PolarisInitializeException(Throwable t) {
		super(t);
	}

	public PolarisInitializeException(String message, Throwable t) {
		super(message, t);
	}
}
